package fwcd.circuitbuilder.model.cable;

import java.util.function.BiPredicate;

import fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;

public class CableColorEqualityChecker implements BiPredicate<Circuit1x1ComponentModel, Circuit1x1ComponentModel> {
	@Override
	public boolean test(Circuit1x1ComponentModel t, Circuit1x1ComponentModel u) {
		return t.getColor().flatMap(tColor -> u.getColor().map(tColor::equals)).orElse(false);
	}
}
