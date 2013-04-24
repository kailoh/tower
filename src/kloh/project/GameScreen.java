package kloh.project;

import java.awt.Graphics2D;

import kloh.gameengine.Application;
import kloh.gameengine.Screen;
import kloh.gameengine.Sound;
import kloh.gameengine.Viewport;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class GameScreen implements Screen{

	private Application _application;
	private Viewport _viewport;
	private Vec2i _UIViewportTopLeft;
	private Vec2i _UIViewportDimensions;
	private Vec2i _UITowerPanelDimensions;
	private Vec2i _UITowerPanelTopLeft;
	private Vec2i _UIUpgradePanelTopLeft;
	private Vec2i _UIUpgradePanelDimensions;
	private TowerPanel _towerPanel;
	private UpgradePanel _upgradePanel;
	private Vec2i _dragStart;
	private float _secondsElapsed;
	private HealthLayer _healthLayer;
	private ExplodingLayer _explodingLayer;
	private Sound _backgroundSound;
	
	public GameScreen(Application application, int difficulty) {
		_backgroundSound = new Sound("maintheme.wav");
		_backgroundSound.loop();
		Vec2i gridDimensions;
		switch(difficulty) {
		case 0: gridDimensions = new Vec2i(20,20);
		break;
		case 1: gridDimensions = new Vec2i(25,25);
		break;
		case 2: gridDimensions = new Vec2i(25,25);
		break;
		default: throw new IllegalArgumentException("Difficulty should be an int from 0 to 2 inclusive");
		}
		
		_dragStart = new Vec2i(0,0);
		_application = application;
		_UIViewportTopLeft = new Vec2i(0,0);
		_UIViewportDimensions = new Vec2i(_application.getSize().x-Constants.TOWER_PANEL_WIDTH, _application.getSize().y-Constants.UPGRADE_PANEL_HEIGHT);
		_secondsElapsed = 0;
				
		_UIUpgradePanelDimensions = new Vec2i(_UIViewportDimensions.x,Constants.UPGRADE_PANEL_HEIGHT);
		_UIUpgradePanelTopLeft = new Vec2i(0, _application.getSize().y-Constants.UPGRADE_PANEL_HEIGHT);
		_upgradePanel = new UpgradePanel(_UIUpgradePanelTopLeft, _UIUpgradePanelDimensions);
		
		_UITowerPanelDimensions = new Vec2i(Constants.TOWER_PANEL_WIDTH, _application.getSize().y);
		_UITowerPanelTopLeft = new Vec2i(_UIViewportDimensions.x,0);
		_towerPanel = new TowerPanel(_UITowerPanelTopLeft, _UITowerPanelDimensions);
		
		Board board = new Board(gridDimensions, _towerPanel, _upgradePanel, this, difficulty);
		
		
		_towerPanel.setBoard(board); //the towerpanel has to know about the board too
		_upgradePanel.setBoard(board); //the upgradepanel has to know about the board too
		
		_viewport = new Viewport(_UIViewportTopLeft,_UIViewportDimensions, new Vec2f(0,0),
								board, new Vec2i(25, 25), gridDimensions);
		_healthLayer = new HealthLayer(board.getUnits(), _viewport);
		_explodingLayer = new ExplodingLayer(board.getUnits(), _viewport);
	}
	
	public void addUnit(Unit unit) {
		_healthLayer.addNewUnit(unit);
		if (unit instanceof ExplodingUnit) {
			_explodingLayer.addNewUnit((ExplodingUnit)unit);
		}
	}
	
	@Override
	public void onDraw(Graphics2D g) {
		_viewport.draw(g);
		_healthLayer.draw(g);
		_explodingLayer.draw(g);
		_towerPanel.draw(g);
		_upgradePanel.draw(g);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		_secondsElapsed = _secondsElapsed + (nanosSincePreviousTick/1000000000f);
		_viewport.onTick(_secondsElapsed);
		_towerPanel.setTime((int)_secondsElapsed);
	}
	
	public void setScreen(int score, int round) {
		_backgroundSound.stop();
		_application.setScreen(new VictoryScreen(_application, score, round, (int)_secondsElapsed));
	}

	@Override
	public void onDown(int keycode) {
		switch (keycode) {
		case 37:
			_viewport.move(new Vec2f(-1, 0));
			break;
		case 38:
			_viewport.move(new Vec2f(0, -1));
			break;
		case 39:
			_viewport.move(new Vec2f(1, 0));
			break;
		case 40:
			_viewport.move(new Vec2f(0, 1));
			break;
		case 27:
			_backgroundSound.stop();
			_application.setScreen(new MenuScreen(_application));
			break;
		case Constants.WALL_KEYCODE:
			_towerPanel.setSelectedTower(Constants.WALL_INT);
			break;
		case Constants.BASIC_TOWER_KEYCODE:
			_towerPanel.setSelectedTower(Constants.BASIC_TOWER_INT);
			break;
		case Constants.GOLD_TOWER_KEYCODE:
			_towerPanel.setSelectedTower(Constants.GOLD_TOWER_INT);
			break;
		case Constants.ADVANCED_TOWER_KEYCODE:
			_towerPanel.setSelectedTower(Constants.ADVANCED_TOWER_INT);
			break;
		case 32:
			_upgradePanel.shortcutTyped();
			break;
		}
		
	}

	@Override
	public void onUp(int keycode) {
	}

	@Override
	public void onRepeated(int keycode) {
		if (keycode == 37) { //left
			_viewport.move(new Vec2f(-1, 0));
		}
		if (keycode == 38) { //up
			_viewport.move(new Vec2f(0, -1));
		}
		if (keycode == 39) { //right
			_viewport.move(new Vec2f(1, 0));
		}
		if (keycode == 40) { //down
			_viewport.move(new Vec2f(0, 1));
		}
	}

	@Override
	public void onTyped(int keycode) {
	}

	@Override
	public void onResize(Vec2i newSize) {
		_viewport.setBound(new Vec2i(0,0), new Vec2i(newSize.x-Constants.TOWER_PANEL_WIDTH, newSize.y-Constants.UPGRADE_PANEL_HEIGHT));
		//_transparentLayer.setBound(new Vec2i(0,0), newSize);
		_UIViewportDimensions = newSize.minus(Constants.TOWER_PANEL_WIDTH, Constants.UPGRADE_PANEL_HEIGHT);
		_UITowerPanelDimensions = new Vec2i(Constants.TOWER_PANEL_WIDTH, newSize.y);
		_UITowerPanelTopLeft = new Vec2i(_UIViewportDimensions.x, 0);
		_towerPanel.setBound(_UITowerPanelTopLeft, _UITowerPanelDimensions);
		_UIUpgradePanelDimensions = new Vec2i(_UIViewportDimensions.x,Constants.UPGRADE_PANEL_HEIGHT);
		_UIUpgradePanelTopLeft = new Vec2i(0, newSize.y-Constants.UPGRADE_PANEL_HEIGHT);
		_upgradePanel.setBound(_UIUpgradePanelTopLeft, _UIUpgradePanelDimensions);
	}

	@Override
	public void onMouseClicked(int clickCount, Vec2i location) {
		//System.out.println("_UIUpgradePanelTopLeft: " + _UIUpgradePanelTopLeft + " | _UIUpgradePanelDimensions: " + _UIUpgradePanelDimensions);
		//System.out.println(_UIViewportTopLeft + " | " + _UIViewportDimensions + " | " + _UITowerPanelTopLeft + " | " + _UITowerPanelDimensions);
		if (location.x > _UIViewportTopLeft.x && location.x < _UIViewportTopLeft.x + _UIViewportDimensions.x
		 && location.y > _UIViewportTopLeft.y && location.y < _UIViewportTopLeft.y + _UIViewportDimensions.y) {
			_viewport.onMouseClicked(new Vec2i(location.x, location.y));
		}
		
		else if (location.x > _UITowerPanelTopLeft.x && location.x < _UITowerPanelTopLeft.x + _UITowerPanelDimensions.x
		&& location.y > _UITowerPanelTopLeft.y && location.y < _UITowerPanelTopLeft.y + _UITowerPanelDimensions.y) {
			_towerPanel.onMouseClicked(new Vec2i(location.x, location.y));
		}
		else if (location.x > _UIUpgradePanelTopLeft.x && location.x < _UIUpgradePanelTopLeft.x + _UIUpgradePanelDimensions.x
		&& location.y > _UIUpgradePanelTopLeft.y && location.y < _UIUpgradePanelTopLeft.y + _UIUpgradePanelDimensions.y) {
			_upgradePanel.onMouseClicked(new Vec2i(location.x, location.y));
		}
	}

	@Override
	public void onMousePressed() {
	}

	@Override
	public void onMouseReleased() {
	}

	@Override
	public void onMouseMoved(Vec2i location) {
		if (location.x < _UIViewportDimensions.x) {
			_viewport.onMouseMoved(location);
		}
		else if (location.x > _UITowerPanelTopLeft.x) {
			_towerPanel.onMouseMoved(location);
		}
	}

	@Override
	public void onMouseWheelForward() {
		_viewport.zoomIn(2);
	}

	@Override
	public void onMouseWheelBackward() {
		_viewport.zoomIn(0.5f);
	}
	
	@Override
	public void onMouseDragged(Vec2i location) {
		_viewport.center(new Vec2i(_dragStart.x - location.x, _dragStart.y - location.y));
		_dragStart = location;
	}
	
	@Override
	public void onDragStart(Vec2i location) {
		_dragStart = location;
	}

	@Override
	public void onDragEnd(Vec2i location) {
	}

}
