package com.fwcd.circuitbuilder.model;

/**
 * A model-representation encapsulating the entire
 * abstract application state.
 */
public class CircuitBuilderModel {
	private final CircuitGridModel grid = new CircuitGridModel();
	
	public CircuitGridModel getGrid() { return grid; }
}
