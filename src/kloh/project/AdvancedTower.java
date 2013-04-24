package kloh.project;

import cs195n.Vec2i;

public class AdvancedTower extends Tower {

	public AdvancedTower(Vec2i location, Board board) {
		super(location, Constants.ADVANCED_TOWER_SIZE, board, Constants.ADVANCED_TOWER_COLOR);
	}

	@Override
	public void onTick() {
		super.fire(Constants.ADVANCED_TOWER_RATE,Constants.ADVANCED_TOWER_RANGE, Constants.ADVANCED_TOWER_DAMAGE);
	} 

}
