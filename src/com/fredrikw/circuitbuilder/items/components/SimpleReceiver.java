package com.fredrikw.circuitbuilder.items.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Map;

import com.fredrikw.circuitbuilder.core.CircuitCell;
import com.fredrikw.circuitbuilder.utils.AbsolutePos;
import com.fredrikw.circuitbuilder.utils.Direction;

/**
 * Provides a basic implementation for simple,
 * powered, (by default non-emitting) components.
 * 
 * @author Fredrik
 *
 */
public abstract class SimpleReceiver extends BasicComponent {
	private boolean nowPowered = false;
	private boolean soonPowered = nowPowered;
	
	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		Image image = nowPowered ? getEnabledImage() : getDisabledImage();
		g2d.drawImage(image, pos.getX(), pos.getY(), null);
	}
	
	protected abstract Image getEnabledImage();
	
	protected abstract Image getDisabledImage();

	@Override
	public void onPlace(Map<Direction, CircuitCell> neighbors) {
		
	}

	@Override
	public void update() {
		nowPowered = soonPowered;
	}

	@Override
	public void tick(Map<Direction, CircuitCell> neighbors) {
		soonPowered = false;
		
		for (Direction dir : neighbors.keySet()) {
			for (CircuitComponent component : neighbors.get(dir)) {
				if (component.isPowered() && component.outputsTowards(dir.invert())) {
					soonPowered = true;
				}
			}
		}
	}

	@Override
	public boolean isPowered() {
		return nowPowered;
	}
	
	@Override
	public boolean outputsTowards(Direction dir) {
		return false;
	}
}
