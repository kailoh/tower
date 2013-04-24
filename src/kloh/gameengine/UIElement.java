package kloh.gameengine;

import cs195n.Vec2i;

public interface UIElement {
	
	public void setBound(Vec2i topLeft, Vec2i uiDimensions);
	public void draw(java.awt.Graphics2D aBrush);
	
}