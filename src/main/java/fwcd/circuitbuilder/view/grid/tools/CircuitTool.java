package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Graphics2D;
import java.awt.Image;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.view.utils.PositionedRenderable;
import fwcd.fructose.Option;
import fwcd.fructose.OptionInt;

/**
 * A visual tool used to modify the circuit.
 */
public interface CircuitTool extends PositionedRenderable {
	String getName();
	
	default Option<Image> getImage() { return Option.empty(); }
	
	default boolean onLeftClick(CircuitGridModel grid, CircuitCellModel cell) { return false; }
	
	default OptionInt getWidth() { return getImage().mapToInt(it -> it.getWidth(null)); }
	
	default OptionInt getHeight() { return getImage().mapToInt(it -> it.getHeight(null)); }
	
	default boolean useImageButton() { return true; }
	
	@Override
	default void render(Graphics2D g2d, AbsolutePos pos) {
		getImage().ifPresent(img -> g2d.drawImage(img, pos.getX(), pos.getY(), null));
	}
	
	default boolean onRightClick(CircuitGridModel grid, CircuitCellModel cell) {
		boolean handled = false;
		for (Circuit1x1ComponentModel component : cell.getComponents()) {
			component.toggle();
			handled |= true;
		}
		return handled;
	}
}
