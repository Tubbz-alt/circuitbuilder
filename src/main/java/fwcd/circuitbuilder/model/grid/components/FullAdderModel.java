package fwcd.circuitbuilder.model.grid.components;

import java.util.Arrays;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.Directioned;
import fwcd.circuitbuilder.utils.RelativePos;

public class FullAdderModel extends BasicLargeComponent {
	private static final List<Directioned<RelativePos>> INPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 0), Direction.UP), // a
		new Directioned<>(new RelativePos(1, 0), Direction.UP), // b
		new Directioned<>(new RelativePos(2, 0), Direction.UP) // carryIn
	);
	private static final List<Directioned<RelativePos>> OUTPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 1), Direction.DOWN), // carryOut
		new Directioned<>(new RelativePos(2, 1), Direction.DOWN) // sum
	);
	
	public FullAdderModel() {
		super(INPUT_POSITIONS.size(), OUTPUT_POSITIONS.size());
	}
	
	@Override
	public String getName() { return "FullAdder"; }
	
	@Override
	public String getSymbol() { return "FA"; }
	
	@Override
	protected Directioned<RelativePos> getInputPosition(int index) { return INPUT_POSITIONS.get(index); }
	
	@Override
	protected Directioned<RelativePos> getOutputPosition(int index) { return OUTPUT_POSITIONS.get(index); }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) {
		return visitor.visitFullAdder(this);
	}
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		// Inputs
		boolean a = inputs[0];
		boolean b = inputs[1];
		boolean carryIn = inputs[2];
		// Outputs
		boolean carryOut = (carryIn && (a ^ b)) || (a && b);
		boolean sum = a ^ b ^ carryIn;
		return new boolean[] {carryOut, sum};
	}
}
