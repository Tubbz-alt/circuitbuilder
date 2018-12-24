package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

/**
 * An NAND logic gate.
 */
public class NandModel extends BasicLargeComponent {
	private static final int INPUTS_COUNT = 2;
	private static final int OUTPUTS_COUNT = 1;
	
	public NandModel() {
		super(INPUTS_COUNT, OUTPUTS_COUNT);
	}
		
	@Override
	public String getName() { return "NAND"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitNand(this); }
	
	@Override
	protected boolean[] compute(boolean[] inputs) {
		return new boolean[] {!inputs[0] || !inputs[1]};
	}
}
