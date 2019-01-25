package fwcd.circuitbuilder.model.grid.components;

import java.util.List;

import fwcd.circuitbuilder.utils.Positioned;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * A multi-cell circuit component.
 */
public interface CircuitLargeComponentModel extends CircuitComponentModel {
	List<Positioned<InputComponentModel>> getInputs();
	
	List<Positioned<OutputComponentModel>> getOutputs();
	
	List<RelativePos> getOccupiedPositions(RelativePos topLeft);
	
	void tick();
}
