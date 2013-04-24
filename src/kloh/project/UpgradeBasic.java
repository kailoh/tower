package kloh.project;

import java.awt.Graphics2D;
import java.util.ArrayList;

import kloh.gameengine.Rectangle;
import kloh.gameengine.Text;
import cs195n.Vec2i;

public class UpgradeBasic extends Upgrades {
	
	private Rectangle _basic;
	private Text _text1;
	private Text _text2;

	public UpgradeBasic(Vec2i topLeft, Vec2i dimensions, Board board, Vec2i tile, Tower tower) {
		super(topLeft, dimensions, board, tile, tower);
		_basic = new Rectangle(Constants.BASIC_TOWER_COLOR, Constants.SELECTED_COLOR, 2, topLeft, new Vec2i(dimensions.y,dimensions.y));
		_text1 = new Text(Constants.TEXT_FONT, "bold", 18, "BASIC TOWER", Constants.BASIC_TOWER_COLOR);
		_text2 = new Text(Constants.TEXT_FONT, "bold", 15, "Upgrade Cost: $" + (Constants.BASIC_TOWER_COST-Constants.WALL_COST) + " | Rate: Slow | Range: " + Constants.BASIC_TOWER_RANGE + " | Damage: " + Constants.BASIC_TOWER_DAMAGE, Constants.BASIC_TOWER_COLOR);
		_text1.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y));
		_text2.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y+20));
	}
	
	@Override
	public void onMouseClicked(Vec2i location) {
		Vec2i rectLocation = _basic.getLocation();
		Vec2i rectSize = _basic.getSize();
		if (location.x >= rectLocation.x && location.x <= rectLocation.x + rectSize.x && location.y >= rectLocation.y && location.y <= rectLocation.y + rectSize.y) {
			this.shortcut();
		}
	}
	
	@Override
	public void setBound(Vec2i topLeft, Vec2i dimensions) {
		_basic.setLocation(topLeft);
		_basic.setSize(new Vec2i(dimensions.y,dimensions.y));
		_text1.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y));
		_text2.setLocation(new Vec2i(topLeft.x + dimensions.y + 10, topLeft.y+20));
	}
	
	@Override
	public void shortcut() {
		if (_board.getGold() - (Constants.BASIC_TOWER_COST-Constants.WALL_COST)>= 0) {
			_board.reduceGold(Constants.BASIC_TOWER_COST-Constants.WALL_COST);
			Tower tower = new BasicTower(_tile, _board);
			ArrayList<Tower> towers = _board.getTowers();
			towers.remove(_tower);
			towers.add(tower);
			_basic.setBorderColor(Constants.UNSELECTED_COLOR);
			_board.clearSelection();
		}
	}
	
	@Override
	public void draw(Graphics2D aBrush) {
		_basic.draw(aBrush);
		_text1.draw(aBrush);
		_text2.draw(aBrush);
	}

}
