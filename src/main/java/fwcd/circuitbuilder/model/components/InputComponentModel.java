package fwcd.circuitbuilder.model.components;

import fwcd.circuitbuilder.model.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

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
	public String getName() { return "Input"; }
	
	@Override
	public boolean isAtomic() { return false; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitInputComponent(this); }
}
