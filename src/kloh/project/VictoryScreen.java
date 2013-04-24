package kloh.project;

import java.awt.Graphics2D;

import cs195n.Vec2i;
import kloh.gameengine.Application;
import kloh.gameengine.Screen;
import kloh.gameengine.Sound;

public class VictoryScreen implements Screen {
	
	private Application _application;
	private VictoryPage _victoryPage;
	private Sound _backgroundSound;
	
	public VictoryScreen(Application application, int score, int round, int time) {
		_application = application;
		_victoryPage = new VictoryPage(new Vec2i(0,0), _application.getSize(), score, round, time);
		_backgroundSound = new Sound("menutheme.wav");
		_backgroundSound.loop();
	}

	@Override
	public void onDraw(Graphics2D g) {
		_victoryPage.draw(g);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDown(int keycode) {
		if (keycode == 32) { //space bar
			_backgroundSound.stop();
			_application.setScreen(new MenuScreen(_application));
		}
	}

	@Override
	public void onUp(int keycode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRepeated(int keycode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTyped(int keycode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResize(Vec2i newSize) {
		_victoryPage.setBound(new Vec2i(0,0), new Vec2i(newSize.x,newSize.y));
	}

	@Override
	public void onMouseClicked(int clickCount, Vec2i location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMousePressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDragged(Vec2i location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMoved(Vec2i location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseWheelForward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseWheelBackward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragStart(Vec2i location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragEnd(Vec2i location) {
		// TODO Auto-generated method stub
		
	}

}
