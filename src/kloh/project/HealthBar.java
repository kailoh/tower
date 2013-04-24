package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;

import kloh.gameengine.Rectangle;
import cs195n.Vec2i;

public class HealthBar {

	private Rectangle _border, _remaining;
	private Vec2i _size;
	private int _maxLife;
	private int _life;
	
	public HealthBar(Vec2i size, int maxLife, Color barColor) {
		_maxLife = _life = maxLife;
		_size = size;
		_border = new Rectangle(new Color(0,0,0,1), Color.BLACK, 1);
		_remaining = new Rectangle(barColor, null, 0);
		_border.setSize(new Vec2i(size.x+1, size.y+1));
		_remaining.setSize(new Vec2i(size.x, size.y));
	}
	
	public void setLocation(Vec2i location) {
		_border.setLocation(new Vec2i(location.x-1, location.y-1));
		_remaining.setLocation(location);
	}

	public void changeCurrentLife(int currentLife) {
		_life = currentLife;
		int remainingx = (int) (((float)_life/(float)_maxLife)*(float)_size.x);
		_remaining.setSize(new Vec2i(remainingx, _size.y));
	}
	
	public int getLife() {
		return _life;
	}
	
	public void draw (Graphics2D aBrush) {
		_border.draw(aBrush);
		_remaining.draw(aBrush);
	}
	
}
