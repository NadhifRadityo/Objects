package io.github.NadhifRadityo.Objects.Units;

import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SecurityUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class Unit {
	protected final static Map<UUID, UnitType> allUnits = new HashMap<>();
	public static UnitType[] getAllUnits() { return allUnits.values().toArray(new UnitType[0]); }
	public static UnitType getUnit(UUID uuid) { return allUnits.get(uuid); }
	protected static void addUnit(UnitType unitType) { allUnits.put(unitType.getUUID(), unitType); }
	protected static void removeUnit(UnitType unitType) { allUnits.remove(unitType.getUUID()); }
	public static boolean isUnitAlreadyAdded(String name, String symbol, Collection<? extends UnitType> allUnits) {
		for(UnitType unit : allUnits) if(unit.getName().equalsIgnoreCase(name) || unit.getSymbol().equalsIgnoreCase(symbol))
			return true; return false;
	} public static boolean isUnitAlreadyAdded(String name, String symbol) { return isUnitAlreadyAdded(name, symbol, allUnits.values()); }

	protected final static Map<Class<? extends Unit>, ArrayList<UnitTypesHolder>> supportedUnits = new HashMap<>();
	public static UnitTypesHolder[] getSupportedUnits(Class<? extends Unit> unit) { return supportedUnits.containsKey(unit) ? supportedUnits.get(unit).toArray(new UnitTypesHolder[0]) : null; }
	public static UnitTypesHolder addSupportedUnit(Class<? extends Unit> unit, UnitTypesHolder unitTypesHolder) {
		if(!supportedUnits.containsKey(unit)) supportedUnits.put(unit, new ArrayList<>());
		supportedUnits.get(unit).add(unitTypesHolder); unitTypesHolder.setParent(unit); return unitTypesHolder;
	}
	public static void removeSupportedUnit(Class<? extends Unit> unit, UnitTypesHolder unitTypesHolder) {
		if(!supportedUnits.containsKey(unit)) return; unitTypesHolder.setParent(null);
		supportedUnits.get(unit).remove(unitTypesHolder);
	}

	protected UnitType unitType;
	protected Object value;

	protected Unit(UnitType unitType, Object value) {
		this.unitType = unitType;
		this.value = value;
	}

	public UnitType getUnitType() { return unitType; }
	public Object getValue() { return value; }
	public String toReadableString() { return unitType.toReadableString(value); }

	public static class UnitTypesHolder {
		protected final List<UnitType> unitTypes;
		protected Class<? extends Unit> parent;

		public UnitTypesHolder(Class<? extends Unit> parent) { this.unitTypes = new ArrayList<>(); this.parent = parent; }
		public UnitTypesHolder() { this(null); }

		public UnitType[] getUnitTypes() { return unitTypes.toArray(new UnitType[0]); }
		public UnitType addUnitType(UnitType unitType) { unitTypes.add(unitType); unitType.setNewInstance(parent); return unitType; }
		public void removeUnitType(UnitType unitType) { unitTypes.remove(unitType); unitType.setNewInstance((Class<Unit>) null); }

		protected Class<? extends Unit> getParent() { return parent; }
		protected void setParent(Class<? extends Unit> parent) { this.parent = parent; unitTypes.forEach(e -> e.setNewInstance(parent)); }

		public boolean isAcceptable(UnitType unitType, Object value) {
			if(parent == null) throw new IllegalArgumentException("Not initialized!");
			return isUnitAlreadyAdded(unitType.getName(), unitType.getSymbol(), unitTypes);
		}
	}

	public static class UnitType {
		protected final UUID uuid;
		protected final Map<UUID, ReferencedCallback.ObjectReferencedCallback> resolvers;
		protected final String name;
		protected final String symbol;
		protected ReferencedCallback<Unit> newInstance;

		public UnitType(String name, String symbol, ReferencedCallback<Unit> newInstance) {
			if(isUnitAlreadyAdded(name, symbol)) throw new IllegalArgumentException();
			this.uuid = SecurityUtils.getRandomUUID();
			this.resolvers = new HashMap<>();
			this.newInstance = newInstance;
			this.name = name;
			this.symbol = symbol;
		} public UnitType(String name, String symbol) { this(name, symbol, (ReferencedCallback<Unit>) null); }
		protected UnitType(String name, String symbol, Class<? extends Unit> clazz) { this(name, symbol, getNewInstance(clazz)); }

		public final UUID getUUID() { return uuid; }
		public String getName() { return name; }
		public String getSymbol() { return symbol; }
		public String toReadableString(Object value) { return value.toString() + symbol; }

		public Map.Entry<UUID, ReferencedCallback.ObjectReferencedCallback>[] getResolvers() { return resolvers.entrySet().toArray(new Map.Entry[0]); }
		public void addResolver(UnitType unitType, ReferencedCallback.ObjectReferencedCallback callback) { resolvers.put(unitType.getUUID(), callback); }
		public void removeResolver(UnitType unitType, ReferencedCallback.ObjectReferencedCallback callback) { resolvers.put(unitType.getUUID(), callback); }

		protected ReferencedCallback<Unit> getNewInstance() { return newInstance; }
		protected void setNewInstance(ReferencedCallback<Unit> newInstance) { this.newInstance = newInstance; }
		protected void setNewInstance(Class<? extends Unit> clazz) { setNewInstance(getNewInstance(clazz)); }

		public Unit convertFrom(UnitType unitType, Object object) {
			if(newInstance == null) throw new IllegalArgumentException("Not initialized!");
			ReferencedCallback.ObjectReferencedCallback callback = resolvers.get(unitType.getUUID());
			if(callback == null) throw new IllegalArgumentException(); return newInstance.get(callback.get(object, unitType), this);
		}
		public Unit convertTo(UnitType unitType, Object object) { return unitType.convertFrom(this, object); }

		private static ReferencedCallback<Unit> getNewInstance(Class<? extends Unit> clazz) {
			if(clazz == null) return null; Constructor<? extends Unit> constructor;
			ArrayList<Class<?>> params = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
				params.add(UnitType.class); params.add(Object.class);
				constructor = ClassUtils.getConstructors(clazz, params)[0];
			} finally { Pool.returnObject(ArrayList.class, params); }
			if(constructor == null) throw new IllegalArgumentException("Can not get default constructor!");
			return (args) -> { try { return (Unit) constructor.newInstance(args[1], args[0]);
			} catch(InstantiationException|IllegalAccessException|InvocationTargetException e) { throw new Error(e); } };
		}
	}
}
