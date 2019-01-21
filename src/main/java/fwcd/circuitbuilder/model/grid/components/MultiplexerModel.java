package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

public class MultiplexerModel extends BasicLargeComponent {
	// TODO: Sided inputs
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) {
		return visitor.visitMultiplexer(this);
	}
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		
	}
}
