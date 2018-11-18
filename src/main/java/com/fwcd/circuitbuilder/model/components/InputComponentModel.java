package com.fwcd.circuitbuilder.model.components;

import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.utils.RelativePos;

/**
 * An input for a large circuit component.
 */
public class InputComponentModel extends BasicReceiver {
	private final RelativePos deltaPos;
	
	public InputComponentModel(RelativePos deltaPos) {
		this.deltaPos = deltaPos;
	}
	
	/**
	 * Fetches the offset from the parent component's
	 * top-left grid position.
	 */
	public RelativePos getDeltaPos() { return deltaPos; }
	
	@Override
	public void accept(CircuitItemVisitor visitor) { visitor.visitInputComponent(this); }
}
