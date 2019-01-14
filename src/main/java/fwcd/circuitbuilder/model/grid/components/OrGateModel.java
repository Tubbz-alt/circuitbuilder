package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

/**
 * An OR logic gate.
 */
public class OrGateModel extends BasicLargeComponent {
	private static final int INPUT_COUNT = 2;
	private static final int OUTPUT_COUNT = 1;
	
	public OrGateModel() {
		super(INPUT_COUNT, OUTPUT_COUNT);
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
