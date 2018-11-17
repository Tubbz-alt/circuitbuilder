package com.fwcd.circuitbuilder.items.nestedcircuits;

import java.awt.Image;

import com.fwcd.circuitbuilder.items.CircuitItem;
import com.fwcd.circuitbuilder.utils.RelativePos;

public interface NestedCircuit extends CircuitItem {
	NestedCircuit copy();
	
	NestedCircuitInput[] getInputs();
	
	NestedCircuitOutput[] getOutputs();
	
	RelativePos[] getOccupiedPositions(RelativePos basePos);
	
	Image getMultiCellImage();
	
	void tick();
}
