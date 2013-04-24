package kloh.gameengine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cs195n.Vec2f;

public class Animation {
	
	private int _current;
	private BufferedImage[] _imageList;
	private boolean _active;
	private Vec2f _location;
	private Vec2f _size;
	
	public Animation(BufferedImage[] imageList) {
		_imageList = imageList;
		_current = 0;
		_active = false;
	}
	
	public void onTick() {
		if (_active) {
			_current++;
		}
	}	
	
	public void start() {
		_active = true;
	}
	
	public void stop() {
		_active = false;
		_current = 0;
	}
	
	public void setLocation(Vec2f location) {
		_location = location;
	}
	
	public void setSize(Vec2f size) {
		_size = size;
	}
	
	public void draw(Graphics2D aBrush) {
		BufferedImage currentImage = _imageList[_current % _imageList.length];
		aBrush.drawImage(currentImage, (int) _location.x, (int) _location.y, (int) _size.x, (int) _size.y, null);
	}
	
}
