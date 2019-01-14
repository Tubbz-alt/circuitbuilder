package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

/**
 * An XOR logic gate.
 */
public class XorGateModel extends BasicLargeComponent {
	private static final int INPUT_COUNT = 2;
	private static final int OUTPUT_COUNT = 1;
	
	public XorGateModel() {
		super(INPUT_COUNT, OUTPUT_COUNT);
	}
		
	@Override
	public String getName() { return "XOR"; }
		
	@Override
	public String getSymbol() { return "/="; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitXor(this); }
	
	@Override
	protected boolean[] compute(boolean[] inputs) {
		return new boolean[] {inputs[0] ^ inputs[1]};
	}
}
