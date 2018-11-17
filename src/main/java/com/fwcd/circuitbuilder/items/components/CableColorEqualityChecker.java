package com.fwcd.circuitbuilder.items.components;

import java.util.function.BiPredicate;

public class CableColorEqualityChecker implements BiPredicate<CircuitComponent, CircuitComponent> {
	@Override
	public boolean test(CircuitComponent t, CircuitComponent u) {
		if (t instanceof Cable && u instanceof Cable) {
			return ((Cable) t).getColor() == ((Cable) u).getColor();
		} else {
			return false;
		}
	}
}
