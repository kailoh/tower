package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;

import kloh.gameengine.Entity;
import kloh.gameengine.Text;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class AfterDestroyText implements Entity {
	
	private Text _text;
	private Vec2f _location;
	private int _counter;
	private Board _board;
	private int _points;
	
	public AfterDestroyText(Vec2f location, int createTime, int points, Board board) {
		_counter = 0;
		_points = points;
		_board = board;
		_text = new Text(Constants.TEXT_FONT, "bold", 17, "+" + points, new Color(255,255,255,255));
		_text.setLocation(new Vec2i((int)location.x,(int)location.y));
		_location = location;
	}
	
	public void onTick(float currTime) {
		_counter++;
		if (_counter <= 59) {
			_text = new Text(Constants.TEXT_FONT, "bold", 17, "+" + _points, new Color(255,255,255,(int)(255-((255/60)*_counter))));
		}
		if (_counter > 60) {
			_board.removeEntity(this);
		}
	}
	
	@Override
	public void draw(Graphics2D aBrush) {
		_text.draw(aBrush);
	}


	@Override
	public void setLocation(Vec2f vec) {
		_location = vec;
		_text.setLocation(new Vec2i((int)vec.x,(int)vec.y));
	}

	@Override
	public void setSize(Vec2f vec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vec2f getLocation() {
		return _location;
	}

	@Override
	public Vec2f getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
