package fwcd.circuitbuilder.view.tools;

import java.awt.Graphics2D;
import java.awt.Image;

import fwcd.circuitbuilder.model.CircuitCellModel;
import fwcd.circuitbuilder.model.CircuitGridModel;
import fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.view.utils.PositionedRenderable;
import fwcd.fructose.Option;

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
