package kloh.project;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kloh.gameengine.GameTile;
import cs195n.Vec2i;

public class Tile extends GameTile {
	
	private BufferedImage _tileImage;
	
	public Tile(Vec2i location, Vec2i size, BufferedImage subImage) {
		super(location, size);
		_tileImage = subImage;
	}

	public void draw(Graphics2D aBrush) {
		super.draw(aBrush);
		aBrush.drawImage(_tileImage, _location.x+1, _location.y+1, _size.x-1, _size.y-1, null);
		
		//uncomment the following line for debugging
		
	}

}
