package com.fwcd.circuitbuilder.view;

import com.fwcd.circuitbuilder.model.cable.CableColor;
import com.fwcd.circuitbuilder.view.tools.CircuitTool;
import com.fwcd.fructose.Observable;
import com.fwcd.fructose.Option;

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
