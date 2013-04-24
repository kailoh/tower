package kloh.gameengine;

import java.awt.Graphics2D;

import cs195n.Vec2f;

public interface Entity {

	public void draw(Graphics2D aBrush);
	public void setLocation(Vec2f vec);
	public void setSize(Vec2f vec);
	public Vec2f getLocation();
	public Vec2f getSize();
	
}
