package kloh.gameengine;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class PathFinder {
	
	private GraphNode _startNode;
	private GraphNode _endNode;
	private ArrayList<GraphNode> _graph;
	
	public PathFinder(GraphNode startNode, GraphNode endNode, ArrayList<GraphNode> graph) {
		_startNode = startNode;
		_endNode = endNode;
		_graph = graph;
		this.populateCost();
	}
	
	/*This function populates each node with its G and H values*/
	public void populateCost() { 
		for (int i=0; i<_graph.size(); i++) {
			_graph.get(i).setH(Math.abs(_endNode.getLocation().x - _graph.get(i).getLocation().x) + Math.abs(_endNode.getLocation().y - _graph.get(i).getLocation().y));
			_graph.get(i).setG(1);
		}
		_startNode.setG(0);
	}
	
	public Stack<GraphNode> astar() {
		ArrayList<GraphNode> closedSet = new ArrayList<GraphNode>();
		PriorityQueue<GraphNode> openSet = new PriorityQueue<GraphNode>(10, new GraphNodeComparator());
		openSet.add(_startNode);
		if (_startNode.getLocation() != _endNode.getLocation()) {
			while (!openSet.isEmpty() ) {
				if (closedSet.contains(_endNode)) {
					break;
				}
				GraphNode current = openSet.remove();
				//System.out.println("current is " + current.getLocation());
				closedSet.add(current);
				ArrayList<GraphNode> neighbors = current.getNeighbors();
				for (int i=0; i<neighbors.size(); i++) { 
					//System.out.println("looking at " + neighbors.get(i).getLocation() + " which is the neighbor of " + current.getLocation());
					int tentativegscore = current.getG() + 1;
					//System.out.println("current's G score is " + current.getG());
					//System.out.println("tentative g score is " + tentativegscore);
					if (!closedSet.contains(neighbors.get(i))) {
						if (!openSet.contains(neighbors.get(i)) || tentativegscore < neighbors.get(i).getG()) { 
							neighbors.get(i).setParent(current);
							neighbors.get(i).setG(tentativegscore);
							if (!openSet.contains(neighbors.get(i))) {
								openSet.add(neighbors.get(i));
							}
							//System.out.println(neighbors.get(i).getLocation() + " 's parent is " + current.getLocation());
							//System.out.println(neighbors.get(i).getLocation() + " 's G score is " + neighbors.get(i).getG());
							//System.out.println(neighbors.get(i).getLocation() + " 's H score is " + neighbors.get(i).getH());
							//System.out.println(neighbors.get(i).getLocation() + " 's F score is " + neighbors.get(i).getF());
						}
					}
				}
			}
		}

		Stack<GraphNode> path = new Stack<GraphNode>();
		//for (int k=0;k<closedSet.size();k++) {
			//if (closedSet.get(k) != _startNode) {
				//System.out.println("IN closed set: " + closedSet.get(k).getLocation() + "  | parent is " + closedSet.get(k).getParent().getLocation());
			//}
		//}
		if (closedSet.contains(_endNode)) {
			
			GraphNode currentNode = _endNode;
			while (currentNode != _startNode) {
				path.push(currentNode);
				currentNode = currentNode.getParent();
			}
		}
		return path;
	}
}
