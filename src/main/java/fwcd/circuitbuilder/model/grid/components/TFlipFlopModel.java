package fwcd.circuitbuilder.model.grid.components;

import java.util.Arrays;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.Directioned;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * A toggle flip flop. Changes its state
 * at every clock tick if T is true.
 */
public class TFlipFlopModel extends ClockControlledFlipFlop {
	private static final List<Directioned<RelativePos>> INPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 0), Direction.LEFT), // T
		new Directioned<>(new RelativePos(0, 1), Direction.LEFT) // clk
	);
	private static final List<Directioned<RelativePos>> OUTPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(3, 0), Direction.RIGHT), // Q
		new Directioned<>(new RelativePos(3, 2), Direction.RIGHT) // Q*
	);
	
	private final JkFlipFlopModel jk = new JkFlipFlopModel();
	
	public TFlipFlopModel() {
		super(INPUT_POSITIONS.size(), OUTPUT_POSITIONS.size());
	}
	
	@Override
	public String getName() { return "T-FF"; }
	
	@Override
	public String getSymbol() { return "T"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitTFlipFlop(this); }
	
	@Override
	protected Directioned<RelativePos> getInputPosition(int index) { return INPUT_POSITIONS.get(index); }
	
	@Override
	protected Directioned<RelativePos> getOutputPosition(int index) { return OUTPUT_POSITIONS.get(index); }
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean t = inputs[0];
		boolean clk = applyInversion(inputs[1]);
		return jk.compute(t, clk, t);
	}
}
