package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An edge-triggered delay flip flop.
 */
public class DMasterSlaveModel extends ClockControlledFlipFlop {
	private static final RelativePos[] INPUT_POSITIONS = {
		new RelativePos(0, 0), // D
		new RelativePos(0, 1) // clk
	};
	private static final RelativePos[] OUTPUT_POSITIONS = {
		new RelativePos(3, 0), // Q
		new RelativePos(3, 2) // Q*
	};
	
	private final DLatchModel master = new DLatchModel();
	private final DLatchModel slave = new DLatchModel();
	
	public DMasterSlaveModel() {
		super(INPUT_POSITIONS.length, OUTPUT_POSITIONS.length);
	}
	
	@Override
	public String getName() { return "D-MS-FF"; }
	
	@Override
	public String getSymbol() { return "D"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitDMasterSlave(this); }
	
	@Override
	protected RelativePos getInputPosition(int index) { return INPUT_POSITIONS[index]; }
	
	@Override
	protected RelativePos getOutputPosition(int index) { return OUTPUT_POSITIONS[index]; }
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean d = inputs[0];
		boolean clk = applyInversion(inputs[1]);
		boolean[] masterOut = master.compute(d, !clk);
		
		return slave.compute(masterOut[0], clk);
	}
}
