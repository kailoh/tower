package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;

import kloh.gameengine.Rectangle;
import kloh.gameengine.Text;
import kloh.gameengine.UIElement;
import cs195n.Vec2i;

public class SelectionPage implements UIElement {
		
	private Text _text1;
	private Text _instructions1;
	private Text _instructions2;
	private Text _instructions3;
	private Text _instructions4;
	private Text _choice1;
	private Text _choice2;
	private Text _choice3;
	private Text _selected;
	private Text _credits1;
	private int _textSize;
	private Rectangle _background;
	
	public SelectionPage(Vec2i topLeft, Vec2i dimensions) {
		_textSize = 24;
		_background = new Rectangle(new Color(800000), null, 0);
		_background.setSize(dimensions);
		_background.setLocation(topLeft);
		_text1 = new Text(Constants.HEADING_FONT, "bold", _textSize*4, "TOWER DEFENSE", Color.WHITE);
		_text1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + _textSize));
		_instructions1 = new Text(Constants.TEXT_FONT, "bold", 20, "Enemies spawn from the four edges and make their way to your barracks.", Color.WHITE);
		_instructions1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 8*_textSize));
		_instructions2 = new Text(Constants.TEXT_FONT, "bold", 20, "Stop them by building walls and towers.", Color.WHITE);
		_instructions2.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 9*_textSize));
		_instructions3 = new Text(Constants.TEXT_FONT, "bold", 20, "Enemies have more life as the game advances, and they may explode too.", Color.WHITE);
		_instructions3.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 10*_textSize));
		_instructions4 = new Text(Constants.TEXT_FONT, "bold", 20, "Navigate using your mouse, and quit any time by clicking on [QUIT].", Color.WHITE);
		_instructions4.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 11*_textSize));
		_choice1 = new Text(Constants.TEXT_FONT, "bold", _textSize, " ", Color.GRAY);
		_choice1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 14*_textSize));
		_choice2 = new Text(Constants.TEXT_FONT, "bold", _textSize, " ", Color.WHITE);
		_choice2.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 15*_textSize));
		_choice3 = new Text(Constants.TEXT_FONT, "bold", _textSize, " ", Color.GRAY);
		_choice3.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 16*_textSize));
		_selected = new Text(Constants.TEXT_FONT, "bold", _textSize, " ", Color.ORANGE);
		_selected.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 18*_textSize));
		_credits1 = new Text(Constants.TEXT_FONT, "bold", (int)(0.8*_textSize), "Game by Kai | Music by Bryce", Color.WHITE);
		_credits1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 22*_textSize));
	}

	@Override
	public void setBound(Vec2i topLeft, Vec2i dimensions) {
		_text1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + _textSize));
		_instructions1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 8*_textSize));
		_instructions2.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 9*_textSize));
		_instructions3.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 10*_textSize));
		_instructions4.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 11*_textSize));
		_choice1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 14*_textSize));
		_choice2.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 15*_textSize));
		_choice3.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 16*_textSize));
		_selected.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 18*_textSize));
		_credits1.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 22*_textSize));
		_background.setSize(dimensions);
		_background.setLocation(topLeft);
	}
	
	
	public void setChoice(String choice1, String choice2, String choice3, String selected) {
		_choice1.setText(choice1);
		_choice2.setText(choice2);
		_choice3.setText(choice3);
		_selected.setText("Selected: << " + selected + " >> ... Press SPACE BAR to continue");
	}
	
	@Override
	public void draw(Graphics2D aBrush) {
		_background.draw(aBrush);
		_text1.draw(aBrush);
		_instructions1.draw(aBrush);
		_instructions2.draw(aBrush);
		_instructions3.draw(aBrush);
		_instructions4.draw(aBrush);
		_choice1.draw(aBrush);
		_choice2.draw(aBrush);
		_choice3.draw(aBrush);
		_selected.draw(aBrush);
		_credits1.draw(aBrush);
	}
}
