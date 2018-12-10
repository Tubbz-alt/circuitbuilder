package fwcd.circuitbuilder.view.tools;

import java.util.function.Supplier;

import fwcd.circuitbuilder.model.CircuitCellModel;
import fwcd.circuitbuilder.model.CircuitGridModel;
import fwcd.circuitbuilder.model.components.CircuitLargeComponentModel;

/**
 * A simple tool that places a large component item.
 */
public class PlaceLargeItemTool<T extends CircuitLargeComponentModel> extends CreateItemTool<T> {
	public PlaceLargeItemTool(Supplier<T> factory) {
		super(factory);
	}
	
	@Override
	public void onLeftClick(CircuitGridModel grid, CircuitCellModel cell) {
		grid.putLarge(createItem(), cell.getPos());
	}
}
