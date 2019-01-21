package fwcd.circuitbuilder.model.grid.components;

import java.util.Arrays;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.Directioned;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An edge-triggered delay flip flop.
 */
public class DMasterSlaveModel extends ClockControlledFlipFlop {
	private static final List<Directioned<RelativePos>> INPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 0), Direction.LEFT), // D
		new Directioned<>(new RelativePos(0, 1), Direction.LEFT) // clk
	);
	private static final List<Directioned<RelativePos>> OUTPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(3, 0), Direction.RIGHT), // Q
		new Directioned<>(new RelativePos(3, 2), Direction.RIGHT) // Q*
	);
	
	private final DLatchModel master = new DLatchModel();
	private final DLatchModel slave = new DLatchModel();
	
	public DMasterSlaveModel() {
		super(INPUT_POSITIONS.size(), OUTPUT_POSITIONS.size());
	}
	
	@Override
	public String getName() { return "D-MS-FF"; }
	
	@Override
	public String getSymbol() { return "D"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitDMasterSlave(this); }
	
	@Override
	protected Directioned<RelativePos> getInputPosition(int index) { return INPUT_POSITIONS.get(index); }
	
	@Override
	protected Directioned<RelativePos> getOutputPosition(int index) { return OUTPUT_POSITIONS.get(index); }
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean d = inputs[0];
		boolean clk = applyInversion(inputs[1]);
		boolean[] masterOut = master.compute(d, !clk);
		
		return slave.compute(masterOut[0], clk);
	}
}
