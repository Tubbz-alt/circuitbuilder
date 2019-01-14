package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.RelativePos;

public class RsMasterSlaveModel extends BasicLargeComponent {
	private static final int INPUT_COUNT = 3;
	private static final int OUTPUT_COUNT = 2;
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
		super(INPUT_COUNT, OUTPUT_COUNT);
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
		boolean clk = inputs[1];
		boolean r = inputs[2];
		boolean[] masterOut = master.compute(s, !clk, r);
		
		return slave.compute(masterOut[0], clk, masterOut[1]);
	}
}
