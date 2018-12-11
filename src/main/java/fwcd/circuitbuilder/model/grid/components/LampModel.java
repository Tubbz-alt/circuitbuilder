package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

public class LampModel extends BasicReceiver {	
	@Override
	public String getName() { return "Lamp"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitLamp(this); }
}
