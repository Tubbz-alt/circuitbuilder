package com.fwcd.circuitbuilder.model.cable;

import java.util.function.IntFunction;

import com.fwcd.fructose.draw.DrawColor;

public enum CableColor {
	RED(strength -> new DrawColor(strength, 0, 0)),
	YELLOW(strength -> new DrawColor(strength, strength, 0)),
	GREEN(strength -> new DrawColor(0, strength, 0)),
	BLUE(strength -> new DrawColor(0, 0, strength));
	
	private final IntFunction<DrawColor> color;
	
	private CableColor(IntFunction<DrawColor> color) {
		this.color = color;
	}
	
	public DrawColor getColor(int colorStrength) {
		return color.apply(colorStrength);
	}
}
