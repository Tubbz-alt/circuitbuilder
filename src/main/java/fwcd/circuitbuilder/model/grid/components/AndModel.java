package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

/**
 * An AND logic gate.
 */
public class AndModel extends BasicLargeComponent {
	private static final int INPUTS_COUNT = 2;
	private static final int OUTPUTS_COUNT = 1;
	
	public AndModel() {
		super(INPUTS_COUNT, OUTPUTS_COUNT);
	}
		
	@Override
	public String getName() { return "AND"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitAnd(this); }
	
	@Override
	protected boolean[] compute(boolean[] inputs) {
		return new boolean[] {inputs[0] && inputs[1]};
	}
}
