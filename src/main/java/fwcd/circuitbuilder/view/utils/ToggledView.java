package fwcd.circuitbuilder.view.utils;

import fwcd.fructose.swing.View;

public interface ToggledView extends View {
	/**
	 * Allows the wrapped component to respond
	 * to visibility updates in order to pause
	 * computationally expensive tasks, for example.
	 * 
	 * @param visible - The new visibility of this view
	 */
	default void onUpdateVisibility(boolean visible) {}
}
