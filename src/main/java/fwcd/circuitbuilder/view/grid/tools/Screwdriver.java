package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Image;

import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.components.CircuitComponentModel;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Option;
import fwcd.fructose.swing.ResourceImage;

/**
 * A tool used to remove items from the circuit board.
 */
public class Screwdriver implements CircuitTool {
	private static final Image IMAGE = new ResourceImage("/screwdriver.png").get();
	
	@Override
	public String getSymbol() { return "<>"; }
	
	@Override
	public Option<Image> getImage() { return Option.of(IMAGE); }
	
	@Override
	public boolean onLeftClick(CircuitGridModel grid, RelativePos pos, Iterable<? extends CircuitComponentModel> components) {
		grid.clearCell(pos);
		return true;
	}
}
