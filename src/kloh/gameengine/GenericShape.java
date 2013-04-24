package kloh.gameengine;

public abstract class GenericShape {
	
	protected java.awt.Color _borderColor;
	protected java.awt.Color _fillColor;
	protected int _strokeWidth;
	
	public void setFillColor(java.awt.Color color) {
		_fillColor = color;
	}
	
	public void setBorderColor(java.awt.Color color) {
		_borderColor = color;
	}
	
	public void setStrokeWidth(int strokeWidth) {
		_strokeWidth = strokeWidth;
	}
	
}
