package kloh.gameengine;

import java.util.ArrayList;
import cs195n.Vec2i;

public abstract class GraphNode { 
	
	private ArrayList<GraphNode> _neighbors;
	private int _g;
	private int _h;
	private Vec2i _location;
	private GraphNode _parent;
	
	public void populateNeighbors(ArrayList<GraphNode> neighbors) {
		_neighbors = neighbors;
	}
	
	public ArrayList<GraphNode> getNeighbors() {
		return _neighbors;
	}
	
	public void setParent(GraphNode parent) {
		_parent = parent;
	}
	
	public GraphNode getParent() {
		return _parent;
	}
	
	public void setLocation(Vec2i location) {
		_location = location;
	}
	
	public Vec2i getLocation() {
		return _location;
	}
	
	public void setG(int g) {
		_g = g;
	}
	
	public int getG() {
		return _g;
	}
	
	public void setH(int h) {
		_h = h;
	}
	
	public int getH() {
		return _h;
	}
	
	public int getF() {
		int f = _g + _h;
		return f;
	}
	
}
