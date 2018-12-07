package fwcd.circuitbuilder.view.utils;

import java.awt.Graphics2D;

import fwcd.circuitbuilder.utils.AbsolutePos;

/**
 * A view that can be drawn at a user-defined
 * position on a graphics context.
 */
public interface PositionedRenderable {
	void render(Graphics2D g2d, AbsolutePos pos);
}