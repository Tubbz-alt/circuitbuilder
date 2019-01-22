package fwcd.circuitbuilder.model.grid.components;

import java.util.Arrays;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.Directioned;
import fwcd.circuitbuilder.utils.RelativePos;

public class DemultiplexerModel extends BasicLargeComponent {
	private static final List<Directioned<RelativePos>> INPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 1), Direction.LEFT),
		new Directioned<>(new RelativePos(0, 2), Direction.DOWN) // Control
	);
	private static final List<Directioned<RelativePos>> OUTPUT_POSITIONS = Arrays.asList(
		new Directioned<>(new RelativePos(0, 0), Direction.RIGHT), // Output 0
		new Directioned<>(new RelativePos(0, 2), Direction.RIGHT) // Output 1
	);
	
	public DemultiplexerModel() {
		super(INPUT_POSITIONS.size(), OUTPUT_POSITIONS.size());
	}
	
	@Override
	public String getName() { return "Demultiplexer"; }
	
	@Override
	public String getSymbol() { return "DEMUX"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) {
		return visitor.visitDemultiplexer(this);
	}
	
	@Override
	protected boolean[] compute(boolean... inputs) {
		boolean input = inputs[0];
		boolean control = inputs[1];
		
		return new boolean[] {!control && input, control && input};
	}
}
