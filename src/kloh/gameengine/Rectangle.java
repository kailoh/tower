package kloh.gameengine;

import java.awt.Color;
import java.awt.Graphics2D;

import cs195n.Vec2i;

public class Rectangle extends GenericShape {
	
	protected java.awt.geom.Rectangle2D.Float _rectangle;
	
	public Rectangle(Color fillColor, Color borderColor, int strokeWidth) {
		_rectangle = new java.awt.geom.Rectangle2D.Float();
		_fillColor = fillColor;
		_borderColor = borderColor;
		_strokeWidth = strokeWidth;
	}
	
	public Rectangle(Color fillColor, Color borderColor, int strokeWidth, Vec2i location, Vec2i size) {
		_rectangle = new java.awt.geom.Rectangle2D.Float();
		_fillColor = fillColor;
		_borderColor = borderColor;
		_strokeWidth = strokeWidth;
		_rectangle.setFrame(location.x, location.y, size.x, size.y);
	}
	
	public void setLocation(Vec2i location) {
		_rectangle.setFrame(location.x, location.y, _rectangle.getWidth(), _rectangle.getHeight());
	}
	
	public Color getFillColor() {
		return _fillColor;
	}
	
	public Vec2i getLocation() {
		return new Vec2i((int)_rectangle.getX(), (int)_rectangle.getY());
	}
	
	public void setSize(Vec2i size) {
		_rectangle.setFrame(_rectangle.getX(), _rectangle.getY(), size.x, size.y);
	}
	
	public Vec2i getSize() {
		return new Vec2i((int)_rectangle.getWidth(), (int)_rectangle.getHeight());
	}
	
	public void draw (Graphics2D aBrush) {
		java.awt.Color savedColor = aBrush.getColor();
		java.awt.Stroke savedStroke = aBrush.getStroke();
		aBrush.setColor(_fillColor);
		aBrush.fill(_rectangle);
		aBrush.setColor(savedColor);
		aBrush.setStroke(new java.awt.BasicStroke(_strokeWidth));
		if (_strokeWidth != 0) {
			aBrush.setColor(_borderColor);
			aBrush.draw(_rectangle);
			aBrush.setColor(savedColor);
			aBrush.setStroke(savedStroke);
		}
	}

}
