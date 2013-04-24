package kloh.project;

import cs195n.Vec2i;

public class GoldTower extends Tower{

	public GoldTower(Vec2i location, Board board) {
		super(location, Constants.GOLD_TOWER_SIZE, board, Constants.GOLD_TOWER_COLOR);
	}
	
	@Override
	public void onTick() {
		super.mine(Constants.GOLD_TOWER_RATE,Constants.GOLD_TOWER_EARNINGS);
	}

}
