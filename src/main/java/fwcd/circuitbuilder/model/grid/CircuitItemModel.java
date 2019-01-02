package fwcd.circuitbuilder.model.grid;

/**
 * An item that can be placed on a circuit board.
 */
public interface CircuitItemModel {
	String getName();
	
	<T> T accept(CircuitItemVisitor<T> visitor);
	
	default String getSymbol() { return getName(); }
}
