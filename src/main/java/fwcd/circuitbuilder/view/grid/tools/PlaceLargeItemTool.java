package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Image;
import java.util.function.Supplier;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.components.CircuitLargeComponentModel;
import fwcd.fructose.Option;

/**
 * A simple tool that places a large component item.
 */
public class PlaceLargeItemTool<T extends CircuitLargeComponentModel> extends CreateItemTool<T> {
	public PlaceLargeItemTool(Supplier<T> factory, CircuitItemVisitor<Option<Image>> imageProvider) {
		super(factory, imageProvider, /* useImage */ false);
	}
	
	@Override
	public void onLeftClick(CircuitGridModel grid, CircuitCellModel cell) {
		grid.putLarge(createItem(), cell.getPos());
	}
}
