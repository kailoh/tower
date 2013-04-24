package kloh.gameengine;

import java.awt.Graphics2D;

import kloh.project.Constants;
import cs195n.Vec2i;

/*An abstract game tile that can be extended to a real tile in the game code*/
public abstract class GameTile extends GraphNode implements IntegerAdjustable {
	
	private boolean _built;
	protected Vec2i _size;
	protected Vec2i _location;
	private boolean _full;
	protected Rectangle _rectangle;
	private int _selected; //-1 is RED BORDER, 0 is NO BORDER, 1 is GREEN BORDER
	
	public GameTile(Vec2i location, Vec2i size) {
		_built = false;
		_full = false;
		_location = location;
		_size = size;
		_selected = 0;
		/* uncomment for debug
		Color color = Color.ORANGE;
		if (!_built && !_full) {
			color = Color.green;
		}
		if (_built) {
			color = Color.blue;
		}
		if (_full) {
			color = Color.red;
		}*/
		_rectangle = new Rectangle(Constants.UNSELECTED_COLOR, Constants.UNSELECTED_COLOR, 1);
	}
	
	public void setSize(Vec2i size) {
		_size = size;
		_rectangle.setSize(_size);
	}
	
	public Vec2i getSize() {
		return _size;
	}
	
	public void setSelected(int toSet) {
		_selected = toSet;
		switch (_selected) {
		case 1:
			_rectangle.setBorderColor(Constants.SELECTED_COLOR);
			break;
		case -1:
			_rectangle.setBorderColor(Constants.INVALID_COLOR);
			break;
		case 0:
			_rectangle.setBorderColor(Constants.UNSELECTED_COLOR);
			break;
		default:
			throw new IllegalArgumentException("Wrong int passed into setselected of game tile");
		}
	}
	
	public int getSelected() {
		return _selected;
	}
	
	public void setFull(boolean full) {
		_full = full;
	}
	
	public boolean getFull() {
		return _full;
	}
	
	public void setBuilt(boolean built) {
		_built = built;
	}
	
	public boolean getBuilt() {
		return _built;
	}
	
	@Override
	public void setLocation(Vec2i location) {
		_location = location;
		_rectangle.setLocation(_location);
	}
	
	@Override
	public Vec2i getLocation() {
		return _location;
	}

	
	public void draw(Graphics2D aBrush) {
		_rectangle.draw(aBrush);
	}

}
