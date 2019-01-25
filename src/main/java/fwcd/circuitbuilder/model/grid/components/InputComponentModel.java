package fwcd.circuitbuilder.model.grid.components;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An input for a large circuit component.
 */
public class InputComponentModel extends BasicReceiver implements IOComponentModel {
	private final RelativePos deltaPos;
	private final Set<Direction> inputDirections;
	
	public InputComponentModel(RelativePos deltaPos, Direction... inputDirections) {
		this.deltaPos = deltaPos;
		this.inputDirections = Stream.of(inputDirections).collect(Collectors.toSet());
	}
	
	/**
	 * Fetches the offset from the parent component's
	 * top-left grid position.
	 */
	@Override
	public RelativePos getDeltaPos() { return deltaPos; }
		
	@Override
	public String getName() { return "Input"; }
	
	@Override
	public boolean isAtomic() { return false; }
	
	@Override
	public boolean canConnectFrom(Direction direction) { return inputDirections.contains(direction); }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitInputComponent(this); }
}
