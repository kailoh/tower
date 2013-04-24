package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import kloh.gameengine.UIElement;
import kloh.gameengine.Viewport;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class ExplodingLayer implements UIElement {
	
	private ArrayList<Unit> _units;
	private Viewport _viewport;
	private HashMap<Unit, HealthBar> _healthBarMap;
	
	public ExplodingLayer(ArrayList<Unit> units, Viewport viewport) {
		_healthBarMap = new HashMap<Unit, HealthBar>();
		_units = units;
		_viewport = viewport;
		for (int i=0; i<_units.size(); i++) {
			if (_units.get(i) instanceof ExplodingUnit) {
				_healthBarMap.put(_units.get(i), new HealthBar(new Vec2i(30, 4), Constants.EXPLODING_UNIT_TIME,Color.RED));
			}
		}
	}

	@Override
	public void setBound(Vec2i topLeft, Vec2i uiDimensions) {
	}
	
	public void addNewUnit(ExplodingUnit unit) {
		_healthBarMap.put(unit, new HealthBar(new Vec2i(30,4), Constants.EXPLODING_UNIT_TIME, Color.RED));
	}

	@Override
	public void draw(Graphics2D aBrush) {
		for (int i=0; i<_units.size(); i++) {
			if (_units.get(i) instanceof ExplodingUnit) {
				Vec2i unitScreenCoordinates = _viewport.gameToScreen(_units.get(i).getLocation().plus(new Vec2f(0f,-0.2f)));
				int timeLeft = ((ExplodingUnit)_units.get(i)).getTime();
				_healthBarMap.get(_units.get(i)).changeCurrentLife(timeLeft);
				if (timeLeft <= Constants.EXPLODING_UNIT_TIME) {
					_healthBarMap.get(_units.get(i)).setLocation(unitScreenCoordinates);
					_healthBarMap.get(_units.get(i)).draw(aBrush);
				}
			}
		}
	}

}
