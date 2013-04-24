package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import kloh.gameengine.Entity;
import kloh.gameengine.Rectangle;
import kloh.gameengine.Sound;
import cs195n.Vec2f;
import cs195n.Vec2i;

public abstract class Tower implements Entity {
	
	private Rectangle _towerRect;
	private Vec2f _location;
	private Vec2f _size;
	private Vec2i _tile;
	private Board _board;
	private int _counter;
	private Sound _sound;
	
	public Tower(Vec2i location, Vec2f size, Board board, Color color) {
		_board = board;
		_counter = 1;
		_towerRect = new Rectangle(color, Color.BLACK, 1);
		_tile = location;
		_location = new Vec2f(location).plus(new Vec2f(1,1).minus(size).sdiv(2));
		_size = size;
		_sound = new Sound("bow_arrow.wav");
	}
	
	public Vec2i getTile() {
		return _tile;
	}
	
	public void setLocation(Vec2f location) {
		_location = location;
		_towerRect.setLocation(new Vec2i((int)location.x, (int)location.y));
	}
	
	public Vec2f getLocation() {
		return _location;
	}
	
	public void setSize(Vec2f size) {
		_size = size;
		_towerRect.setSize(new Vec2i((int)size.x, (int)size.y));
	}
	
	public Vec2f getSize() {
		return _size;
	}
	
	public abstract void onTick();
	
	public void mine(int mineInterval, int mineReward) {
		if (_counter % mineInterval == 0) {
			_board.addGold(mineReward);
			_counter = 1;
		}
		else {
			_counter++;
		}
	}
	
	public void fire(int fireInterval, int range, int damage) {
		// find nearest unit
		if (_counter % fireInterval == 0) {
			Vec2i nearest = new Vec2i(-1,-1); //initialize to (-1,-1);
			float closestdist = Float.MAX_VALUE;
			ArrayList<Unit> units = _board.getUnits();
			for (int j=0; j<units.size(); j++) {
				Vec2i next = units.get(j).getNextTile();
				//calculate distance between unit's next tile and tower
				float dist = (float) Math.sqrt(Math.pow(next.x-_tile.x, 2) + Math.pow(next.y-_tile.y, 2));
				if (dist<closestdist) {
					nearest = next;
					closestdist = dist;
				}
			}

			if (closestdist<6) {
				Vec2f offset = (_size.minus(Constants.PROJECTILE_SIZE)).sdiv(2);
				_board.addProjectile(_location.plus(offset), new Vec2f(nearest).plus(0.5f, 0.5f).minus(_location.plus(offset)).normalized().sdiv(20f), damage);
				_sound.play();
			}
			_counter = 1;
		}
		else {
			_counter++;
		}

	}
	
	public void draw(Graphics2D aBrush) {
		_towerRect.draw(aBrush);
	}

}
