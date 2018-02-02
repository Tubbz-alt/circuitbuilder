package com.fredrikw.circuitbuilder.items.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Map;

import com.fredrikw.circuitbuilder.core.CircuitCell;
import com.fredrikw.circuitbuilder.utils.AbsolutePos;
import com.fredrikw.circuitbuilder.utils.Direction;

/**
 * Provides a basic implementation for simple,
 * emitting components.
 * 
 * @author Fredrik
 *
 */
public abstract class SimpleEmitter extends BasicComponent {
	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		Image image = isPowered() ? getEnabledImage() : getDisabledImage();
		g2d.drawImage(image, pos.getX(), pos.getY(), null);
	}
	
	protected abstract Image getEnabledImage();
	
	protected abstract Image getDisabledImage();

	@Override
	public void onPlace(Map<Direction, CircuitCell> neighbors) {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void tick(Map<Direction, CircuitCell> neighbors) {
		
	}
}
