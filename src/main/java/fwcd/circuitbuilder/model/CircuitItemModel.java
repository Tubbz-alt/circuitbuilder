package fwcd.circuitbuilder.model;

/**
 * An item that can be placed on a circuit board.
 */
public interface CircuitItemModel {
	String getName();
	
	void accept(CircuitItemVisitor visitor);
}
