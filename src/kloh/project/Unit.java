package kloh.project;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Stack;

import kloh.gameengine.Animation;
import kloh.gameengine.Dir;
import kloh.gameengine.GraphNode;
import kloh.gameengine.MovableUnit;
import kloh.gameengine.PathFinder;
import cs195n.Vec2f;
import cs195n.Vec2i;

public abstract class Unit extends MovableUnit {
	
	private HashMap<Dir,Animation> _animationMap;
	private Dir _currentAnimation;
	protected Board _board;
	protected Vec2i _current; //current tile
	protected Vec2i _next; //next tile
	protected Vec2i _destination; //destination tile
	protected Vec2i _start; //starting tile of a movement journey
	private Stack<GraphNode> _stack;
	private int _health;

	public Unit(Vec2i current, Board board, HashMap<Dir,Animation> animationMap, int health) {
		super(new Vec2f(current.x+Constants.UNIT_OFFSET.x,current.y+Constants.UNIT_OFFSET.y), Constants.UNIT_SIZE);
		_board = board;
		_animationMap = animationMap;
		_currentAnimation = Dir.N;
		_current = current;
		_start = current;
		_destination = current;
		_next = current;
		_health = health;
	}
	
	public void freeUpTiles() {
		_board.getTile(_next).setFull(false);
		_board.getTile(_current).setFull(false);
	}
	
	public Vec2i getNextTile() {
		return _next;
	}
	
	public boolean stopped() {
		if (_current.equals(_next) && _current.equals(_destination) && _current.equals(_start)) {
			return true;
		}
		else return false;
	}
	
	public void reduceHealth(int reduction) {
		_health = _health - reduction;
	}
	
	public int getHealth() {
		return _health;
	}
	
	public boolean checkPath(Vec2i current, Vec2i destination) {
		
		//populate tiles here -- we only care that the immediate tiles DO NOT contain enemies blocking the way
		for (int x=0; x<_board.getGridDimensions().x; x++) {
			for (int y=0; y<_board.getGridDimensions().y; y++) {
				_board.populateNeighbors(x, y, true);
			}
		}
		_board.populateNeighbors(current.x, current.y, false);

		PathFinder pathFinder = new PathFinder(_board.getTile(current), _board.getTile(destination), _board.getValidTiles());
		Stack<GraphNode> stack = pathFinder.astar();
		_stack = stack;
		if (!_stack.empty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean shouldIMove() {
		if (!_current.equals(_destination)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void atStartMove() {
		if (_current != _destination) {
			this.checkPath(_current, _destination);
			if (_stack.size() > 0) { //if checkpath produces a path that is moveable
				Vec2i next = _stack.pop().getLocation();
				if (!_board.getTile(next).getFull()) {
					this.atStartCanMove(next);
				}
				else {
					this.atStartCannotMove();
				}
			}
			else {
				this.atStartCannotMove();
			}
		}
		else {
			this.atStartCannotMove();
		}
		
	}
	
	public void atStartCanMove(Vec2i next) {
		_next = next;
		_board.getTile(_next).setFull(true);
		this.moveInRightDirection();
	}
	
	public void atStartCannotMove() {
		_destination = _current;
		_start = _current;
	}
	
	public void move() {
		float arriveOffset = (float)0.25;
		_location = new Vec2f((float) (Math.round(_location.x*100.0)/100.0), (float) (Math.round(_location.y*100.0)/100.0));
		if (_location.x == _start.x + arriveOffset && _location.y == _start.y + arriveOffset) { //at start
			this.atStartMove();
		}
		else { 
			this.notAtStartMove(arriveOffset);
		}
	}
	
	public void notAtStartMove(float offset) {
		Vec2f nextLocation = new Vec2f((float)(_next.x + 0.25), (float)(_next.y + 0.25));
		if (_location.equals(nextLocation)) { 
			this.arriveNextTileMove();
		}
		else {
			this.moveInRightDirection();
		}
	}
					
	public void arriveNextTileMove() {
		if (_next.equals(_destination)) { //if unit has arrived at destination
			this.arriveDestination();
		}
		else { //if unit has not arrived at destination
			this.notArriveDestination();
		}
	}
	
	public void arriveDestination() {
		_board.getTile(_current).setFull(false);
		_current = _destination;
		_start = _current;
		//_animationMap.get(_currentAnimation).stop();
	}
	
	public void notArriveDestination() {
		if (_stack.size() > 0) {
			Vec2i next = _stack.pop().getLocation();
			if (!_board.getTile(next).getFull()) {
				this.nextTileEmpty(next);
			}
			else {
				this.nextTileFull(next);
			}
		}
	}
	
	public void nextTileEmpty(Vec2i next) {
		_board.getTile(_current).setFull(false);
		_current = _next;
		_next = next;
		_board.getTile(_next).setFull(true);
		this.moveInRightDirection();
	}
	
	public void nextTileFull(Vec2i next) {
		_board.getTile(_current).setFull(false);
		_current = _next;
		_start = _current;
		_animationMap.get(_currentAnimation).stop();
	}


	public void setDestination(Vec2i destination) {
		_destination = destination;
	}
	

	
	public abstract void specialOnTick(float s);
	
	public void onTick(float s) {
		_animationMap.get(_currentAnimation).onTick();
		this.specialOnTick(s);
	}

	public void moveInRightDirection() {
		if (_location.x != _next.x + 0.25 || _location.y != _next.y + 0.25) { //|| _location.x != _next.x - 0.25 || _location.y != _next.y - 0.25) {
			if (_next.x + 0.25 > _location.x) { // right
				_location = new Vec2f((float) (Math.round((_location.x + Constants.UNIT_MOVE_STEP) * 100.0) / 100.0), (float) (Math.round(_location.y * 100.0) / 100.0));
				if (_currentAnimation != Dir.E) {
					_animationMap.get(_currentAnimation).stop();
					_currentAnimation = Dir.E;
					_animationMap.get(_currentAnimation).start();
				}
			} 
			else if (_next.y + 0.25 > _location.y) { // down
				_location = new Vec2f((float) (Math.round((_location.x) * 100.0) / 100.0), (float) (Math.round((_location.y + Constants.UNIT_MOVE_STEP) * 100.0) / 100.0));
				if (_currentAnimation != Dir.S) {
					_animationMap.get(_currentAnimation).stop();
					_currentAnimation = Dir.S;
					_animationMap.get(_currentAnimation).start();
				}
			} 
			else if (_next.x + 0.25 < _location.x) { // left
				_location = new Vec2f((float) (Math.round((_location.x - Constants.UNIT_MOVE_STEP) * 100.0) / 100.0), (float) (Math.round(_location.y * 100.0) / 100.0));
				if (_currentAnimation != Dir.W) {
					_animationMap.get(_currentAnimation).stop();
					_currentAnimation = Dir.W;
					_animationMap.get(_currentAnimation).start();
				}
			} 
			else if (_next.y + 0.25 < _location.y) { // up
				_location = new Vec2f((float) (Math.round((_location.x) * 100.0) / 100.0), (float) (Math.round((_location.y - Constants.UNIT_MOVE_STEP) * 100.0) / 100.0));
				if (_currentAnimation != Dir.N) {
					_animationMap.get(_currentAnimation).stop();
					_currentAnimation = Dir.N;
					_animationMap.get(_currentAnimation).start();
				}
			}
		}
	}
	
	public void setSize(Vec2f size) {
		super.setSize(size);
		_animationMap.get(_currentAnimation).setSize(size);
	}
	
	public void setLocation(Vec2f location) {
		super.setLocation(location);
		_animationMap.get(_currentAnimation).setLocation(location);
	}


	public void draw(Graphics2D aBrush) {
		//super.draw(aBrush);
		_animationMap.get(_currentAnimation).draw(aBrush);
	}


}