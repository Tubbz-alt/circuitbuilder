package com.fwcd.circuitbuilder.view.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.NoSuchElementException;

import com.fwcd.circuitbuilder.model.components.CircuitLargeComponentModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.view.CircuitItemImageProvider;
import com.fwcd.circuitbuilder.view.utils.PositionedRenderable;

/**
 * A visual representation of a multi-cell component.
 */
public class CircuitLargeComponentView implements PositionedRenderable {
	private Image image;
	
	public CircuitLargeComponentView(CircuitLargeComponentModel model) {
		new CircuitItemImageProvider(it -> image = it);
		if (image == null) {
			throw new NoSuchElementException("Circuit component of type " + model.getClass().getName() + " has no image representation in CircuitItemImageProvider!");
		}
	}
	
	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		g2d.drawImage(image, pos.getX(), pos.getY(), null);
	}
}