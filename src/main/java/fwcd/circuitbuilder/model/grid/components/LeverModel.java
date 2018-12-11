package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

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
