package kloh.gameengine;

import cs195n.Vec2f;

public interface FloatAdjustable {
	public Vec2f getSize();
	public void setSize(Vec2f size);
	public void setLocation(Vec2f location);
	public Vec2f getLocation();
}
