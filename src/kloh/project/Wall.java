package kloh.project;

import cs195n.Vec2i;

public class Wall extends Tower {

	public Wall(Vec2i location, Board board) {
		super(location, Constants.WALL_SIZE, board, Constants.WALL_COLOR);
		
	}
	
	@Override
	public void onTick() {
		//do nothing
	}

}
