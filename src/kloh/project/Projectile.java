package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import kloh.gameengine.Entity;
import kloh.gameengine.FloatAdjustable;
import kloh.gameengine.Rectangle;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class Projectile implements FloatAdjustable, Entity {
	
	private Board _board;
	private Vec2f _location;
	private BufferedImage _image;
	private Vec2f _size;
	private Vec2f _velocity;
	private Rectangle _rectangle;
	private int _damage; //how many HP the projectile does in damage upon hit
	
	public Projectile(Vec2f initialLocation, BufferedImage image, Vec2f velocity, Board board, int damage) {
		_board = board;
		_damage = damage;
		_location = initialLocation;
		_image = image;
		_size = Constants.PROJECTILE_SIZE;
		_velocity = velocity;
		_rectangle = new Rectangle(Constants.PROJECTILE_COLOR, Constants.PROJECTILE_COLOR, 0, new Vec2i((int)_location.x,(int)_location.y), new Vec2i((int)_size.x,(int)_size.y));
	}
	
	public void move() {
		_location = _location.plus(_velocity);
		_rectangle.setLocation(new Vec2i((int)_location.x,(int)_location.y));
	}
	
	
	@Override
	public Vec2f getSize() {
		return _size;
	}

	@Override
	public void setSize(Vec2f size) {
		_size = size;
		_rectangle.setSize(new Vec2i((int)_size.x,(int)_size.y));
	}

	@Override
	public void setLocation(Vec2f location) {
		_location = location;		
		_rectangle.setLocation(new Vec2i((int)_location.x,(int)_location.y));
	}

	@Override
	public Vec2f getLocation() {
		return _location;
	}
	
	public void onTick() {
		if (this.didProjectileHitBoardEdge()) {
			_board.removeEntity(this);
		}
		else {
			Unit unit = this.didProjectileHitUnit();
			if (unit != null) {
				_board.removeEntity(this);
				unit.reduceHealth(_damage);
				if (unit.getHealth() <= 0) {
					int scoreadd = 0;
					if (unit instanceof BasicUnit) {
						scoreadd = Constants.BASIC_UNIT_SCORE;
					}
					else if (unit instanceof ExplodingUnit) {
						scoreadd = Constants.EXPLODING_UNIT_SCORE;
					}
					assert(scoreadd>0):"must add more than 1 point";
					_board.addScore(scoreadd);
					_board.addGold(1);
					_board.removeEntity(unit);
					_board.addExplodeText(unit.getLocation(), scoreadd);
				}
			}
			else {
				this.move();
			}
		}
	}
	
	public Unit didProjectileHitUnit() { //2 AABs colliding
		ArrayList<Unit> units = _board.getUnits();
		for (int i=0; i<units.size();i++) {
			Vec2f unitLocation = units.get(i).getLocation();
			Vec2f unitSize = units.get(i).getSize();			
			if (_location.x <= unitLocation.x + unitSize.x && _location.x + _size.x >= unitLocation.x && _location.y <= unitLocation.y + unitSize.y && _location.y + _size.y >= unitLocation.y) {
				return units.get(i);
			}
		}
		return null;
	}
	
	public boolean didProjectileHitBoardEdge() {
		Vec2i gridDimensions = _board.getGridDimensions();
		if (_location.x < 0 || _location.x > gridDimensions.x || _location.y < 0 || _location.y > gridDimensions.y) {
			return true;
		}
		return false;
	}
	
	
	public void draw(Graphics2D aBrush) {
		//aBrush.drawImage(_image, (int) _location.x, (int) _location.y, (int) _size.x, (int) _size.y, null);
		_rectangle.draw(aBrush);
	}

}
