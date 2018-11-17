package com.fwcd.circuitbuilder.tools;

import com.fwcd.circuitbuilder.core.CircuitCell;
import com.fwcd.circuitbuilder.items.CircuitItem;

public interface CircuitTool extends CircuitItem {
	void onLeftClick(CircuitCell cell);
	
	default void onRightClick(CircuitCell cell) {
		cell.getComponent().ifPresent((component) -> component.onRightClick());
	}
}
