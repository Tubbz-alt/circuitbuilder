package fwcd.circuitbuilder.model.grid.components;

import java.util.Arrays;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.Directioned;
import fwcd.circuitbuilder.utils.RelativePos;

public class MultiplexerModel extends BasicLargeComponent {
	private static final List<Directioned<RelativePos>> INPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 0), Direction.LEFT), // Input 0
		new Directioned<>(new RelativePos(0, 2), Direction.LEFT), // Input 1
		new Directioned<>(new RelativePos(0, 2), Direction.DOWN) // Control
	);
	private static final List<Directioned<RelativePos>> OUTPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 1), Direction.RIGHT)
	);
	
	public MultiplexerModel() {
		super(INPUT_POSITIONS.size(), OUTPUT_POSITIONS.size());
	}
	
	@Override
	public String getName() { return "Multiplexer"; }
	
	@Override
	public String getSymbol() { return "MUX"; }
	
	@Override
	protected Directioned<RelativePos> getInputPosition(int index) { return INPUT_POSITIONS.get(index); }
	
	@Override
	protected Directioned<RelativePos> getOutputPosition(int index) { return OUTPUT_POSITIONS.get(index); }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) {
		return visitor.visitMultiplexer(this);
	}
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean control = inputs[2];
		return new boolean[] {(!control && inputs[0]) || (control && inputs[1])};
	}
}
