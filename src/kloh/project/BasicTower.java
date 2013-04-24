package kloh.project;

import cs195n.Vec2i;

public class BasicTower extends Tower {

	public BasicTower(Vec2i location, Board board) {
		super(location, Constants.BASIC_TOWER_SIZE, board, Constants.BASIC_TOWER_COLOR);
	}
	
	@Override
	public void onTick() {
		super.fire(Constants.BASIC_TOWER_RATE, Constants.BASIC_TOWER_RANGE, Constants.BASIC_TOWER_DAMAGE);
	}

}
