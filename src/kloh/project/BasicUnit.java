package kloh.project;

import java.util.HashMap;

import kloh.gameengine.Animation;
import kloh.gameengine.Dir;
import cs195n.Vec2i;

public class BasicUnit extends Unit {

	public BasicUnit(Vec2i current, Board board,
			HashMap<Dir, Animation> animationMap, int health) {
		super(current, board, animationMap, health);
		// TODO Auto-generated constructor stub
	}
	
	public void specialOnTick(float s) {
		//do nothing
	}

}
