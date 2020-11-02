package io.github.NadhifRadityo.Objects.Utilizations.Unit;

import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Units.Unit;

import java.util.Map;
import java.util.Set;

public class StairUnit implements DeadableObject {
	protected final PriorityList<Unit.UnitType> unitTypes;
	protected final int ratio;
	private boolean isDead = false;

	private StairUnit(int ratio) {
		this.unitTypes = Pool.tryBorrow(Pool.getPool(PriorityList.class));
		this.ratio = ratio;
	} public static StairUnit newInstance(int ratio) { return new StairUnit(ratio); }

	@Override public void setDead() { isDead = true; Pool.returnObject(PriorityList.class, unitTypes); }
	@Override public boolean isDead() { return isDead; }

	public Map<Unit.UnitType, Integer> getUnitTypes() { assertNotDead(); return unitTypes.getMap(); }
	public void addUnitType(Unit.UnitType unitType, int order) { assertNotDead(); unitTypes.add(unitType, order); }
//	public void addUnitType(Unit.UnitType unitType) { assertNotDead(); addUnitType(unitType, 0); }
	public void removeUnitType(Unit.UnitType unitType) { assertNotDead(); unitTypes.remove(unitType); }

	public void addResolvers() {
		Set<Map.Entry<Unit.UnitType, Integer>> entries = unitTypes.getMap().entrySet();
		for(Map.Entry<Unit.UnitType, Integer> entry : entries) {
			for(Map.Entry<Unit.UnitType, Integer> entry2 : entries) {
				double delta = Math.pow(ratio, entry2.getValue() - entry.getValue());
				entry.getKey().addResolver(entry2.getKey(), (args) -> ((Number) args[0]).doubleValue() * delta);
			}
		} setDead();
	}
}
