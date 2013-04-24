package kloh.project;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import kloh.gameengine.Animation;
import kloh.gameengine.Dir;
import kloh.gameengine.Entity;
import kloh.gameengine.GameWorld;
import kloh.gameengine.GraphNode;
import kloh.gameengine.PathFinder;
import kloh.gameengine.SpriteReader;
import kloh.gameengine.Viewport;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class Board implements GameWorld {
	
	private Tile[][] _tiles;
	private ArrayList<Unit> _units;
	private ArrayList<Entity> _removalList;
	private ArrayList<Tower> _towers;
	private ArrayList<AfterDestroyText> _afterDestroyTexts;
	private ArrayList<Explosion> _explosions;
	private Vec2i _gridDimensions;
	private ArrayList<Projectile> _projectiles;
	private HashMap<String, HashMap<Vec2i, BufferedImage>> _imageMap;
	private TowerPanel _towerPanel;
	private int _currentRound;
	private int _prev; //the previous time we spawned enemies was at this # second
	private GameScreen _gameScreen;
	private Vec2i _heartTile; //the location of the tile all enemies want to get to
	private int _score;
	private int _gold;
	private Vec2i _tileSelected; //the current tile that is selected, if any
	private UpgradePanel _upgradePanel;
	private BoardEvaluator _boardEvaluator;
	private int _prevRoundHealth; //health of units in previous round
	private boolean _clickedOn; //whether the selected tile has been clicked on
	private int _goldTowers; //number of gold towers
	
	public Board(Vec2i gridDimensions, TowerPanel towerPanel, UpgradePanel upgradePanel, GameScreen gameScreen, int difficulty) {
		SpriteInfo spriteInfo = new SpriteInfo();
		SpriteReader spriteReader = new SpriteReader(spriteInfo.createSpriteFiles(), spriteInfo.createSpriteInfo());
		_imageMap = spriteReader.getImageMap();
		
		_gridDimensions = gridDimensions;
		_gold = Constants.STARTING_GOLD;
		_gameScreen = gameScreen;
		_goldTowers = 0;
		_score = 0;
		_currentRound = 0;
		_prev = 0;
		_prevRoundHealth = 100;
		_clickedOn = false;
		_heartTile = new Vec2i(gridDimensions.x/2, gridDimensions.y/2);
		_towerPanel = towerPanel;
		_towers = new ArrayList<Tower>();
		this.initializeTiles();
		float evaluationThreshold;
		switch (difficulty) {
		case 0:
			evaluationThreshold = Constants.EVALUATION_THRESHOLD_EASY;
			this.addTower(Constants.BASIC_TOWER_INT, _heartTile.minus(1,0));
			this.addTower(Constants.BASIC_TOWER_INT, _heartTile.plus(1,0));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x+5, _heartTile.y));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x+5, _heartTile.y-1));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x+5, _heartTile.y+1));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x-5, _heartTile.y));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x-5, _heartTile.y-1));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x-5, _heartTile.y+1));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x, _heartTile.y+5));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x-1, _heartTile.y+5));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x+1, _heartTile.y+5));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x, _heartTile.y-5));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x-1, _heartTile.y-5));
			this.addTower(Constants.WALL_INT, new Vec2i(_heartTile.x+1, _heartTile.y-5));
			break;
		case 1:
			evaluationThreshold = Constants.EVALUATION_THRESHOLD_MEDIUM;
			this.addTower(Constants.BASIC_TOWER_INT, _heartTile.minus(1,0));
			this.addTower(Constants.BASIC_TOWER_INT, _heartTile.plus(1,0));
			_gold = _gold-2;
			break;
		case 2:
			evaluationThreshold = Constants.EVALUATION_THRESHOLD_HARD;
			//this.addTower(Constants.WALL_INT, _heartTile.minus(1,0));
			//this.addTower(Constants.WALL_INT, _heartTile.plus(1,0));
			_gold = _gold-2;
			break;
		default: throw new IllegalArgumentException("difficulty should be int from 0 to 2 inclusive");
		}
		
		_boardEvaluator = new BoardEvaluator(evaluationThreshold);
		_upgradePanel = upgradePanel;
		_upgradePanel.display(8, null, null);
		_tileSelected = null;
		
		_removalList = new ArrayList<Entity>();
		
		_projectiles = new ArrayList<Projectile>();
		_units = new ArrayList<Unit>();
		_afterDestroyTexts = new ArrayList<AfterDestroyText>();
		_explosions = new ArrayList<Explosion>();
		
	}
	
	public HashMap<Dir,Animation> createUnitImages() {
		
		HashMap<Dir,Animation> animationMap = new HashMap<Dir,Animation>();
		
		BufferedImage[] friendlyMoveUpImages = new BufferedImage[1];
		friendlyMoveUpImages[0] = _imageMap.get("soldier-up.png").get(new Vec2i(0,0));
		animationMap.put(Dir.N, new Animation(friendlyMoveUpImages));
						
		BufferedImage[] friendlyMoveDownImages = new BufferedImage[1];
		friendlyMoveDownImages[0] = _imageMap.get("soldier-down.png").get(new Vec2i(0,0));
		animationMap.put(Dir.S, new Animation(friendlyMoveDownImages));
		
		BufferedImage[] friendlyMoveLeftImages = new BufferedImage[1];
		friendlyMoveLeftImages[0] = _imageMap.get("soldier-left.png").get(new Vec2i(0,0));
		animationMap.put(Dir.W, new Animation(friendlyMoveLeftImages));
		
		BufferedImage[] friendlyMoveRightImages = new BufferedImage[1];
		friendlyMoveRightImages[0] = _imageMap.get("soldier-right.png").get(new Vec2i(0,0));
		animationMap.put(Dir.E, new Animation(friendlyMoveRightImages));

		return animationMap;
	}
	
	public BufferedImage[] createExplosionImages() {
		BufferedImage[] explosions = new BufferedImage[25];
		for (int row=0; row<5; row++) {
			for (int col=0; col<5; col++) {
				explosions[row+col*5] = _imageMap.get("explosions.png").get(new Vec2i(row,col));
			}
		}
		return explosions;
	}

	public void addExplosion(Vec2f location, Vec2f size) {
		_explosions.add(new Explosion(this.createExplosionImages(), this, location.minus(0.5f,0.5f), new Vec2f(2,2)));
	}
	
	public void addProjectile(Vec2f initialLocation, Vec2f velocity, int damage) {
		_projectiles.add(new Projectile(initialLocation, null, velocity, this, damage));
	}
	
	public void initializeTiles() {
		_tiles = new Tile[_gridDimensions.x][_gridDimensions.y];
		for (int x=0; x<_gridDimensions.x; x++) {
			for (int y=0; y<_gridDimensions.y; y++) {
				_tiles[x][y] = new Tile(new Vec2i(x,y), new Vec2i(1,1), _imageMap.get("grass.png").get(new Vec2i(0,0)));
			}
		}
		_tiles[_heartTile.x][_heartTile.y] = new Tile(_heartTile, new Vec2i(1,1), _imageMap.get("barracks.png").get(new Vec2i(0,0))); //replacing the earlier initialized tile with a barracks
		_tiles[0][_heartTile.y].setBuilt(true);
		_tiles[_gridDimensions.x-1][_heartTile.y].setBuilt(true);
		_tiles[_heartTile.x][0].setBuilt(true);
		_tiles[_heartTile.x][_gridDimensions.y-1].setBuilt(true);
		

		for (int x=0; x<_gridDimensions.x; x++) {
			for (int y=0; y<_gridDimensions.y; y++) {
				this.populateNeighbors(x, y, false);
			}
		}
	}
	
	public void addTower(int towerType, Vec2i location) {
		switch(towerType) {
		case Constants.WALL_INT:
			_towers.add(new Wall(location,this));
			break;
		case Constants.BASIC_TOWER_INT:
			_towers.add(new BasicTower(location,this));
			break;
		case Constants.GOLD_TOWER_INT:
			_towers.add(new GoldTower(location,this));
			break;
		case Constants.ADVANCED_TOWER_INT:
			_towers.add(new AdvancedTower(location,this));
			break;
		case Constants.SUPER_TOWER_INT:
			_towers.add(new SuperTower(location,this));
			break;
		default:
			throw new IllegalArgumentException("The towerType passed in must be valid");
		}
		_tiles[location.x][location.y].setBuilt(true);
		_tiles[location.x][location.y].setFull(true);
	}
	
	public Vec2i getGridDimensions() {
		return _gridDimensions;
	}
	
	public ArrayList<Unit> getUnits() {
		return _units;
	}
	
	public void populateNeighbors(int x, int y, boolean special) {
		if (!special) {
			ArrayList<GraphNode> neighbors = new ArrayList<GraphNode>();
			if (x != 0) {
				if (!_tiles[x-1][y].getBuilt() && !_tiles[x-1][y].getFull()) {
					neighbors.add(_tiles[x-1][y]);
				}
			}
			if (x != _gridDimensions.x-1) {
				if (!_tiles[x+1][y].getBuilt() && !_tiles[x+1][y].getFull()) {
					neighbors.add(_tiles[x+1][y]);
				}
			}
			if (y != 0) {
				if (!_tiles[x][y-1].getBuilt() && !_tiles[x][y-1].getFull()) {
					neighbors.add(_tiles[x][y-1]);
				}
			}
			if (y != _gridDimensions.y-1) {
				if (!_tiles[x][y+1].getBuilt() && !_tiles[x][y+1].getFull()) {
					neighbors.add(_tiles[x][y+1]);
				}
			}
			_tiles[x][y].populateNeighbors(neighbors);
		}
		else {
			ArrayList<GraphNode> neighbors = new ArrayList<GraphNode>();
			if (x != 0) {
				if (!_tiles[x-1][y].getBuilt()) {
					neighbors.add(_tiles[x-1][y]);
				}
			}
			if (x != _gridDimensions.x-1) {
				if (!_tiles[x+1][y].getBuilt()) {
					neighbors.add(_tiles[x+1][y]);
				}
			}
			if (y != 0) {
				if (!_tiles[x][y-1].getBuilt()) {
					neighbors.add(_tiles[x][y-1]);
				}
			}
			if (y != _gridDimensions.y-1) {
				if (!_tiles[x][y+1].getBuilt()) {
					neighbors.add(_tiles[x][y+1]);
				}
			}
			_tiles[x][y].populateNeighbors(neighbors);
		}
	}
	
	//checks if there's valid paths from the start squares
	public boolean checkValidPath(Vec2i tileToBuildOn) {
		_tiles[tileToBuildOn.x][tileToBuildOn.y].setBuilt(true); 
		_tiles[tileToBuildOn.x][tileToBuildOn.y].setFull(true); 
		for (int a=0; a<_gridDimensions.x; a++) {
			for (int b=0; b<_gridDimensions.y; b++) {
				this.populateNeighbors(a,b,true);
			}
		}
		boolean path = this.pathToCenter();
		_tiles[tileToBuildOn.x][tileToBuildOn.y].setBuilt(false); 
		_tiles[tileToBuildOn.x][tileToBuildOn.y].setFull(false); 
		return path;
	}
	
	@Override
	public void onMouseMoved(Vec2f location) {
		Vec2i locationTile = new Vec2i((int)location.x, (int)location.y);
		if (_towerPanel.getSelectedTower() > -1 && !_clickedOn) { //if there is a tower selected in tower panel & there wasn't a tile clicked on before
			if (locationTile.x >= 0 && locationTile.x < _gridDimensions.x && locationTile.y >= 0 && locationTile.y < _gridDimensions.y && !_tiles[locationTile.x][locationTile.y].getFull() && !_tiles[locationTile.x][locationTile.y].getBuilt() && !(locationTile.x == _heartTile.x && locationTile.y == _heartTile.y)) { //if tile is not occupied by unit, and tile is not built, and this is not the center tile
				if (_gold - this.getTowerCost(_towerPanel.getSelectedTower()) > -1 && this.checkValidPath(locationTile)) { //there is a valid path and there is enough gold to build
					this.setSelectedTile(locationTile, true); 
				}
				else { //we can't build the tower
					this.setSelectedTile(locationTile, false);
				}
			}
			else if (locationTile.x >= 0 && locationTile.x < _gridDimensions.x && locationTile.y >= 0 && locationTile.y < _gridDimensions.y && _tiles[locationTile.x][locationTile.y].getFull() && !_tiles[locationTile.x][locationTile.y].getBuilt() && !(locationTile.x == _heartTile.x && locationTile.y == _heartTile.y)) {
				this.setSelectedTile(locationTile, false);
			}
			else if (!_clickedOn) {
				this.clearSelection();
			}
		}
		else if (!_clickedOn) {
			this.clearSelection();
		}
	}
	
	public boolean getClickedOn() {
		return _clickedOn;
	}
	
	public void onMouseClicked(Vec2f locationClicked) {
		int selectedTower = _towerPanel.getSelectedTower();
		Vec2i locationTile = new Vec2i((int)locationClicked.x, (int)locationClicked.y);
		if (locationTile.x >= 0 && locationTile.x < _gridDimensions.x && locationTile.y >= 0 && locationTile.y < _gridDimensions.y) {
			if (_tileSelected != null && _tiles[_tileSelected.x][_tileSelected.y].getFull()) {
				this.setSelectedTile(_tileSelected, false);
			}
			
			if (_tileSelected != null && _tiles[_tileSelected.x][_tileSelected.y].getSelected() == 1 && !_clickedOn) { //if it is the right condition to place a tower
				//assert(_tileSelected.equals(locationTile)):"If there was a tile selected, the tile must be equals to the tile we are on now";
				int cost = this.getTowerCost(selectedTower);
				if (selectedTower == Constants.GOLD_TOWER_INT && _goldTowers < Constants.GOLD_TOWER_MAX) { //if we're building a gold tower
					if (_gold - cost > -1) {
						_gold -= cost;
						this.addTower(selectedTower, locationTile);
						_towerPanel.setSelectedTower(-1); 
						_goldTowers++;
						_towerPanel.setGoldTowers(Constants.GOLD_TOWER_MAX-_goldTowers);
					}
				}
				else if (selectedTower != Constants.GOLD_TOWER_INT){
					if (_gold - cost > -1) {
						_gold -= cost;
						this.addTower(selectedTower, locationTile);
						_towerPanel.setSelectedTower(-1); 
					}
				}
				this.clearSelection();
			}

			//if there is a tower on that tile
			else if (_tiles[locationTile.x][locationTile.y].getBuilt() 
					&& !locationTile.equals(_heartTile) 
					&& !locationTile.equals(new Vec2i(0,_heartTile.y))
					&& !locationTile.equals(new Vec2i(_gridDimensions.x-1,_heartTile.y))
					&& !locationTile.equals(new Vec2i(_heartTile.x,0))
					&& !locationTile.equals(new Vec2i(_heartTile.x,_gridDimensions.y-1))) {
				Tower tower = this.getTower(locationTile);
				assert(tower != null):"There must be a tower occupying the tile if its marked as built and not one of the heart tiles or spawning tiles";
				this.setSelectedTile(locationTile, true);
				_upgradePanel.display(this.getTowerType(tower), locationTile, tower);
				_towerPanel.setSelectedTower(-1); 
				_clickedOn = true;
			}
		}
	}
	
	/*Sets a tile to selected or invalid
	 * first it restores the original selected tile to unselected
	 * then it sets the new tile to selected or invalid
	 */
	public void setSelectedTile(Vec2i tile, boolean selection) {
		if (tile == null) {
			throw new IllegalArgumentException("Tile passed in should NOT be null!!!");
		}
		if (_tileSelected != null) {
			_tiles[_tileSelected.x][_tileSelected.y].setSelected(0);
			_clickedOn = false;
		}
		_tileSelected = tile;
		if (selection) {
			_tiles[_tileSelected.x][_tileSelected.y].setSelected(1);
		}
		else {
			_tiles[_tileSelected.x][_tileSelected.y].setSelected(-1);
		}
	}
	
	/*Clears any selected tile - makes sure that there is no selected tile*/
	public void clearSelection() {
		if (_tileSelected != null) {
			_tiles[_tileSelected.x][_tileSelected.y].setSelected(0); //restores the original tile to unselected
		}
		_tileSelected = null; //sets the selected tile to null
		_upgradePanel.display(8, null, null); //there is no selected tile, so there is no tower to upgrade, so sets the upgradepanel to display the default message
		_clickedOn = false;
	}
	
	/*Pass in a tower, gets the int of the tower*/
	public int getTowerType(Tower tower) {
		int towerType = -1;
		if (tower instanceof Wall) {
			towerType = Constants.WALL_INT;
		}
		else if (tower instanceof BasicTower) {
			towerType = Constants.BASIC_TOWER_INT;
		}
		else if (tower instanceof GoldTower) {
			towerType = Constants.GOLD_TOWER_INT;
		}
		else if (tower instanceof AdvancedTower) {
			towerType = Constants.ADVANCED_TOWER_INT;
		}
		else if (tower instanceof SuperTower) {
			towerType = Constants.SUPER_TOWER_INT;
		}
		assert(towerType >= 0):"Wrong type of tower";
		return towerType;
	}
	
	/*Passes in a tile coordinate, gets the tower on the tile*/
	public Tower getTower(Vec2i tileLocation) {
		int i=0;
		boolean found = false;
		while (i<_towers.size() && !found) {
			Vec2i tile = _towers.get(i).getTile();
			if (tile.equals(tileLocation)) {
				return _towers.get(i);
			}
			else {
				i++;
			}
		}
		return null;
	}
	
	public void addExplodeText(Vec2f location, int points) {
		AfterDestroyText explodeText = new AfterDestroyText(location, _currentRound*Constants.ROUND_INTERVAL, points, this);
		_afterDestroyTexts.add(explodeText);
	}
	
	public void addUnit(Vec2i tile, int health, int unitType) {
		Unit unit;
		switch(unitType) {
		case Constants.BASIC_UNIT_INT:
			unit = new BasicUnit(tile, this, this.createUnitImages(), health);
			break;
		case Constants.EXPLODING_UNIT_INT:
			unit = new ExplodingUnit(tile, this, this.createUnitImages(), health, _currentRound*Constants.ROUND_INTERVAL);
			break;
		default:
			throw new IllegalArgumentException("unitType is not valid");
		}
		unit.setDestination(_heartTile);
		_units.add(unit);
		_gameScreen.addUnit(unit);
	}
	
	public void spawnEnemies() {
		if (_currentRound <= 3) {
			this.addUnit(new Vec2i(0,_heartTile.y), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			this.addUnit(new Vec2i(_gridDimensions.x-1, _heartTile.y), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			this.addUnit(new Vec2i(_heartTile.x, 0), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			this.addUnit(new Vec2i(_heartTile.x, _gridDimensions.y-1), _prevRoundHealth, Constants.BASIC_UNIT_INT);
		}
		else if (_currentRound <= 15){ //difficulty only increases after round 3
			_prevRoundHealth = _prevRoundHealth + _boardEvaluator.evaluate(this);
			this.addUnit(new Vec2i(0,_heartTile.y), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			this.addUnit(new Vec2i(_gridDimensions.x-1, _heartTile.y), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			this.addUnit(new Vec2i(_heartTile.x, 0), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			this.addUnit(new Vec2i(_heartTile.x, _gridDimensions.y-1), _prevRoundHealth, Constants.BASIC_UNIT_INT);
		}
		else { //after round 15, there may be exploding units (1/3 chance of exploding units)
			_prevRoundHealth = _prevRoundHealth + _boardEvaluator.evaluate(this);
			int exploding1 = (int) (Math.random()*3);
			if (exploding1 == 0) {
				this.addUnit(new Vec2i(0,_heartTile.y), _prevRoundHealth, Constants.EXPLODING_UNIT_INT);
			}
			else {
				this.addUnit(new Vec2i(0,_heartTile.y), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			}
			
			int exploding2 = (int) (Math.random()*3);
			if (exploding2 == 0) {
				this.addUnit(new Vec2i(_gridDimensions.x-1, _heartTile.y), _prevRoundHealth, Constants.EXPLODING_UNIT_INT);
			}
			else {
				this.addUnit(new Vec2i(_gridDimensions.x-1, _heartTile.y), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			}
			
			int exploding3 = (int) (Math.random()*3);
			if (exploding3 == 0) {
				this.addUnit(new Vec2i(_heartTile.x, 0), _prevRoundHealth, Constants.EXPLODING_UNIT_INT);
			}
			else {
				this.addUnit(new Vec2i(_heartTile.x, 0), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			}
			
			int exploding4 = (int) (Math.random()*3);
			if (exploding4 == 0) {
				this.addUnit(new Vec2i(_heartTile.x, _gridDimensions.y-1), _prevRoundHealth, Constants.EXPLODING_UNIT_INT);
			}
			else {
				this.addUnit(new Vec2i(_heartTile.x, _gridDimensions.y-1), _prevRoundHealth, Constants.BASIC_UNIT_INT);
			}
			
		}
	}
	
	public int getCurrentRound() {
		return _currentRound;
	}
	
	public int getScore() {
		return _score;
	}
	
	public void addScore(int addition) {
		_score += addition;
	}
	
	public void addGold(int addition) {
		_gold += addition;
	}
	
	public void reduceGold(int reduction) {
		_gold -= reduction;
		assert(_gold>=0):"Gold reduced to below 0";
	}
	
	public int getGold() {
		return _gold;
	}
	
	public void gameOver() {
		_gameScreen.setScreen(_score, _currentRound);
	}

	public void onTick(float s) { //this argument is seconds elapsed since start, not since last tick!
		
		if (_tiles[_gridDimensions.x/2][_gridDimensions.y/2].getFull()) { //if the target tile is occupied by a unit
			this.gameOver();
		}
		
		for (int i=0; i<_towers.size(); i++) {
			_towers.get(i).onTick();
		}
		
		if (((int)s) != _prev && ((int)s)%Constants.ROUND_INTERVAL == 0) { //time to spawn enemies
			_currentRound++;
			_towerPanel.setRound(_currentRound);
			this.spawnEnemies();
			_prev = (int) s;
		}
		
		/* COMMENTED OUT BECAUSE NOW POPULATING IS DONE WITHIN EACH UNIT'S MOVE METHOD
		for (int a=0; a<_gridDimensions.x; a++) { //populate neighbors before moving unit
			for (int b=0; b<_gridDimensions.y; b++) {
				this.populateNeighbors(a,b);
			}
		}*/
		
		for (int i=0; i<_units.size();i++) { //do unit's ontick and moves it
			Unit currentUnit = _units.get(i);
			currentUnit.onTick(s);
			if (currentUnit.shouldIMove()) {
				currentUnit.move();
			}
		}

		for (int i=0; i<_projectiles.size(); i++) { //do projectile's ontick
			_projectiles.get(i).onTick();
		}
		
		for (int i=0; i<_afterDestroyTexts.size(); i++) { //do explodetext's ontick
			_afterDestroyTexts.get(i).onTick(s);
		}
		
		for (int i=0; i<_explosions.size(); i++) { //do explosion's ontick
			_explosions.get(i).onTick();
		}
		
		for (int i=0; i<_removalList.size(); i++) { //remove what has to be removed
			Entity entity = _removalList.remove(i);
			if (entity instanceof Unit) {
				((Unit) entity).freeUpTiles();
				_units.remove(entity);
			}
			else if (entity instanceof Projectile) {
				_projectiles.remove(entity);
			}
			else if (entity instanceof Tower) {
				_towers.remove(entity);
				Vec2i tile = ((Tower)entity).getTile();
				_tiles[tile.x][tile.y].setBuilt(false);
				_tiles[tile.x][tile.y].setFull(false);
			}
			else if (entity instanceof AfterDestroyText) {
				_afterDestroyTexts.remove(entity);
			}
			else if (entity instanceof Explosion) {
				_explosions.remove(entity);
			}
		}
		
		_towerPanel.setScore(_score); //set the score of the tower panel to current game score
		_towerPanel.setGold(_gold); //set the gold of the tower panle to current gold count
	}
	
	public void removeEntity(Entity entity) {
		_removalList.add(entity);
	}
	
	public Tile getTile(Vec2i tilePosition) {
		return _tiles[tilePosition.x][tilePosition.y];
	}

	public ArrayList<GraphNode> getValidTiles() {
		ArrayList<GraphNode> validTiles = new ArrayList<GraphNode>();
		for (int x=0; x<_gridDimensions.x; x++) {
			for (int y=0; y<_gridDimensions.y; y++) {
				if ((!_tiles[x][y].getBuilt()) && (!_tiles[x][y].getFull())) {
					validTiles.add(_tiles[x][y]);
				}
			}
		}
		return validTiles;
	}
	
	/*Draws objects contained in the board, by passing their game coordinates to the viewport and returning the screen coordinates*/
	public void draw(Graphics2D aBrush, Viewport viewport) {
		
		//draws tiles
		for (int x=0; x<_gridDimensions.x; x++) {
			for (int y=0; y<_gridDimensions.y; y++) {
				Vec2i tileSize = _tiles[x][y].getSize();
				Vec2i tileLocation = _tiles[x][y].getLocation();
				_tiles[x][y].setLocation(viewport.gameToScreen(new Vec2f(tileLocation.x, tileLocation.y)));
				_tiles[x][y].setSize(viewport.gameToScreenSize(new Vec2f(tileSize.x, tileSize.y)).minus(1,1));
				_tiles[x][y].draw(aBrush);
				_tiles[x][y].setSize(tileSize);
				_tiles[x][y].setLocation(tileLocation);
			}
		}
		
		for (int i=0; i<_units.size(); i++) { //draws units
			Unit unit = _units.get(i);
			Vec2f unitSize = unit.getSize();
			Vec2f unitLocation = unit.getLocation();
			unit.setLocation(new Vec2f(viewport.gameToScreen(unitLocation).x, viewport.gameToScreen(unitLocation).y));
			unit.setSize(new Vec2f(viewport.gameToScreenSize(unitSize).x, viewport.gameToScreenSize(unitSize).y));
			unit.draw(aBrush);
			unit.setSize(unitSize);
			unit.setLocation(unitLocation);
		}
		
		for (int i=0; i<_towers.size(); i++) { //draws towers
			Tower tower = _towers.get(i);
			Vec2f towerSize = tower.getSize();
			Vec2f towerLocation = tower.getLocation();
			tower.setLocation(new Vec2f(viewport.gameToScreen(towerLocation).x, viewport.gameToScreen(towerLocation).y));
			tower.setSize(new Vec2f(viewport.gameToScreenSize(towerSize).x, viewport.gameToScreenSize(towerSize).y).minus(1,1));
			tower.draw(aBrush);
			tower.setSize(towerSize);
			tower.setLocation(towerLocation);
		}
		
		for (int i=0; i<_projectiles.size(); i++) { //draws projectiles
			Projectile projectile = _projectiles.get(i);
			Vec2f projectileSize = projectile.getSize();
			Vec2f projectileLocation = projectile.getLocation();
			projectile.setLocation(new Vec2f(viewport.gameToScreen(projectileLocation).x, viewport.gameToScreen(projectileLocation).y));
			projectile.setSize(new Vec2f(viewport.gameToScreenSize(projectileSize).x, viewport.gameToScreenSize(projectileSize).y));
			projectile.draw(aBrush);
			projectile.setSize(projectileSize);
			projectile.setLocation(projectileLocation);
		}
		
		for (int i=0; i<_afterDestroyTexts.size(); i++) { //draws after destroy texts
			AfterDestroyText explodeText = _afterDestroyTexts.get(i);
			Vec2f explodeTextLocation = explodeText.getLocation();
			explodeText.setLocation(new Vec2f(viewport.gameToScreen(explodeTextLocation).x, viewport.gameToScreen(explodeTextLocation).y));
			explodeText.draw(aBrush);
			explodeText.setLocation(explodeTextLocation);
		}
		
		for (int i=0; i<_explosions.size(); i++) { //draws explosions
			Explosion explosion = _explosions.get(i);
			Vec2f explosionSize = explosion.getSize();
			Vec2f explosionLocation = explosion.getLocation();
			explosion.setLocation(new Vec2f(viewport.gameToScreen(explosionLocation).x, viewport.gameToScreen(explosionLocation).y));
			explosion.setSize(new Vec2f(viewport.gameToScreenSize(explosionSize).x, viewport.gameToScreenSize(explosionSize).y));
			explosion.draw(aBrush);
			explosion.setSize(explosionSize);
			explosion.setLocation(explosionLocation);
		}
	}
	
	public ArrayList<Tower> getTowers() {
		return _towers;
	}
	
	public int getTowerCost(int selectedTower) {
		int cost = 0;
		switch(selectedTower) {
		case Constants.WALL_INT:
			cost = Constants.WALL_COST;
			break;
		case Constants.BASIC_TOWER_INT: 
			cost = Constants.BASIC_TOWER_COST;
			break;
		case Constants.GOLD_TOWER_INT: 
			cost = Constants.GOLD_TOWER_COST;
			break;
		case Constants.ADVANCED_TOWER_INT:
			cost = Constants.ADVANCED_TOWER_COST;
			break;
		default:
			throw new IllegalArgumentException("shouldn't have passed this value into getTowerCost");
		}
		return cost;
	}
	
	/*Checks whether there is still a path to center from the spawning corners*/
	public boolean pathToCenter() {
		PathFinder pathFinder1 = new PathFinder(_tiles[0][_heartTile.y], _tiles[_heartTile.x][_heartTile.y], this.getValidTiles());
		Stack<GraphNode> stack1 = pathFinder1.astar();
		if (stack1.empty()) {
			return false;
		}
		
		PathFinder pathFinder2 = new PathFinder(_tiles[_gridDimensions.x-1][_heartTile.y], _tiles[_heartTile.x][_heartTile.y], this.getValidTiles());
		Stack<GraphNode> stack2 = pathFinder2.astar();
		if (stack2.empty()) {
			return false;
		}
		PathFinder pathFinder3 = new PathFinder(_tiles[_heartTile.x][0], _tiles[_heartTile.x][_heartTile.y], this.getValidTiles());
		Stack<GraphNode> stack3 = pathFinder3.astar();
		if (stack3.empty()) {
			return false;
		}
		PathFinder pathFinder4 = new PathFinder(_tiles[_heartTile.x][_gridDimensions.y-1], _tiles[_heartTile.x][_heartTile.y], this.getValidTiles());
		Stack<GraphNode> stack4 = pathFinder4.astar();
		if (stack4.empty()) {
			return false;
		}
			
		return true;
	}

	public int getGoldTowers() {
		return _goldTowers;
	}
	
}
