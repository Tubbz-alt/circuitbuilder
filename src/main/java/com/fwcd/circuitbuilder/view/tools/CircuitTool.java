package com.fwcd.circuitbuilder.view.tools;

import java.awt.Image;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitGridModel;
import com.fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import com.fwcd.fructose.Option;

/**
 * A visual tool used to modify the circuit.
 */
public interface CircuitTool {
	default Option<Image> getImage() { return Option.empty(); }
	
	default void onLeftClick(CircuitGridModel grid, CircuitCellModel cell) {}
	
	default void onRightClick(CircuitGridModel grid, CircuitCellModel cell) {
		for (Circuit1x1ComponentModel component : cell.getComponents()) {
			component.toggle();
		}
	}
}
