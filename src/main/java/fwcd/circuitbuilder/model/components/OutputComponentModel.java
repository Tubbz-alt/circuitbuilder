package fwcd.circuitbuilder.model.components;

import fwcd.circuitbuilder.model.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An output for a large circuit component.
 */
public class OutputComponentModel extends BasicEmitter {
	private final RelativePos deltaPos;
	
	public OutputComponentModel(RelativePos deltaPos) {
		this.deltaPos = deltaPos;
	}
		
	@Override
	public String getName() { return "Output"; }
	
	/**
	 * Fetches the offset from the parent component's
	 * top-left grid position.
	 */
	public RelativePos getDeltaPos() { return deltaPos; }
	
	@Override
	public void setPowered(boolean powered) { super.setPowered(powered); }
	
	@Override
	public boolean isAtomic() { return false; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitOutputComponent(this); }
}
