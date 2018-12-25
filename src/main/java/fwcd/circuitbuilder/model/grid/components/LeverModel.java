package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

public class LeverModel extends BasicEmitter {
	@Override
	public boolean toggle() {
		setPowered(!isPowered());
		return true;
	}
		
	@Override
	public String getName() { return "Lever"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitLever(this); }
}
