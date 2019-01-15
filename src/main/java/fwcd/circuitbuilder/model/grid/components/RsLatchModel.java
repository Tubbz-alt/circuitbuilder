package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

public class RsLatchModel extends ClockControlledFlipFlop {
	private static final RelativePos[] INPUT_POSITIONS = {
		new RelativePos(0, 0), // s
		new RelativePos(0, 1), // clk
		new RelativePos(0, 2) // r
	};
	private static final RelativePos[] OUTPUT_POSITIONS = {
		new RelativePos(3, 0), // Q
		new RelativePos(3, 2) // Q*
	};
	
	private boolean q = false;
	private boolean qStar = true;
	
	public RsLatchModel() {
		super(INPUT_POSITIONS.length, OUTPUT_POSITIONS.length);
	}
	
	@Override
	public String getName() { return "RS-Latch"; }
	
	@Override
	public String getSymbol() { return "RS"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitRsLatch(this); }
	
	@Override
	protected RelativePos getInputPosition(int index) { return INPUT_POSITIONS[index]; }
	
	@Override
	protected RelativePos getOutputPosition(int index) { return OUTPUT_POSITIONS[index]; }
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean s = inputs[0];
		boolean clk = applyInversion(inputs[1]);
		boolean r = inputs[2];
		boolean nextQ = (s && clk) || !qStar;
		boolean nextQStar = (r && clk) || !q;
		
		q = nextQ;
		qStar = nextQStar;
		return new boolean[] {q, qStar};
	}
}
