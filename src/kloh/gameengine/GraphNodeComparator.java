package kloh.gameengine;

import java.util.Comparator;

public class GraphNodeComparator implements Comparator<GraphNode>{

	@Override
	public int compare(GraphNode o1, GraphNode o2) {
		if (o2.getF() > o1.getF()) {
			return -1;
		}
		else if (o2.getF() < o1.getF()) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
