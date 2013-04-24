package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;

import kloh.gameengine.Rectangle;
import kloh.gameengine.Text;
import kloh.gameengine.UIElement;
import cs195n.Vec2i;

public class InfoBox implements UIElement {

	private Rectangle _background;
	private Text _text1;
	private Text _text2;
	private Text _text3;
	private Text _text4;
	private Text _text5;
	
	public InfoBox(Vec2i topLeft, Vec2i uiDimensions, int tower) {
		_background = new Rectangle(Color.WHITE, Color.BLACK, 2, topLeft, uiDimensions);
		switch (tower) {
		case -1:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 18, " ", Color.BLACK);
			_text2 = new Text(Constants.TEXT_FONT, "bold", 18, " ", Color.BLACK);
			_text3 = new Text(Constants.TEXT_FONT, "bold", 18, " ", Color.BLACK);
			_text4 = new Text(Constants.TEXT_FONT, "bold", 18, " ", Color.BLACK);
			_text5 = new Text(Constants.TEXT_FONT, "bold", 18, " ", Color.BLACK);
			break;
		case Constants.WALL_INT:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 18, "WALL", Constants.WALL_COLOR);
			_text2 = new Text(Constants.TEXT_FONT, "bold", 18, "Cost: $" + Constants.WALL_COST, Constants.WALL_COLOR);
			_text3 = new Text(Constants.TEXT_FONT, "bold", 18, "Just a wall", Constants.WALL_COLOR);
			_text4 = new Text(Constants.TEXT_FONT, "bold", 18, "Blocks enemies", Constants.WALL_COLOR);
			_text5 = new Text(Constants.TEXT_FONT, "bold", 18, " ", Constants.WALL_COLOR);
			break;
		case Constants.BASIC_TOWER_INT:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 18, "BASIC TOWER", Constants.BASIC_TOWER_COLOR);
			_text2 = new Text(Constants.TEXT_FONT, "bold", 18, "Cost: $" + Constants.BASIC_TOWER_COST, Constants.BASIC_TOWER_COLOR);
			_text3 = new Text(Constants.TEXT_FONT, "bold", 18, "Rate: Slow", Constants.BASIC_TOWER_COLOR);
			_text4 = new Text(Constants.TEXT_FONT, "bold", 18, "Range: " + Constants.BASIC_TOWER_RANGE, Constants.BASIC_TOWER_COLOR);
			_text5 = new Text(Constants.TEXT_FONT, "bold", 18, "Damage: " + Constants.BASIC_TOWER_DAMAGE, Constants.BASIC_TOWER_COLOR);
			break;
		case Constants.GOLD_TOWER_INT:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 18, "GOLD TOWER", Constants.GOLD_TOWER_COLOR);
			_text2 = new Text(Constants.TEXT_FONT, "bold", 18, "Cost: $" + Constants.GOLD_TOWER_COST, Constants.GOLD_TOWER_COLOR);
			_text3 = new Text(Constants.TEXT_FONT, "bold", 18, "Doesn't fire", Constants.GOLD_TOWER_COLOR);
			_text4 = new Text(Constants.TEXT_FONT, "bold", 18, "Earns $", Constants.GOLD_TOWER_COLOR);
			_text5 = new Text(Constants.TEXT_FONT, "bold", 18, "At most " + Constants.GOLD_TOWER_MAX + " only", Constants.GOLD_TOWER_COLOR);
			break;
		case Constants.ADVANCED_TOWER_INT:
			_text1 = new Text(Constants.TEXT_FONT, "bold", 18, "ADVANCED TOWER", Constants.ADVANCED_TOWER_COLOR);
			_text2 = new Text(Constants.TEXT_FONT, "bold", 18, "Cost: $" + Constants.ADVANCED_TOWER_COST, Constants.ADVANCED_TOWER_COLOR);
			_text3 = new Text(Constants.TEXT_FONT, "bold", 18, "Rate: Medium", Constants.ADVANCED_TOWER_COLOR);
			_text4 = new Text(Constants.TEXT_FONT, "bold", 18, "Range: " + Constants.ADVANCED_TOWER_RANGE, Constants.ADVANCED_TOWER_COLOR);
			_text5 = new Text(Constants.TEXT_FONT, "bold", 18, "Damage: " + Constants.ADVANCED_TOWER_DAMAGE, Constants.ADVANCED_TOWER_COLOR);
			break;
		}
		_text1.setLocation(new Vec2i(topLeft.x + 5, topLeft.y));
		_text2.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+20));
		_text3.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+40));
		_text4.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+60));
		_text5.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+80));
	}
	
	public void setBound(Vec2i topLeft, Vec2i uiDimensions) {
		_text1.setLocation(new Vec2i(topLeft.x + 5, topLeft.y));
		_text2.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+20));
		_text3.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+40));
		_text4.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+60));
		_text5.setLocation(new Vec2i(topLeft.x + 5, topLeft.y+80));
		_background.setLocation(topLeft);
		_background.setSize(uiDimensions);
	}
	
	public void draw(Graphics2D aBrush) {
		_background.draw(aBrush);
		_text1.draw(aBrush);
		_text2.draw(aBrush);
		_text3.draw(aBrush);
		_text4.draw(aBrush);
		_text5.draw(aBrush);
	}
	
}
