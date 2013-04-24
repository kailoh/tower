package kloh.project;

import kloh.gameengine.Evaluator;
import kloh.gameengine.GameWorld;

public class BoardEvaluator implements Evaluator {
	
	private float _threshold;
	
	public BoardEvaluator(float threshold) {
		_threshold = threshold;
	}
	
	public int evaluate(GameWorld gameWorld) {
		Board board = (Board) gameWorld;
		int currentRound = board.getCurrentRound();
		int score = board.getScore();
		int gold = board.getGold();
		float evaluation = 0;
		if (currentRound >= 1) {
			float nominator = (0.8f*score + 0.2f*(gold/2))/1.5f;
			evaluation = nominator/(currentRound*0.85f);
			//System.out.println("currentRound: " + currentRound + " | score: " + score + " | gold: " + gold + " | evaluation: " + evaluation);
		}
		if (evaluation > _threshold) {
			return (int) (((evaluation-_threshold)/_threshold)*60f);
		}
		else return 0;
	}

}
