package kloh.gameengine;

import java.awt.Graphics2D;

import cs195n.Vec2f;

public interface GameWorld {
	public void onMouseClicked(Vec2f clickLocation);
	public void onMouseMoved(Vec2f location);
	public void onTick(float s);
	public void draw(Graphics2D aBrush, Viewport viewport);
}
