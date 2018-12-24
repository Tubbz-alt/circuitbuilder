package fwcd.circuitbuilder.view.grid.theme;

import java.awt.Color;
import java.awt.Image;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.fructose.Option;

/**
 * A theme containing item images for a circuit grid.
 */
public interface CircuitGridTheme {
	String getName();
	
	CircuitItemVisitor<Option<Image>> getItemImageProvider();
	
	Color getGridLineColor();
}
