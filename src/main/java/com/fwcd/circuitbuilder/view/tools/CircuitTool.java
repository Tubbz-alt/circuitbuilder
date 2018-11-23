package com.fwcd.circuitbuilder.view.tools;

import java.awt.Graphics2D;
import java.awt.Image;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitGridModel;
import com.fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.view.utils.PositionedRenderable;
import com.fwcd.fructose.Option;

/**
 * A visual tool used to modify the circuit.
 */
public interface CircuitTool extends PositionedRenderable {
	String getName();
	
	default Option<Image> getImage() { return Option.empty(); }
	
	default void onLeftClick(CircuitGridModel grid, CircuitCellModel cell) {}
	
	@Override
	default void render(Graphics2D g2d, AbsolutePos pos) {
		getImage().ifPresent(img -> g2d.drawImage(img, pos.getX(), pos.getY(), null));
	}
	
	default void onRightClick(CircuitGridModel grid, CircuitCellModel cell) {
		for (Circuit1x1ComponentModel component : cell.getComponents()) {
			component.toggle();
		}
	}
}
