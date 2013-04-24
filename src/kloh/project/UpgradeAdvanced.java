package kloh.project;

import java.awt.Graphics2D;
import java.util.ArrayList;

import kloh.gameengine.Rectangle;
import kloh.gameengine.Text;
import cs195n.Vec2i;

public class UpgradeAdvanced extends Upgrades {
	
	private Rectangle _advanced;
	private Text _text1;
	private Text _text2;

	public UpgradeAdvanced(Vec2i topLeft, Vec2i dimensions, Board board, Vec2i tile, Tower tower) {
		super(topLeft, dimensions, board, tile, tower);
		_advanced = new Rectangle(Constants.ADVANCED_TOWER_COLOR, Constants.SELECTED_COLOR, 2, topLeft, new Vec2i(dimensions.y,dimensions.y));
		_text1 = new Text(Constants.TEXT_FONT, "bold", 18, "ADVANCED TOWER", Constants.ADVANCED_TOWER_COLOR);
		_text2 = new Text(Constants.TEXT_FONT, "bold", 15, "Upgrade Cost: $" + (Constants.ADVANCED_TOWER_COST-Constants.BASIC_TOWER_COST) + " | Rate: Medium | Range: " + Constants.ADVANCED_TOWER_RANGE  + " | Damage: " + Constants.ADVANCED_TOWER_DAMAGE, Constants.ADVANCED_TOWER_COLOR);
		_text1.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y));
		_text2.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y+20));
	}
	
	@Override
	public void onMouseClicked(Vec2i location) {
		Vec2i rectLocation = _advanced.getLocation();
		Vec2i rectSize = _advanced.getSize();
		if (location.x >= rectLocation.x && location.x <= rectLocation.x + rectSize.x && location.y >= rectLocation.y && location.y <= rectLocation.y + rectSize.y) {
			this.shortcut();
		}
	}
	
	@Override
	public void setBound(Vec2i topLeft, Vec2i dimensions) {
		super.setBound(topLeft,dimensions);
		_advanced.setLocation(topLeft);
		_advanced.setSize(new Vec2i(dimensions.y,dimensions.y));
		_text1.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y));
		_text2.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y+20));
	}
	
	@Override
	public void shortcut() {
		if (_board.getGold() - (Constants.ADVANCED_TOWER_COST-Constants.BASIC_TOWER_COST)>= 0) {
			_board.reduceGold(Constants.ADVANCED_TOWER_COST-Constants.BASIC_TOWER_COST);
			Tower tower = new AdvancedTower(_tile, _board);
			ArrayList<Tower> towers = _board.getTowers();
			towers.remove(_tower);
			towers.add(tower);
			_advanced.setBorderColor(Constants.UNSELECTED_COLOR);
			_board.clearSelection();
		}
	}
	
	@Override
	public void draw(Graphics2D aBrush) {
		_advanced.draw(aBrush);
		_text1.draw(aBrush);
		_text2.draw(aBrush);
	}

}
