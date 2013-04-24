package kloh.gameengine;

import java.awt.Graphics2D;
import cs195n.*;

public interface Screen {
	public void onDraw(Graphics2D g);
	public void onTick(long nanosSincePreviousTick);
	public void onDown(int keycode);
	public void onUp(int keycode);
	public void onRepeated(int keycode);
	public void onTyped(int keycode);
	public void onResize(Vec2i newSize);
	public void onMouseClicked(int clickCount, Vec2i location);
	public void onMousePressed();
	public void onMouseReleased();
	public void onMouseDragged(Vec2i location);
	public void onMouseMoved(Vec2i location);
	public void onMouseWheelForward();
	public void onMouseWheelBackward();
	public void onDragStart(Vec2i location);
	public void onDragEnd(Vec2i location);
}
