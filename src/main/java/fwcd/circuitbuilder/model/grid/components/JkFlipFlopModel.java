package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An edge-triggered, jump-kill flip flop
 */
public class JkFlipFlopModel extends ClockControlledFlipFlop {
	private static final RelativePos[] INPUT_POSITIONS = {
		new RelativePos(0, 0), // J
		new RelativePos(0, 1), // clk
		new RelativePos(0, 2) // K
	};
	private static final RelativePos[] OUTPUT_POSITIONS = {
		new RelativePos(3, 0), // Q
		new RelativePos(3, 2) // Q*
	};
	
	private final RsMasterSlaveModel rs = new RsMasterSlaveModel();
	
	public JkFlipFlopModel() {
		super(INPUT_POSITIONS.length, OUTPUT_POSITIONS.length);
	}
	
	@Override
	public String getName() { return "JK-FF"; }
	
	@Override
	public String getSymbol() { return "JK"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitJkFlipFlop(this); }
	
	@Override
	protected RelativePos getInputPosition(int index) { return INPUT_POSITIONS[index]; }
	
	@Override
	protected RelativePos getOutputPosition(int index) { return OUTPUT_POSITIONS[index]; }
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean j = inputs[0];
		boolean clk = applyInversion(inputs[1]);
		boolean k = inputs[2];
		boolean q = rs.getSlave().getQ();
		boolean qStar = rs.getSlave().getQStar();
		
		return rs.compute(qStar && j, clk, q && k);
	}
}
