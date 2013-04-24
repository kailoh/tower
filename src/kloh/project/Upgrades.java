package kloh.project;

import java.awt.Graphics2D;

import cs195n.Vec2i;

public class Upgrades {
	
	protected Board _board;
	protected Vec2i _tile;
	protected Tower _tower;
	
	public Upgrades(Vec2i topLeft, Vec2i dimensions, Board board, Vec2i tile, Tower tower) {
		_board = board;
		_tile = tile;
		_tower = tower;
	}
	
	public void setBound(Vec2i topLeft, Vec2i dimensions) {
	}
	
	public void onMouseClicked(Vec2i location) {
	}
	
	public void shortcut() {
	}
	
	public void draw(Graphics2D aBrush) {
	}
	
}
