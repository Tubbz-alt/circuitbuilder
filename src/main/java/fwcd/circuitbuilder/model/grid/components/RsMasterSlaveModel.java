package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An edge-triggered reset-set master slave flip flop.
 */
public class RsMasterSlaveModel extends ClockControlledFlipFlop {
	private static final RelativePos[] INPUT_POSITIONS = {
		new RelativePos(0, 0), // s
		new RelativePos(0, 1), // clk
		new RelativePos(0, 2) // r
	};
	private static final RelativePos[] OUTPUT_POSITIONS = {
		new RelativePos(3, 0), // Q
		new RelativePos(3, 2) // Q*
	};
	
	private final RsLatchModel master = new RsLatchModel();
	private final RsLatchModel slave = new RsLatchModel();
	
	public RsMasterSlaveModel() {
		super(INPUT_POSITIONS.length, OUTPUT_POSITIONS.length);
	}
	
	@Override
	public String getName() { return "RS-MS-FF"; }
	
	@Override
	public String getSymbol() { return "RS"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitRsMasterSlave(this); }
	
	@Override
	protected RelativePos getInputPosition(int index) { return INPUT_POSITIONS[index]; }
	
	@Override
	protected RelativePos getOutputPosition(int index) { return OUTPUT_POSITIONS[index]; }
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean s = inputs[0];
		boolean clk = applyInversion(inputs[1]);
		boolean r = inputs[2];
		boolean[] masterOut = master.compute(s, !clk, r);
		
		return slave.compute(masterOut[0], clk, masterOut[1]);
	}
	
	public RsLatchModel getMaster() { return master; }
	
	public RsLatchModel getSlave() { return slave; }
}
