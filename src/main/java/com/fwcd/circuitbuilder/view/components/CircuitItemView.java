package com.fwcd.circuitbuilder.view.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.NoSuchElementException;

import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.view.CircuitItemImageProvider;
import com.fwcd.circuitbuilder.view.utils.PositionedRenderable;

/**
 * The visual representation of a circuit component.
 */
public class CircuitItemView implements PositionedRenderable {
	private Image image;
	
	public CircuitItemView(CircuitItemModel model) {
		model.accept(new CircuitItemImageProvider(it -> image = it));
		if (image == null) {
			throw new NoSuchElementException("Circuit component of type " + model.getClass().getName() + " has no image representation in CircuitItemImageProvider!");
		}
	}
	
	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		g2d.drawImage(image, pos.getX(), pos.getY(), null);
	}
}