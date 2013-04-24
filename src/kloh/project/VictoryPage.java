package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;

import kloh.gameengine.Rectangle;
import kloh.gameengine.Text;
import kloh.gameengine.UIElement;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import cs195n.Vec2i;

public class VictoryPage implements UIElement {

	private int _textSize;
	private Text _score;
	private Text _round;
	private Text _time;
	private Text _text2;
	private Rectangle _background;
	
	public VictoryPage(Vec2i topLeft, Vec2i dimensions, int score, int round, int time) {
		_textSize = 24;
		_background = new Rectangle(new Color(800000), null, 0);
		_background.setSize(dimensions);
		_background.setLocation(topLeft);
		_score = new Text(Constants.TEXT_FONT, "bold", 70, "FINAL SCORE: " + score, Constants.VICTORY_TEXT_COLOR);
		_score.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + _textSize*2));
		
		//MUST FILL THIS IN
		//The token that I put in here might have already expired by the time you look at it. If so, do the following steps:
		//Go to: https://graph.facebook.com/oauth/authorize?client_id=228493873948878& redirect_uri=http://www.facebook.com/connect/login_success.html& scope=publish_stream,create_event
		//Facebook will redirect you to http://www.facebook.com/connect/login_success.html? code=MY_VERIFICATION_CODE
		//Go to: https://graph.facebook.com/oauth/access_token?client_id=228493873948878& redirect_uri=http://www.facebook.com/connect/login_success.html& client_secret=789619d96db9a19f076774d125fdb84b&code=MY_VERIFICATION_CODE
		//Facebook will respond with access_token=MY_ACCESS_TOKEN
		//Set token below to the value of MY_ACCESS_TOKEN
		
		String token = "AAADP0GAwqM4BAB2gKqVz9lhF7NwhZCkQuZCHlORnsyEZBccIeLsfCSaa09fn5uhntG97MsMPzgpAiX8Rq4fMGVbh4x3ZBiWLsZCWk47Y8EAZDZD";
		
		FacebookClient facebookClient = new DefaultFacebookClient(token);
		
		try {
			facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", "I have achieved a score of " + score + " on Kai's Tower Defense!"));
		} catch(Exception e) {
			//do nothing
		}
		
		_round = new Text(Constants.TEXT_FONT, "bold", 35, "You made it to round " + round, Constants.VICTORY_TEXT_COLOR);
		_round.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + _textSize*12));
		_time = new Text(Constants.TEXT_FONT, "bold", 35, "You survived for: " + time + " seconds", Constants.VICTORY_TEXT_COLOR);
		_time.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + _textSize*14));
		_text2 = new Text(Constants.TEXT_FONT, "bold", 35, "Press SPACE BAR to return to menu", Constants.VICTORY_TEXT_COLOR); 
		_text2.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 16*_textSize));
	}
	
	@Override
	public void setBound(Vec2i topLeft, Vec2i uiDimensions) {
		_score.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 2*_textSize));
		_round.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 12*_textSize));
		_time.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 14*_textSize));
		_text2.setLocation(new Vec2i(topLeft.x + _textSize, topLeft.y + 16*_textSize));
		_background.setSize(uiDimensions);
		_background.setLocation(topLeft);
	}

	@Override
	public void draw(Graphics2D aBrush) {
		_background.draw(aBrush);
		_score.draw(aBrush);
		_round.draw(aBrush);
		_time.draw(aBrush);
		_text2.draw(aBrush);
	}

}
