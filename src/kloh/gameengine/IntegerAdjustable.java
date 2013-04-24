package kloh.gameengine;

import cs195n.Vec2i;

public interface IntegerAdjustable {
	public Vec2i getSize();
	public void setSize(Vec2i size);
	public void setLocation(Vec2i location);
	public Vec2i getLocation();
}
