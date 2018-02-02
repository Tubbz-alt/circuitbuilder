package com.fredrikw.circuitbuilder.tools;

import com.fredrikw.circuitbuilder.core.CircuitCell;
import com.fredrikw.circuitbuilder.items.CircuitItem;

public interface CircuitTool extends CircuitItem {
	void onLeftClick(CircuitCell cell);
	
	default void onRightClick(CircuitCell cell) {
		cell.getComponent().ifPresent((component) -> component.onRightClick());
	}
}
