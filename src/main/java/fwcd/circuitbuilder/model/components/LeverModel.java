package fwcd.circuitbuilder.model.components;

import fwcd.circuitbuilder.model.CircuitItemVisitor;

public class LeverModel extends BasicEmitter {
	@Override
	public void toggle() {
		setPowered(!isPowered());
	}
		
	@Override
	public String getName() { return "Lever"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitLever(this); }
}
