package com.fwcd.circuitbuilder.view.tools;

import java.awt.Image;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitGridModel;
import com.fwcd.fructose.Option;
import com.fwcd.fructose.swing.ResourceImage;

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
