package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Image;
import java.util.function.Supplier;

import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.components.CircuitComponentModel;
import fwcd.circuitbuilder.model.grid.components.CircuitLargeComponentModel;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Option;

/**
 * A simple tool that places a large component item.
 */
public class PlaceLargeItemTool<T extends CircuitLargeComponentModel> extends CreateItemTool<T> {
	public PlaceLargeItemTool(Supplier<T> factory, CircuitItemVisitor<Option<Image>> imageProvider) {
		super(factory, imageProvider, /* useImage */ true);
	}
	
	@Override
	public boolean onLeftClick(CircuitGridModel grid, RelativePos pos, Iterable<? extends CircuitComponentModel> components) {
		grid.putLarge(createItem(), pos);
		return true;
	}
}
