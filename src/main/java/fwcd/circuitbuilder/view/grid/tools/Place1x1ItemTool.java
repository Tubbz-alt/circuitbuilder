package fwcd.circuitbuilder.view.grid.tools;

import java.util.function.Supplier;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;

/**
 * A simple tool that places a 1x1 component item.
 */
public class Place1x1ItemTool<T extends Circuit1x1ComponentModel> extends CreateItemTool<T> {
	public Place1x1ItemTool(Supplier<T> factory) {
		super(factory);
	}
	
	@Override
	public void onLeftClick(CircuitGridModel grid, CircuitCellModel cell) {
		grid.put(createItem(), cell.getPos());
	}
}
