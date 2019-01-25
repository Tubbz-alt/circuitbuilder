package fwcd.circuitbuilder.model.grid.components;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An output for a large circuit component.
 */
public class OutputComponentModel extends BasicEmitter implements IOComponentModel {
	private final RelativePos deltaPos;
	private final Set<Direction> outputDirections;
	
	public OutputComponentModel(RelativePos deltaPos, Direction... outputDirections) {
		this.deltaPos = deltaPos;
		this.outputDirections = Stream.of(outputDirections).collect(Collectors.toSet());
	}
		
	@Override
	public String getName() { return "Output"; }
	
	/**
	 * Fetches the offset from the parent component's
	 * top-left grid position.
	 */
	@Override
	public RelativePos getDeltaPos() { return deltaPos; }
	
	@Override
	public void setPowered(boolean powered) { super.setPowered(powered); }
	
	@Override
	public boolean isAtomic() { return false; }
	
	@Override
	public boolean canConnectFrom(Direction direction) { return outputsTowards(direction); }
	
	@Override
	public boolean outputsTowards(Direction outputDir) { return outputDirections.isEmpty() || outputDirections.contains(outputDir); }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitOutputComponent(this); }
}
