package kloh.project;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kloh.gameengine.Entity;
import kloh.gameengine.Sound;
import cs195n.Vec2f;

public class Explosion implements Entity {
	
	private int _current;
	private BufferedImage[] _imageList;
	private Vec2f _location;
	private Vec2f _size;
	private Board _board;
	
	public Explosion(BufferedImage[] imageList, Board board, Vec2f location, Vec2f size) {
		_current = 0;
		_imageList = imageList;
		_board = board;
		_location = location;
		_size = size;
		Sound backgroundSound = new Sound("explosionboom.wav");
		backgroundSound.play();
	}
	
	public void onTick() {
		_current++;
		if (_current == 24) {
			_board.removeEntity(this);
		}
	}

	@Override
	public void setLocation(Vec2f vec) {
		_location = vec;
	}

	@Override
	public void setSize(Vec2f vec) {
		_size = vec;
	}

	@Override
	public Vec2f getLocation() {
		return _location;
	}

	@Override
	public Vec2f getSize() {
		return _size;
	}	
	
	@Override
	public void draw (Graphics2D aBrush) {
		if (_current <= 24) {
			aBrush.drawImage(_imageList[_current], (int) _location.x, (int) _location.y, (int) _size.x, (int) _size.y, null);
		}
	}

}
