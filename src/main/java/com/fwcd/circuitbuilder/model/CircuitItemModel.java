package com.fwcd.circuitbuilder.model;

/**
 * An item that can be placed on a circuit board.
 */
public interface CircuitItemModel {
	void accept(CircuitItemVisitor visitor);
}
