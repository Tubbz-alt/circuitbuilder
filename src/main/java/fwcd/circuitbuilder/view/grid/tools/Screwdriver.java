package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Image;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.fructose.Option;
import fwcd.fructose.swing.ResourceImage;

/**
 * A tool used to remove items from the circuit board.
 */
public class Screwdriver implements CircuitTool {
	private static final Image IMAGE = new ResourceImage("/screwdriver.png").get();
	
	@Override
	public String getName() { return "Screwdriver"; }
	
	@Override
	public Option<Image> getImage() { return Option.of(IMAGE); }
	
	@Override
	public void onLeftClick(CircuitGridModel grid, CircuitCellModel cell) {
		grid.clearCell(cell.getPos());
	}
}
