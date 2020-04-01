package fwcd.circuitbuilder.model;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitListenableGridModel;
import fwcd.circuitbuilder.model.logic.LogicEditorModel;

/**
 * A model-representation encapsulating the entire abstract application state.
 */
public class CircuitBuilderModel {
	private final CircuitListenableGridModel grid = new CircuitListenableGridModel();
	private final CircuitEngineModel engine = new CircuitEngineModel(grid);
	private final LogicEditorModel logic = new LogicEditorModel();
	
	public CircuitEngineModel getEngine() { return engine; }
	
	public CircuitListenableGridModel getGrid() { return grid; }
	
	public LogicEditorModel getLogicEditor() { return logic; }
}
