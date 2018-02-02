package com.fwcd.circuitbuilder.items.components;

import java.awt.Color;
import java.util.function.Function;

public enum CableColor {
	RED((strength) -> new Color(strength, 0, 0)),
	YELLOW((strength) -> new Color(strength, strength, 0)),
	GREEN((strength) -> new Color(0, strength, 0)),
	BLUE((strength) -> new Color(0, 0, strength));
	
	private final Function<Integer, Color> color;
	
	private CableColor(Function<Integer, Color> color) {
		this.color = color;
	}
	
	public Color getAWTColor(int colorStrength) {
		return color.apply(colorStrength);
	}
}
