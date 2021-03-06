package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Image;
import java.util.function.Supplier;

import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.grid.components.CircuitComponentModel;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Option;

/**
 * A simple tool that places a 1x1 component item.
 */
public class Place1x1ItemTool<T extends Circuit1x1ComponentModel> extends CreateItemTool<T> {
	public Place1x1ItemTool(Supplier<T> factory, CircuitItemVisitor<Option<Image>> imageProvider) {
		super(factory, imageProvider, /* useImage */ true);
	}
	
	@Override
	public boolean onLeftClick(CircuitGridModel grid, RelativePos pos, Iterable<? extends CircuitComponentModel> components) {
		grid.put(createItem(), pos);
		return true;
	}
}
