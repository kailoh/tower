package kloh.gameengine;

import java.awt.Color;
import java.awt.Graphics2D;

import cs195n.Vec2f;
import cs195n.Vec2i;

public abstract class MovableUnit implements FloatAdjustable, Entity {

	protected Vec2f _location;
	protected Vec2f _size;
	private boolean _selected; //if unit is selected, this evaluates to true.
	
	protected Rectangle _rectangle;

	public MovableUnit(Vec2f location, Vec2f size) {
		_location = location;
		_size = size;
		_rectangle = new Rectangle(Color.white, new Color(0,0,0,1), 2);
		_rectangle.setLocation(new Vec2i((int)_location.x, (int)_location.y));
		_rectangle.setSize(new Vec2i((int)_size.x, (int)_size.y));
	}

	public void setSelected(boolean selected) {
		_selected = selected;
		if (_selected) {
			_rectangle.setBorderColor(Color.BLACK);
		}
		else {
			_rectangle.setBorderColor(new Color(0,0,0,1));
		}
	}

	public void setSize(Vec2f size) {
		_size = size;
		_rectangle.setSize(new Vec2i((int)_size.x, (int)_size.y));
	}
	
	public Vec2f getSize() {
		return _size;
	}
	
	public void setLocation(Vec2f location) {
		_location = location;
		_rectangle.setLocation(new Vec2i((int)_location.x, (int)_location.y));
	}
	
	public Vec2f getLocation() {
		return _location;
	}
	
	public void draw(Graphics2D aBrush) {
		 _rectangle.draw(aBrush);
	}
	
}
