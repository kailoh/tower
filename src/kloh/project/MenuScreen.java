package kloh.project;

import java.awt.Graphics2D;
import java.util.ArrayList;

import kloh.gameengine.Application;
import kloh.gameengine.Screen;
import kloh.gameengine.Sound;
import cs195n.Vec2i;

public class MenuScreen implements Screen {
	
	private Application _application;
	private SelectionPage _selectionPage;
	private int _currentSelection;
	private ArrayList<String> _difficultyArray;
	private ArrayList<String> _difficultyShortArray;
	private Sound _backgroundSound;

	public MenuScreen(Application application) {
		_currentSelection = 1;
		_application = application;
		_difficultyArray = new ArrayList<String>();
		_difficultyArray.add("As Easy As Pie");
		_difficultyArray.add("Medium, But Not Medium Rare");
		_difficultyArray.add("Hard Enough To Chew On");
		_difficultyShortArray = new ArrayList<String>();
		_difficultyShortArray.add("Easy");
		_difficultyShortArray.add("Medium");
		_difficultyShortArray.add("Hard");
		_selectionPage = new SelectionPage(new Vec2i(0, 0), 
				new Vec2i(_application.getSize().x, _application.getSize().y));
		_selectionPage.setChoice("As Easy As Pie", "Medium, But Not Medium Rare", "Hard Enough To Chew On", "Medium");
		_backgroundSound = new Sound("menutheme.wav");
		_backgroundSound.loop();
	}
	
	
	@Override
	public void onDraw(Graphics2D g) {
		_selectionPage.draw(g);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {		
	}

	@Override
	public void onDown(int keycode) {
		if (keycode == 40) { //down key
			int length = _difficultyArray.size();
			if (length > 2) {
				_currentSelection += 1;
				_selectionPage.setChoice(_difficultyArray.get((_currentSelection - 1)%length), _difficultyArray.get((_currentSelection%length)), _difficultyArray.get((_currentSelection + 1)%length), _difficultyShortArray.get((_currentSelection%length)));
			}
		}
		if (keycode == 38) { //up key
			int length = _difficultyArray.size();
			if (length > 2) {
				_currentSelection = _currentSelection + _difficultyArray.size() - 1;
				_selectionPage.setChoice(_difficultyArray.get((_currentSelection - 1)%length), _difficultyArray.get((_currentSelection%length)), _difficultyArray.get((_currentSelection + 1)%length), _difficultyShortArray.get((_currentSelection%length)));
			}
		}
		if (keycode == 32) { //spacebar key
			int length = _difficultyArray.size();
			if (_difficultyArray.size() > 0) {
				_backgroundSound.stop();
				_application.setScreen(new GameScreen(_application, _currentSelection % length));
			}
		}
	}

	@Override
	public void onUp(int keycode) {
	}

	@Override
	public void onRepeated(int keycode) {
	}

	@Override
	public void onTyped(int keycode) {
	}

	@Override
	public void onResize(Vec2i newSize) {
		_selectionPage.setBound(new Vec2i(0,0), new Vec2i(newSize.x,newSize.y));
	}

	@Override
	public void onMouseClicked(int clickCount, Vec2i location) {
	}

	@Override
	public void onMousePressed() {
	}

	@Override
	public void onMouseReleased() {
	}

	@Override
	public void onMouseDragged(Vec2i location) {
	}

	@Override
	public void onMouseMoved(Vec2i location) {
	}

	@Override
	public void onMouseWheelForward() {
	}

	@Override
	public void onMouseWheelBackward() {
	}
	
	@Override
	public void onDragStart(Vec2i location) {
	}

	@Override
	public void onDragEnd(Vec2i location) {
	}
	
}
