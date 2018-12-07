package fwcd.circuitbuilder.view;

import fwcd.circuitbuilder.model.cable.CableColor;
import fwcd.circuitbuilder.view.tools.CircuitTool;
import fwcd.fructose.Observable;
import fwcd.fructose.Option;

/**
 * Contains UI application state associated with the circuit
 * builder.
 */
public class CircuitBuilderContext {
	private final Observable<Option<CircuitTool>> selectedTool = new Observable<>(Option.empty());
	private final Observable<CableColor> selectedColor = new Observable<>(CableColor.RED);
	
	public Observable<CableColor> getSelectedColor() { return selectedColor; }
	
	public Observable<Option<CircuitTool>> getSelectedTool() { return selectedTool; }
}
