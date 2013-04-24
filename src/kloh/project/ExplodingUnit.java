package kloh.project;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import kloh.gameengine.Animation;
import kloh.gameengine.Dir;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class ExplodingUnit extends Unit {
	
	private int _explodingCount;
	private int _leftOverTime;

	public ExplodingUnit(Vec2i current, Board board,
			HashMap<Dir, Animation> animationMap, int health, int seconds) {
		super(current, board, animationMap, health);
		_leftOverTime = ((int)(Math.random()*Constants.EXPLODING_UNIT_VARIABLE_TIME)) + Constants.EXPLODING_UNIT_TIME;
		_explodingCount = seconds + _leftOverTime;
	}

	@Override
	public void specialOnTick(float s) {
		_leftOverTime = (int)(_explodingCount - s);
		if (_leftOverTime <= 0) {
			this.explode();
		}
	}
	
	public int getTime() {
		return _leftOverTime;
	}
	
	/*makes the game slow*/
	public void explode() {
		_board.removeEntity(this);
		ArrayList<Unit> units = _board.getUnits();
		for (int i=0; i<units.size(); i++) {
			if (units.get(i).getLocation().dist(_location) < 2) {
				_board.removeEntity(units.get(i));
			}
		}
		ArrayList<Tower> towers = _board.getTowers();
		for (int j=0; j<towers.size(); j++) {
			if (_location.dist(new Vec2f(towers.get(j).getTile())) < 2) {
				_board.removeEntity(towers.get(j));
			}
		}
		_board.addExplosion(_location, _size);
	}
	
	@Override
	public void draw(Graphics2D aBrush) {
		super.draw(aBrush);
	}

}
