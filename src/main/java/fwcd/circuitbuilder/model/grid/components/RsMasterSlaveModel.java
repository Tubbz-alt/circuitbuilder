package fwcd.circuitbuilder.model.grid.components;

import java.util.Arrays;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.Directioned;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An edge-triggered reset-set master slave flip flop.
 */
public class RsMasterSlaveModel extends ClockControlledFlipFlop {
	private static final List<Directioned<RelativePos>> INPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 0), Direction.LEFT), // s
		new Directioned<>(new RelativePos(0, 1), Direction.LEFT), // clk
		new Directioned<>(new RelativePos(0, 2), Direction.LEFT) // r
	);
	private static final List<Directioned<RelativePos>> OUTPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(3, 0), Direction.RIGHT), // Q
		new Directioned<>(new RelativePos(3, 2), Direction.RIGHT) // Q*
	);
	
	private final RsLatchModel master = new RsLatchModel();
	private final RsLatchModel slave = new RsLatchModel();
	
	public RsMasterSlaveModel() {
		super(INPUT_POSITIONS.size(), OUTPUT_POSITIONS.size());
	}
	
	@Override
	public String getName() { return "RS-MS-FF"; }
	
	@Override
	public String getSymbol() { return "RS"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitRsMasterSlave(this); }
	
	@Override
	protected Directioned<RelativePos> getInputPosition(int index) { return INPUT_POSITIONS.get(index); }
	
	@Override
	protected Directioned<RelativePos> getOutputPosition(int index) { return OUTPUT_POSITIONS.get(index); }
	
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
