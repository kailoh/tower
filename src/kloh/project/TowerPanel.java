package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;

import kloh.gameengine.Rectangle;
import kloh.gameengine.Text;
import kloh.gameengine.UIElement;
import cs195n.Vec2i;

public class TowerPanel implements UIElement {
	
	private Vec2i _topLeft;
	private Vec2i _uiDimensions;
	private Rectangle _background;
	private Rectangle[] _towers;
	private int _selected; //negative for non selected, 0 to NO_OF_TOWERS-1 for the tower selected
	private Text _gold;
	private Text _score;
	private Text _time;
	private Text _round;
	private int _info; //whether the mouse is over a tower icon - ie should info be displayed. if negative, no. else, the info displayed is based on the tower type #.
	private InfoBox _infoBox;
	private Board _board;
	private Text[] _shortcuts;
	private Text _goldTowers;
	
	public TowerPanel(Vec2i topLeft, Vec2i uiDimensions) {
		_infoBox = new InfoBox(new Vec2i(0,0), new Vec2i(0,0), -1);
		_info = -1;
		_background = new Rectangle(Constants.PANEL_FILL_COLOR, Constants.PANEL_BORDER_COLOR, 2, topLeft, uiDimensions);
		_towers = new Rectangle[Constants.NO_OF_TOWERS];
		_shortcuts = new Text[Constants.NO_OF_TOWERS];
		_shortcuts[Constants.WALL_INT] = new Text(Constants.TEXT_FONT, "bold", 16, "[w]", Color.BLACK);
		_shortcuts[Constants.BASIC_TOWER_INT] = new Text(Constants.TEXT_FONT, "bold", 16, "[b]", Color.BLACK);
		_shortcuts[Constants.GOLD_TOWER_INT] = new Text(Constants.TEXT_FONT, "bold", 16, "[g]", Color.BLACK);
		_shortcuts[Constants.ADVANCED_TOWER_INT] = new Text(Constants.TEXT_FONT, "bold", 16, "[a]", Color.BLACK);
		for (int i=0; i<Constants.NO_OF_TOWERS; i++) {
			_towers[i] = new Rectangle(Color.GREEN, Constants.UNSELECTED_COLOR, 4, topLeft.plus(Constants.TOWER_ICON_PADDING, Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*i)), new Vec2i(Constants.TOWER_ICON_SIZE,Constants.TOWER_ICON_SIZE));
			_shortcuts[i].setLocation(topLeft.plus(Constants.TOWER_TEXT_OFFSET).plus(Constants.TOWER_ICON_PADDING, Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*i)));
		}
		_towers[Constants.WALL_INT].setFillColor(Constants.WALL_COLOR);
		_towers[Constants.BASIC_TOWER_INT].setFillColor(Constants.BASIC_TOWER_COLOR);
		_towers[Constants.GOLD_TOWER_INT].setFillColor(Constants.GOLD_TOWER_COLOR);
		_towers[Constants.ADVANCED_TOWER_INT].setFillColor(Constants.ADVANCED_TOWER_COLOR);
		_topLeft = topLeft;
		_uiDimensions = uiDimensions;
		_selected = -1;
		_gold = new Text(Constants.TEXT_FONT, "bold", 50, "$0", Constants.PANEL_TEXT_COLOR);
		_score = new Text(Constants.TEXT_FONT, "bold", 25, "0 pts", Constants.PANEL_TEXT_COLOR);
		_time = new Text(Constants.TEXT_FONT, "bold", 18, "0s elapsed", Constants.PANEL_TEXT_COLOR);
		_round = new Text(Constants.TEXT_FONT, "bold", 20, "Wave 0", Constants.PANEL_TEXT_COLOR);

		_time.setLocation(new Vec2i(topLeft.x + 5, topLeft.y + _uiDimensions.y - 40));
		_round.setLocation(new Vec2i(topLeft.x + 5, topLeft.y + _uiDimensions.y - 70));
		_score.setLocation(new Vec2i(topLeft.x + 10, topLeft.y + _uiDimensions.y - 110));
		_gold.setLocation(new Vec2i(topLeft.x + 15, topLeft.y + _uiDimensions.y - 180));
	}
	
	public void setBoard(Board board) {
		_board = board;
		_goldTowers =  new Text(Constants.TEXT_FONT, "bold", 60, "" + (Constants.GOLD_TOWER_MAX - _board.getGoldTowers()), new Color(0,0,0,0.5f));
		_goldTowers.setLocation(_topLeft.plus(new Vec2i(10,10)).plus(Constants.TOWER_ICON_PADDING, Constants.TOWER_ICON_PADDING*(Constants.GOLD_TOWER_INT+1)+(Constants.TOWER_ICON_SIZE*Constants.GOLD_TOWER_INT)));
	}
	
	public void setGoldTowers(int remaining) {
		_goldTowers =  new Text(Constants.TEXT_FONT, "bold", 60, "" + (Constants.GOLD_TOWER_MAX - _board.getGoldTowers()), new Color(0,0,0,0.5f));
		_goldTowers.setLocation(_topLeft.plus(new Vec2i(10,10)).plus(Constants.TOWER_ICON_PADDING, Constants.TOWER_ICON_PADDING*(Constants.GOLD_TOWER_INT+1)+(Constants.TOWER_ICON_SIZE*Constants.GOLD_TOWER_INT)));
	}
	
	public void setGold(int newGold) {
		_gold = new Text(Constants.TEXT_FONT, "bold", 50, "$" + newGold, Constants.PANEL_TEXT_COLOR);
		_gold.setLocation(new Vec2i(_topLeft.x + 15, _topLeft.y + _uiDimensions.y - 180));
	}
	
	public void setScore(int newScore) {
		_score = new Text(Constants.TEXT_FONT, "bold", 25, newScore + " pts", Constants.PANEL_TEXT_COLOR);
		_score.setLocation(new Vec2i(_topLeft.x + 10, _topLeft.y + _uiDimensions.y - 110));
	}
	
	public void setTime(int s) {
		_time = new Text(Constants.TEXT_FONT, "bold", 18, s + "s elapsed", Constants.PANEL_TEXT_COLOR);
		_time.setLocation(new Vec2i(_topLeft.x + 5, _topLeft.y + _uiDimensions.y - 40));
	}
	
	public int getSelectedTower() {
		return _selected;
	}
	
	public void setRound(int r) {
		_round = new Text(Constants.TEXT_FONT, "bold", 20, "Wave " + r, Constants.PANEL_TEXT_COLOR);
		_round.setLocation(new Vec2i(_topLeft.x + 5, _topLeft.y + _uiDimensions.y - 70));
	}
	
	public void setSelectedTower(int selected) {
		if (_selected >=0 ) {
			_towers[_selected].setBorderColor(Constants.UNSELECTED_COLOR);
		}
		_selected = selected;
		if (_selected >= 0) {
			_towers[_selected].setBorderColor(Constants.SELECTED_COLOR);
		}
		if (_board.getClickedOn()) {
			_board.clearSelection();
		}
	}
	
	public void onMouseMoved(Vec2i location) {
		boolean within = false;
		int i=0;
		while (!within && i<Constants.NO_OF_TOWERS) {
			if (location.x > _topLeft.x+Constants.TOWER_ICON_PADDING && location.x < _topLeft.x +Constants.TOWER_ICON_PADDING+Constants.TOWER_ICON_SIZE
					&& location.y > _topLeft.y + Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*i) && location.y < Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*(i+1))) {
				within = true;
				_info = i;
				_infoBox = new InfoBox(new Vec2i(_topLeft.x-Constants.TOWER_PANEL_WIDTH-Constants.TOWER_ICON_PADDING,_topLeft.y+Constants.TOWER_ICON_PADDING*(_info+1)+(Constants.TOWER_ICON_SIZE*_info)), new Vec2i(200,110), i);
			}
			else {
				i++;
			}
		}
		if (!within) {
			_info = -1;
			_infoBox = new InfoBox(new Vec2i(0,0), new Vec2i(0,0), -1);
		}
	}
	
	public void onMouseClicked(Vec2i clickLocation) {
		boolean within = false;
		int i=0;
		while (!within && i<Constants.NO_OF_TOWERS) {
			if (clickLocation.x > _topLeft.x+Constants.TOWER_ICON_PADDING && clickLocation.x < _topLeft.x +Constants.TOWER_ICON_PADDING+Constants.TOWER_ICON_SIZE
					&& clickLocation.y > _topLeft.y + Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*i) && clickLocation.y < Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*(i+1))) {
				within = true;
				this.setSelectedTower(i);
			}
			else {
				i++;
			}
		}
		if (!within) {
			this.setSelectedTower(-1);
		}
	}

	@Override
	public void setBound(Vec2i topLeft, Vec2i uiDimensions) {
		_topLeft = topLeft;
		_uiDimensions = uiDimensions;
		_background.setLocation(topLeft);
		_background.setSize(uiDimensions);
		for (int i=0; i<Constants.NO_OF_TOWERS; i++) {
			_towers[i].setLocation(topLeft.plus(Constants.TOWER_ICON_PADDING, Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*i)));
			_shortcuts[i].setLocation(topLeft.plus(Constants.TOWER_TEXT_OFFSET).plus(Constants.TOWER_ICON_PADDING, Constants.TOWER_ICON_PADDING*(i+1)+(Constants.TOWER_ICON_SIZE*i)));
		}
		_time.setLocation(new Vec2i(topLeft.x + 5, topLeft.y + _uiDimensions.y - 40));
		_round.setLocation(new Vec2i(topLeft.x + 5, topLeft.y + _uiDimensions.y - 70));
		_score.setLocation(new Vec2i(topLeft.x + 10, topLeft.y + _uiDimensions.y - 110));
		_gold.setLocation(new Vec2i(_topLeft.x + 15, _topLeft.y + _uiDimensions.y - 180));
		_goldTowers.setLocation(_topLeft.plus(new Vec2i(10,10)).plus(Constants.TOWER_ICON_PADDING, Constants.TOWER_ICON_PADDING*(Constants.GOLD_TOWER_INT+1)+(Constants.TOWER_ICON_SIZE*Constants.GOLD_TOWER_INT)));
		//_infoBox.setBound(new Vec2i(_topLeft.x-Constants.TOWER_PANEL_WIDTH-Constants.TOWER_ICON_PADDING,_topLeft.y+Constants.TOWER_ICON_PADDING*(_info+1)+(Constants.TOWER_ICON_SIZE*_info)), Constants.INFO_BOX_DIMENSIONS);
	}

	@Override
	public void draw(Graphics2D aBrush) {
		_background.draw(aBrush);
		for (int i=0; i<Constants.NO_OF_TOWERS; i++) {
			_towers[i].draw(aBrush);
			_shortcuts[i].draw(aBrush);
		}
		_time.draw(aBrush);
		_round.draw(aBrush);
		_score.draw(aBrush);
		_gold.draw(aBrush);
		_infoBox.draw(aBrush);
		_goldTowers.draw(aBrush);
	}


}
