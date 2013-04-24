package kloh.gameengine;

import java.awt.Color;
import java.awt.Graphics2D;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class Viewport implements UIElement { 
	
	private Vec2i _UITopLeft; //the screen coordinates of the topleft pixel in the UIelement
	private Vec2i _UIDimensions;
	private Vec2f _viewportTopLeft; //the game coordinates of the topleft pixel in the UIelement
	private Vec2f _viewportDimensions; //how big the UIelement is in terms of game units x game units
	private GameWorld _viewable;
	private Vec2i _scale; //how many pixels 1 game coordinate unit corresponds to
	private ScrollArrows _scrollArrows;
	private Vec2i _gridDimensions;
	private Rectangle _background;
	
	public Viewport(Vec2i UITopLeft, Vec2i UIDimensions, Vec2f viewportTopLeft, GameWorld viewable, Vec2i scale, Vec2i gridDimensions) {
		_gridDimensions = gridDimensions;
		_UITopLeft = UITopLeft;
		_UIDimensions = UIDimensions;
		_viewportTopLeft = viewportTopLeft;
		_viewportDimensions = new Vec2f((float)UIDimensions.x/(float)scale.x, (float)UIDimensions.y/(float)scale.y);
		_viewable = viewable;
		_scale = scale;
		_scrollArrows = new ScrollArrows(_UITopLeft, _UIDimensions);
		_background = new Rectangle(Color.BLACK, Color.WHITE, 0);
		_background.setLocation(_UITopLeft);
		_background.setSize(_UIDimensions);
	}
	
	public Vec2f screenToGame(Vec2i screenCoordinates) {
		int screenX = screenCoordinates.x;
		int screenY = screenCoordinates.y;
		float gameX = (screenX - _UITopLeft.x)/_scale.x + _viewportTopLeft.x;
		float gameY = (screenY - _UITopLeft.y)/_scale.y + _viewportTopLeft.y;
		return new Vec2f(gameX, gameY);
	}
	
	public Vec2i gameToScreen(Vec2f gameCoordinates) {
		float gameX = gameCoordinates.x;
		float gameY = gameCoordinates.y;
		int screenX = (int) ((gameX - _viewportTopLeft.x)*_scale.x + _UITopLeft.x);
		int screenY = (int) ((gameY - _viewportTopLeft.y)*_scale.y + _UITopLeft.y);
		return new Vec2i(screenX, screenY);
	}
	
	public Vec2i gameToScreenSize(Vec2f gameSize) {
		int screenX = (int) (gameSize.x * _scale.x);
		int screenY = (int) (gameSize.y * _scale.y);
		return new Vec2i(screenX, screenY);
	}
	
	public void zoomIn(float magnitude) {
		if (_scale.x <= 12 && magnitude < 1) {
			//System.out.println("do nothing, too zoomed in");
		}
		else if (_scale.x >= 96 && magnitude > 1) {
			//System.out.println("do nothing, too zoomed out");
		}
		else if (magnitude != 1) {
			float viewportCenterX = _viewportTopLeft.x + (_viewportDimensions.x/2);
			float viewportCenterY = _viewportTopLeft.y + (_viewportDimensions.y/2);
			_viewportDimensions = new Vec2f (_viewportDimensions.x/magnitude, _viewportDimensions.y/magnitude);
			_viewportTopLeft = new Vec2f(viewportCenterX - (_viewportDimensions.x/2), viewportCenterY - (_viewportDimensions.y/2));
			_scale = new Vec2i((int)(_scale.x*magnitude), (int)(_scale.y*magnitude));
		}
	}
	
	public void move(Vec2f moveVector) {
		Vec2f oldViewportTopLeft = _viewportTopLeft;
		_viewportTopLeft = new Vec2f(_viewportTopLeft.x + moveVector.x, _viewportTopLeft.y + moveVector.y);
		if ((_viewportTopLeft.y < -1*_gridDimensions.y - 5 && moveVector.x < 0) || 
				(_viewportTopLeft.y > _gridDimensions.y + 5 && moveVector.y > 0) ||
				(_viewportTopLeft.x < -1*_gridDimensions.x - 5 && moveVector.y < 0) ||
				(_viewportTopLeft.x > _gridDimensions.x + 5 && moveVector.x > 0)) {
			_viewportTopLeft = oldViewportTopLeft;
		}
			
	}

	public void onTick(float s) { //this argument is seconds elapsed since start, not since last tick!
		this.scrollSide();
		_viewable.onTick(s);
		_scrollArrows.onTick();
	}
	
	public void center(Vec2i distanceMoved) {
		float distx = (((distanceMoved.x)/(float)_scale.x));
		float disty = (((distanceMoved.y)/(float)_scale.y));
		this.move(new Vec2f(distx,disty));
	}
	
	@Override
	public void setBound(Vec2i UITopLeft, Vec2i UIDimensions) { //the scale is unchanged
		_viewportDimensions = new Vec2f((float)UIDimensions.x/(float)_scale.x, (float)UIDimensions.y/(float)_scale.y);
		_UITopLeft = UITopLeft;
		_UIDimensions = UIDimensions;
		_scrollArrows.setLocation(UITopLeft, UIDimensions);
		_background.setSize(_UIDimensions);
		_background.setLocation(_UITopLeft);
	}
	
	public void onMouseClicked(Vec2i clickLocation) {
		float gamex = (((clickLocation.x - _UITopLeft.x)/(float)_scale.x) + _viewportTopLeft.x);
		float gamey = (((clickLocation.y - _UITopLeft.y)/(float)_scale.y) + _viewportTopLeft.y);
		_viewable.onMouseClicked(new Vec2f(gamex, gamey));
	}
	
	public void onMouseMoved(Vec2i location) {
		_scrollArrows.onMouseMoved(location);
		float gamex = (((location.x - _UITopLeft.x)/(float)_scale.x) + _viewportTopLeft.x);
		float gamey = (((location.y - _UITopLeft.y)/(float)_scale.y) + _viewportTopLeft.y);
		_viewable.onMouseMoved(new Vec2f(gamex, gamey));
	}
		
	public void scrollSide() {
		float dist = 1;
		int inside = _scrollArrows.getInside();
		if (inside > 0) {
			if (inside == 4) {
				this.move(new Vec2f(dist,0).sdiv(_scale.x/4));
			}
			else if (inside == 3) {
				this.move(new Vec2f(dist*-1,0).sdiv(_scale.x/4));
			}
			else if (inside == 2) {
				this.move(new Vec2f(0,dist).sdiv(_scale.x/4));
			}
			else if (inside == 1) {
				this.move(new Vec2f(0,dist*-1).sdiv(_scale.x/4));
			}
		}
	}

	@Override
	public void draw(Graphics2D aBrush) {
		_background.draw(aBrush);
		aBrush.clipRect(_UITopLeft.x, _UITopLeft.y, _UIDimensions.x, _UIDimensions.y); //sets the clip
		_viewable.draw(aBrush, this);
		aBrush.setClip(null); //restore the clip
		_scrollArrows.draw(aBrush);
	}
	
}
