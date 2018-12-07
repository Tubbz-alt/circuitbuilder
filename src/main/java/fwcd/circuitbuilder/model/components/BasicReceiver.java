package fwcd.circuitbuilder.model.components;

import java.util.Map;

import fwcd.circuitbuilder.model.CircuitCellModel;
import fwcd.circuitbuilder.utils.Direction;

/**
 * Provides a basic implementation for simple,
 * powered, (by default non-emitting) components.
 */
public abstract class BasicReceiver implements Circuit1x1ComponentModel {
	private boolean nowPowered = false;
	private boolean soonPowered = nowPowered;
	
	@Override
	public boolean isPowered() { return nowPowered; }
	
	@Override
	public boolean outputsTowards(Direction outputDir) { return false; }
	
	@Override
	public void update() {
		nowPowered = soonPowered;
	}
	
	@Override
	public void tick(Map<Direction, CircuitCellModel> neighbors) {
		soonPowered = false;
		
		for (Direction dir : neighbors.keySet()) {
			for (Circuit1x1ComponentModel component : neighbors.get(dir).getComponents()) {
				if (component.isPowered() && component.outputsTowards(dir.invert())) {
					soonPowered = true;
				}
			}
		}
	}
}
