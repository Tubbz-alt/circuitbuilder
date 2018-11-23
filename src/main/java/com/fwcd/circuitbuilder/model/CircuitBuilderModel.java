package com.fwcd.circuitbuilder.model;

/**
 * A model-representation encapsulating the entire
 * abstract application state.
 */
public class CircuitBuilderModel {
	private final CircuitGridModel grid = new CircuitGridModel();
	private final CircuitEngineModel engine = new CircuitEngineModel(grid);
	
	public CircuitEngineModel getEngine() { return engine; }
	
	public CircuitGridModel getGrid() { return grid; }
}
