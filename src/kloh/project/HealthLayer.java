package kloh.project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import kloh.gameengine.UIElement;
import kloh.gameengine.Viewport;
import cs195n.Vec2i;

public class HealthLayer implements UIElement {
	
	private ArrayList<Unit> _units;
	private Viewport _viewport;
	private HashMap<Unit, HealthBar> _healthBarMap;
	
	public HealthLayer(ArrayList<Unit> units, Viewport viewport) {
		_healthBarMap = new HashMap<Unit, HealthBar>();
		_units = units;
		_viewport = viewport;
		for (int i=0; i<_units.size(); i++) {
			_healthBarMap.put(_units.get(i), new HealthBar(new Vec2i(30, 4), _units.get(i).getHealth(), Color.GREEN));
		}
	}

	@Override
	public void setBound(Vec2i topLeft, Vec2i uiDimensions) {
	}
	
	public void addNewUnit(Unit unit) {
		_healthBarMap.put(unit, new HealthBar(new Vec2i(30,4), unit.getHealth(), Color.GREEN));
	}

	@Override
	public void draw(Graphics2D aBrush) {
		for (int i=0; i<_units.size(); i++) {
			Vec2i unitScreenCoordinates = _viewport.gameToScreen(_units.get(i).getLocation());
			_healthBarMap.get(_units.get(i)).changeCurrentLife(_units.get(i).getHealth());
			_healthBarMap.get(_units.get(i)).setLocation(unitScreenCoordinates);
			_healthBarMap.get(_units.get(i)).draw(aBrush);
		}
	}

}
