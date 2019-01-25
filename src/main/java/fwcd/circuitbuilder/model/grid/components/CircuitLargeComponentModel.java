package fwcd.circuitbuilder.model.grid.components;

import java.util.List;

import fwcd.circuitbuilder.utils.RelativePos;

/**
 * A multi-cell circuit component.
 */
public interface CircuitLargeComponentModel extends CircuitComponentModel {
	List<InputComponentModel> getInputs();
	
	List<OutputComponentModel> getOutputs();
	
	List<RelativePos> getOccupiedPositions(RelativePos topLeft);
	
	void tick();
}
