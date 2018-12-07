package fwcd.circuitbuilder.model.components;

import java.util.List;

import fwcd.circuitbuilder.model.CircuitItemModel;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * A multi-cell circuit component.
 */
public interface CircuitLargeComponentModel extends CircuitItemModel {
	List<InputComponentModel> getInputs();
	
	List<OutputComponentModel> getOutputs();
	
	List<RelativePos> getOccupiedPositions(RelativePos topLeft);
	
	void tick();
}
