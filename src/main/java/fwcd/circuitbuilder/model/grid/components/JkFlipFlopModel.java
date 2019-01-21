package fwcd.circuitbuilder.model.grid.components;

import java.util.Arrays;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Directioned;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An edge-triggered, jump-kill flip flop
 */
public class JkFlipFlopModel extends ClockControlledFlipFlop {
	private static final List<Directioned<RelativePos>> INPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 0)), // J
		new Directioned<>(new RelativePos(0, 1)), // clk
		new Directioned<>(new RelativePos(0, 2)) // K
	);
	private static final List<Directioned<RelativePos>> OUTPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(3, 0)), // Q
		new Directioned<>(new RelativePos(3, 2)) // Q*
	);
	
	private final RsMasterSlaveModel rs = new RsMasterSlaveModel();
	
	public JkFlipFlopModel() {
		super(INPUT_POSITIONS.size(), OUTPUT_POSITIONS.size());
	}
	
	@Override
	public String getName() { return "JK-FF"; }
	
	@Override
	public String getSymbol() { return "JK"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitJkFlipFlop(this); }
	
	@Override
	protected Directioned<RelativePos> getInputPosition(int index) { return INPUT_POSITIONS.get(index); }
	
	@Override
	protected Directioned<RelativePos> getOutputPosition(int index) { return OUTPUT_POSITIONS.get(index); }
	
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
