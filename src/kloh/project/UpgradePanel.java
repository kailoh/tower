package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;

import kloh.gameengine.Rectangle;
import kloh.gameengine.Text;
import kloh.gameengine.UIElement;
import cs195n.Vec2i;

public class UpgradePanel implements UIElement {
	
	private Rectangle _background;
	private Text _text1;
	private Text _text2;
	private Vec2i _topLeft;
	private Vec2i _uiDimensions;
	private Upgrades _upgrades;
	private Board _board;
	private Rectangle _endRectangle;
	private Text _endText;

	public UpgradePanel(Vec2i topLeft, Vec2i dimensions) {
		_background = new Rectangle(Constants.PANEL_FILL_COLOR, Constants.PANEL_BORDER_COLOR, 2, topLeft, dimensions);
		_topLeft = topLeft;
		_uiDimensions = dimensions;
		_text1 = new Text(Constants.TEXT_FONT, "bold", 17, " ", Constants.PANEL_TEXT_COLOR);
		_text1.setLocation(new Vec2i(topLeft.x + 10, topLeft.y + 5));
		_text2 = new Text(Constants.TEXT_FONT, "bold", 17, " ", Constants.PANEL_TEXT_COLOR);
		_text2.setLocation(new Vec2i(topLeft.x + 10, topLeft.y + 30));
		Vec2i rectangleSize = new Vec2i((int)(dimensions.y*0.6), (int)(dimensions.y*0.6));
		Vec2i rectangleLocation = new Vec2i((int) (topLeft.x+dimensions.x-0.8*dimensions.y), (int) (topLeft.y+0.2*dimensions.y));
		_endText = new Text(Constants.TEXT_FONT, "bold", 19, "QUIT", Color.BLACK);
		_endText.setLocation(rectangleLocation.plus(new Vec2i((int) (0.10*rectangleSize.x), (int)(0.25*rectangleSize.y))));
		_endRectangle = new Rectangle(Constants.QUIT_BUTTON_COLOR, Constants.UNSELECTED_COLOR, 2, rectangleLocation, rectangleSize);
	}
	
	public void setBoard(Board board) {
		_board = board;
		_upgrades = new Upgrades(new Vec2i(_topLeft.x+20,_topLeft.y+40), new Vec2i(_uiDimensions.x-240,_uiDimensions.y-60), board, new Vec2i(-1,-1), null);
	}
	
	public void display(int towerToUpgrade, Vec2i tile, Tower tower) {
		switch (towerToUpgrade) {
		case Constants.WALL_INT:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 17, "Click icon below or press SPACE to upgrade [WALL] to:", Constants.PANEL_TEXT_COLOR);
			_text1.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 5));
			_text2 = new Text(Constants.TEXT_FONT, "bold", 17, " ", Constants.PANEL_TEXT_COLOR);
			_text2.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 30));
			_upgrades = new UpgradeBasic(new Vec2i(_topLeft.x+20,_topLeft.y+40), new Vec2i(_uiDimensions.x-240,_uiDimensions.y-60), _board, tile, tower);
			break;
		case Constants.BASIC_TOWER_INT:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 17, "Click icon below or press SPACE to upgrade [BASIC TOWER] to:", Constants.PANEL_TEXT_COLOR);
			_text1.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 5));
			_text2 = new Text(Constants.TEXT_FONT, "bold", 17, " ", Constants.PANEL_TEXT_COLOR);
			_text2.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 30));
			_upgrades = new UpgradeAdvanced(new Vec2i(_topLeft.x+20,_topLeft.y+40), new Vec2i(_uiDimensions.x-240,_uiDimensions.y-60), _board, tile, tower);
			break;
		case Constants.ADVANCED_TOWER_INT:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 17, "Click icon below or press SPACE to upgrade [ADVANCED TOWER] to:", Constants.PANEL_TEXT_COLOR);
			_text1.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 5));
			_text2 = new Text(Constants.TEXT_FONT, "bold", 17, " ", Constants.PANEL_TEXT_COLOR);
			_text2.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 30));
			_upgrades = new UpgradeSuper(new Vec2i(_topLeft.x+20,_topLeft.y+40), new Vec2i(_uiDimensions.x-240,_uiDimensions.y-60), _board, tile, tower);
			break;
		case 8:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 17, "To build a new tower, use the menu on the right.", Constants.PANEL_TEXT_COLOR);
			_text1.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 5));
			_text2 = new Text(Constants.TEXT_FONT, "bold", 17, "To upgrade a tower, click on an existing tower.", Constants.PANEL_TEXT_COLOR);
			_text2.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 30));
			_upgrades = new Upgrades(new Vec2i(_topLeft.x+20,_topLeft.y+40), new Vec2i(_uiDimensions.x-240,_uiDimensions.y-60), _board, new Vec2i(-1,-1), null);
			break;
		default:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 17, "This tower cannot be upgraded.", Constants.PANEL_TEXT_COLOR);
			_text1.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 5));
			_text2 = new Text(Constants.TEXT_FONT, "bold", 17, "Select an already-built tower to uprade it.", Constants.PANEL_TEXT_COLOR);
			_text2.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 30));
			_upgrades = new Upgrades(new Vec2i(_topLeft.x+20,_topLeft.y+40), new Vec2i(_uiDimensions.x-240,_uiDimensions.y-60), _board, new Vec2i(-1,-1), null);
			break;
		}
	}
	
	public void onMouseClicked(Vec2i location) {
		if (location.x >= _topLeft.x+20 && location.x <= _topLeft.x+20+(_uiDimensions.x-240) && location.y >= _topLeft.y+35 && location.y <= _topLeft.y+35+(_uiDimensions.y-60)) {
			_upgrades.onMouseClicked(location);
		}
		else if (location.x >= _endRectangle.getLocation().x && location.x <= _endRectangle.getLocation().x + _endRectangle.getSize().x && location.y >= _endRectangle.getLocation().y && location.y <= _endRectangle.getLocation().y + _endRectangle.getSize().y) {
			_board.gameOver();
		}
	}
	
	public void shortcutTyped() {
		_upgrades.shortcut();
	}

	@Override
	public void setBound(Vec2i topLeft, Vec2i uiDimensions) {
		_background.setLocation(topLeft);
		_background.setSize(uiDimensions);
		_topLeft = topLeft;
		_uiDimensions = uiDimensions;
		_text1.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 5));
		_text2.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + 30));
		_upgrades.setBound(new Vec2i(_topLeft.x+20,_topLeft.y+40), new Vec2i(_uiDimensions.x-40,_uiDimensions.y-60));
		
		Vec2i rectangleSize = new Vec2i((int)(uiDimensions.y*0.6), (int)(uiDimensions.y*0.6));
		Vec2i rectangleLocation = new Vec2i((int) (topLeft.x+uiDimensions.x-0.8*uiDimensions.y), (int) (topLeft.y+0.2*uiDimensions.y));
		_endText.setLocation(rectangleLocation.plus(new Vec2i((int) (0.10*rectangleSize.x), (int)(0.25*rectangleSize.y))));
		_endRectangle.setLocation(rectangleLocation);
		_endRectangle.setSize(rectangleSize);
	}

	@Override
	public void draw(Graphics2D aBrush) {
		_background.draw(aBrush);
		_text1.draw(aBrush);
		_text2.draw(aBrush);
		_upgrades.draw(aBrush);
		_endRectangle.draw(aBrush);
		_endText.draw(aBrush);
	}
}
