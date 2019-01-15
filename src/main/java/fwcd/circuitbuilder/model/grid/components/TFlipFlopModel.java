package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * A toggle flip flop. Changes its state
 * at every clock tick if T is true.
 */
public class TFlipFlopModel extends ClockControlledFlipFlop {
	private static final RelativePos[] INPUT_POSITIONS = {
		new RelativePos(0, 0), // T
		new RelativePos(0, 1) // clk
	};
	private static final RelativePos[] OUTPUT_POSITIONS = {
		new RelativePos(3, 0), // Q
		new RelativePos(3, 2) // Q*
	};
	
	private final JkFlipFlopModel jk = new JkFlipFlopModel();
	
	public TFlipFlopModel() {
		super(INPUT_POSITIONS.length, OUTPUT_POSITIONS.length);
	}
	
	@Override
	public String getName() { return "T-FF"; }
	
	@Override
	public String getSymbol() { return "T"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitTFlipFlop(this); }
	
	@Override
	protected RelativePos getInputPosition(int index) { return INPUT_POSITIONS[index]; }
	
	@Override
	protected RelativePos getOutputPosition(int index) { return OUTPUT_POSITIONS[index]; }
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean t = inputs[0];
		boolean clk = applyInversion(inputs[1]);
		return jk.compute(t, clk, t);
	}
}
