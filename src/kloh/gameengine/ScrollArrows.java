package kloh.gameengine;

import java.awt.Color;
import java.awt.Graphics2D;

import cs195n.Vec2i;

public class ScrollArrows {
	private Text _up;
	private Text _down;
	private Text _left;
	private Text _right;
	private int _inside; //0 if the mouse is not in any "arrow" zones, 1 in up, 2 in down, 3 in left, 4 in right
	
	public ScrollArrows(Vec2i UITopLeft, Vec2i UIDimensions) {
		_up = new Text("Helvetica", "bold", 22, "^", Color.WHITE);
		_down = new Text("Helvetica", "bold", 18, "v", Color.WHITE);
		_left = new Text("Helvetica", "bold", 18, "<", Color.WHITE);
		_right = new Text("Helvetica", "bold", 18, ">", Color.WHITE);
		_up.setLocation(new Vec2i(UITopLeft.x + UIDimensions.x/2 + 11, UITopLeft.y + 4));
		_down.setLocation(new Vec2i(UITopLeft.x + UIDimensions.x/2 + 9, UITopLeft.y + UIDimensions.y - 26));
		_left.setLocation(new Vec2i(UITopLeft.x + 4, UITopLeft.y + UIDimensions.y/2 + 9));
		_right.setLocation(new Vec2i(UITopLeft.x + UIDimensions.x - 22, UITopLeft.y + UIDimensions.y/2 + 9));
	}
	
	public void draw(Graphics2D aBrush) {
		_up.draw(aBrush);
		_down.draw(aBrush);
		_left.draw(aBrush);
		_right.draw(aBrush);
	}
	
	public int getInside() {
		return _inside;
	}

	public void setLocation(Vec2i UITopLeft, Vec2i UIDimensions) {
		_up.setLocation(new Vec2i(UITopLeft.x + UIDimensions.x/2 + 11, UITopLeft.y + 4));
		_down.setLocation(new Vec2i(UITopLeft.x + UIDimensions.x/2 + 9, UITopLeft.y + UIDimensions.y - 26));
		_left.setLocation(new Vec2i(UITopLeft.x + 4, UITopLeft.y + UIDimensions.y/2 + 9));
		_right.setLocation(new Vec2i(UITopLeft.x + UIDimensions.x - 22, UITopLeft.y + UIDimensions.y/2 + 9));
	}
	
	public void onTick() {
		if (_inside == 1) {
			_up.setColor(Color.gray);
		}
		else if (_inside == 2) {
			_down.setColor(Color.gray);
		}
		else if (_inside == 3) {
			_left.setColor(Color.gray);
		}
		else if (_inside == 4) {
			_right.setColor(Color.gray);
		}
		else {
			_up.setColor(Color.white);
			_down.setColor(Color.white);
			_left.setColor(Color.white);
			_right.setColor(Color.white);
		}
	}
	
	public void onMouseMoved(Vec2i location) {
		if (location.x > _right.getLocation().x - 10 && location.x < _right.getLocation().x + 30
				&& location.y > _right.getLocation().y - 10 && location.y < _right.getLocation().y + 30) {
			_inside = 4;
		}
		else if (location.x > _left.getLocation().x - 10 && location.x < _left.getLocation().x + 30
				&& location.y > _left.getLocation().y - 10 && location.y < _left.getLocation().y + 30) {
			_inside = 3;
		}
		else if (location.x > _down.getLocation().x - 10 && location.x < _down.getLocation().x + 30
				&& location.y > _down.getLocation().y - 10 && location.y < _down.getLocation().y + 30) {
			_inside = 2;
		}
		else if (location.x > _up.getLocation().x - 10 && location.x < _up.getLocation().x + 30
				&& location.y > _up.getLocation().y - 10 && location.y < _up.getLocation().y + 30) {
			_inside = 1;
		}
		else {
			_inside = 0;
		}

	}

}
