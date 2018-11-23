package com.fwcd.circuitbuilder.model;

import com.fwcd.circuitbuilder.model.cable.CableColor;
import com.fwcd.fructose.Observable;
import com.fwcd.fructose.Option;

/**
 * A model-representation encapsulating the entire
 * abstract application state.
 */
public class CircuitBuilderModel {
	private final CircuitGridModel grid = new CircuitGridModel();
	private final Observable<Option<CircuitItemModel>> selectedItem = new Observable<>(Option.empty());
	private final Observable<CableColor> selectedColor = new Observable<>(CableColor.RED);
	
	public Observable<CableColor> getSelectedColor() { return selectedColor; }
	
	public Observable<Option<CircuitItemModel>> getSelectedItem() { return selectedItem; }
	
	public CircuitGridModel getGrid() { return grid; }
}