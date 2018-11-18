package com.fwcd.circuitbuilder.model.components;

import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.utils.RelativePos;

/**
 * An output for a large circuit component.
 */
public class OutputComponentModel extends BasicEmitter {
	private final RelativePos deltaPos;
	
	public OutputComponentModel(RelativePos deltaPos) {
		this.deltaPos = deltaPos;
	}
	
	/**
	 * Fetches the offset from the parent component's
	 * top-left grid position.
	 */
	public RelativePos getDeltaPos() { return deltaPos; }
	
	@Override
	public void setPowered(boolean powered) { super.setPowered(powered); }
	
	@Override
	public void accept(CircuitItemVisitor visitor) { visitor.visitOutputComponent(this); }
}
