package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;

public interface CircuitComponentModel extends CircuitItemModel {
	/**
	 * "Toggles" this component in some way. The precise meaning
	 * may depend on the implementation.
	 * 
	 * @return Whether the component could be toggled
	 */
	default boolean toggle() { return false; }
}
