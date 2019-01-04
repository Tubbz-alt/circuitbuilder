package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

/**
 * An OR logic gate.
 */
public class OrGateModel extends BasicLargeComponent {
	private static final int INPUTS_COUNT = 2;
	private static final int OUTPUTS_COUNT = 1;
	
	public OrGateModel() {
		super(INPUTS_COUNT, OUTPUTS_COUNT);
	}
		
	@Override
	public String getName() { return "OR"; }
		
	@Override
	public String getSymbol() { return ">=1"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitOr(this); }
	
	@Override
	protected boolean[] compute(boolean[] inputs) {
		return new boolean[] {inputs[0] || inputs[1]};
	}
}
