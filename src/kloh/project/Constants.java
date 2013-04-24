package kloh.project;

import java.awt.Color;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class Constants {

	public static final String TEXT_FONT = "STOMP_Zeroes";
	public static final String HEADING_FONT = "Oliver's Barney";
	public static final Color PANEL_FILL_COLOR = new Color(36,74,37);
	public static final Color PANEL_BORDER_COLOR = new Color(112,145,113);
	public static final Color PANEL_TEXT_COLOR = new Color(152,237,135);
	public static final Color QUIT_BUTTON_COLOR = new Color(124,245,103);
	public static final Color VICTORY_TEXT_COLOR = Color.WHITE;
	
	public static final int UPGRADE_PANEL_HEIGHT = 100;
	public static final int ROUND_INTERVAL = 5;
	public static final int STARTING_GOLD = 14;
	public static final float EVALUATION_THRESHOLD_EASY = 0.8f;
	public static final float EVALUATION_THRESHOLD_MEDIUM = 0.65f;
	public static final float EVALUATION_THRESHOLD_HARD = 0.5f;
	
	public static final Vec2f UNIT_OFFSET = new Vec2f(0.25f,0.25f);
	public static final Vec2f UNIT_SIZE = new Vec2f(0.5f, 0.5f);
	public static final float UNIT_MOVE_STEP = 0.01f;
	
	public static final int BASIC_UNIT_INT = 0;
	public static final int BASIC_UNIT_SCORE = 1;
	
	public static final int EXPLODING_UNIT_INT = 1;
	public static final int EXPLODING_UNIT_TIME = 8;
	public static final int EXPLODING_UNIT_VARIABLE_TIME = 30;
	public static final int EXPLODING_UNIT_SCORE = 3;
	
	public static final Vec2f PROJECTILE_SIZE = new Vec2f(0.18f,0.18f);
	public static final Color PROJECTILE_COLOR = Color.WHITE;
	
	public static final Color SELECTED_COLOR = Color.GREEN;
	public static final Color UNSELECTED_COLOR = Color.BLACK;
	public static final Color INVALID_COLOR = Color.RED;
	
	public static final int NO_OF_TOWERS = 4;
	public static final int TOWER_ICON_SIZE = 100;
	public static final Vec2i TOWER_TEXT_OFFSET = new Vec2i(65,75);
	public static final int TOWER_ICON_PADDING = 25;
	public static final int TOWER_PANEL_WIDTH = 150;
	public static final Vec2i INFO_BOX_DIMENSIONS = new Vec2i(200,110);
	
	public static final int WALL_INT = 0;
	public static final int WALL_COST = 1;
	public static final Color WALL_COLOR = Color.GRAY;
	public static final Vec2f WALL_SIZE = new Vec2f(0.8f,0.8f);
	public static final int WALL_KEYCODE = 87;
	
	public static final int BASIC_TOWER_INT = 1;
	public static final int BASIC_TOWER_COST = 3;
	public static final Color BASIC_TOWER_COLOR = new Color(121,191,237);
	public static final int BASIC_TOWER_RANGE = 5;
	public static final int BASIC_TOWER_RATE = 175;
	public static final Vec2f BASIC_TOWER_SIZE = new Vec2f(0.8f,0.8f);
	public static final int BASIC_TOWER_KEYCODE = 66;
	public static final int BASIC_TOWER_DAMAGE = 35; //20 per 100 -> 6.6 per $
	
	public static final int GOLD_TOWER_INT = 3;
	public static final int GOLD_TOWER_COST = 13;
	public static final Color GOLD_TOWER_COLOR = new Color(245,184,105);
	public static final int GOLD_TOWER_RATE = 250;
	public static final int GOLD_TOWER_EARNINGS = 1;
	public static final Vec2f GOLD_TOWER_SIZE = new Vec2f(0.8f,0.8f);
	public static final int GOLD_TOWER_KEYCODE = 71;
	public static final int GOLD_TOWER_MAX = 5;
	
	public static final int ADVANCED_TOWER_INT = 2;
	public static final int ADVANCED_TOWER_COST = 10;
	public static final Color ADVANCED_TOWER_COLOR = new Color(237,119,202);
	public static final int ADVANCED_TOWER_RANGE = 7;
	public static final int ADVANCED_TOWER_RATE = 130;
	public static final Vec2f ADVANCED_TOWER_SIZE = new Vec2f(0.8f,0.8f);
	public static final int ADVANCED_TOWER_KEYCODE = 65;
	public static final int ADVANCED_TOWER_DAMAGE = 80; //61.5 per 100 -> 6.15 per $

	public static final int SUPER_TOWER_INT = 4;
	public static final int SUPER_TOWER_COST = 20;
	public static final Color SUPER_TOWER_COLOR = new Color(240,247,153);
	public static final int SUPER_TOWER_RANGE = 7;
	public static final int SUPER_TOWER_RATE = 75;
	public static final Vec2f SUPER_TOWER_SIZE = new Vec2f(0.8f,0.8f);
	public static final int SUPER_TOWER_DAMAGE = 100; //133 per 100 -> 6.15 per $
	
}
