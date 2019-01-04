package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

/**
 * An EQV logic gate.
 */
public class EqvGateModel extends BasicLargeComponent {
	private static final int INPUTS_COUNT = 2;
	private static final int OUTPUTS_COUNT = 1;
	
	public EqvGateModel() {
		super(INPUTS_COUNT, OUTPUTS_COUNT);
	}
		
	@Override
	public String getName() { return "EQV"; }
		
	@Override
	public String getSymbol() { return "="; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitEqv(this); }
	
	@Override
	protected boolean[] compute(boolean[] inputs) {
		return new boolean[] {inputs[0] == inputs[1]};
	}
}
