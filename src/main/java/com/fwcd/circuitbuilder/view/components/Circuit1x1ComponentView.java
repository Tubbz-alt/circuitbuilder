package com.fwcd.circuitbuilder.view.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.NoSuchElementException;

import com.fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.view.CircuitItemImageProvider;
import com.fwcd.circuitbuilder.view.utils.PositionedRenderable;

/**
 * The visual representation of a single-cell
 * circuit component.
 */
public class Circuit1x1ComponentView implements PositionedRenderable {
	private Image image;
	
	public Circuit1x1ComponentView(Circuit1x1ComponentModel model) {
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