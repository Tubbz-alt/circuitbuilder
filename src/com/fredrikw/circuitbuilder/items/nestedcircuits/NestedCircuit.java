package com.fredrikw.circuitbuilder.items.nestedcircuits;

import java.awt.Image;

import com.fredrikw.circuitbuilder.items.CircuitItem;
import com.fredrikw.circuitbuilder.utils.RelativePos;

public interface NestedCircuit extends CircuitItem {
	NestedCircuit copy();
	
	NestedCircuitInput[] getInputs();
	
	NestedCircuitOutput[] getOutputs();
	
	RelativePos[] getOccupiedPositions(RelativePos basePos);
	
	Image getMultiCellImage();
	
	void tick();
}
