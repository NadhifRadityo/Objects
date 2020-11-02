package io.github.NadhifRadityo.Objects.Units;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.MainTest;
import io.github.NadhifRadityo.Objects.Units.BuiltInUnit.LengthUnit;
import io.github.NadhifRadityo.Objects.Units.BuiltInUnit.MassUnit;
import io.github.NadhifRadityo.Objects.Units.BuiltInUnit.ScreenUnit;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;

public class UnitTest {
	public static void main(String... args) {
		new ScreenUnit(null, null);
		new LengthUnit(null, null);
		new MassUnit(null, null);

		Logger logger = MainTest.LoggerTestWrapper.start(args);
		logger.addListener(record -> {
			String[] kaboom = record.getArgs().get(2).toString().split(" ");
			System.out.println(ArrayUtils.deepToString(kaboom));

			String fromUnit = kaboom[0].replaceAll("[0-9.]", "");
			Unit.UnitType unitSource = getUnitType(fromUnit, LengthUnit.class);
			Unit.UnitType unitTarget = getUnitType(kaboom[1], LengthUnit.class);
			if(unitSource == null || unitTarget == null) return;

			double val = Double.parseDouble(kaboom[0].replace(fromUnit, ""));
			System.out.println(unitSource.convertTo(unitTarget, val).toString());
		});
	}

	public static Unit.UnitType getUnitType(String symbol, Class<? extends Unit> clazz) {
		Unit.UnitTypesHolder[] holders = Unit.getSupportedUnits(clazz);
		for(Unit.UnitTypesHolder holder : holders) for(Unit.UnitType type : holder.getUnitTypes())
			if(type.getSymbol().equalsIgnoreCase(symbol)) return type;
		return null;
	}
}
