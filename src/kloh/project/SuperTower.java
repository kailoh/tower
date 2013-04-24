package kloh.project;

import cs195n.Vec2i;

public class SuperTower extends Tower {

	public SuperTower(Vec2i location, Board board) {
		super(location, Constants.SUPER_TOWER_SIZE, board, Constants.SUPER_TOWER_COLOR);
	}

	@Override
	public void onTick() {
		super.fire(Constants.SUPER_TOWER_RATE,Constants.SUPER_TOWER_RANGE, Constants.SUPER_TOWER_DAMAGE);
	} 

}
