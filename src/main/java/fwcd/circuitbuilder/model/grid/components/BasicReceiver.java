package fwcd.circuitbuilder.model.grid.components;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.utils.Direction;

/**
 * Provides a basic implementation for simple,
 * powered, (by default non-emitting) components.
 */
public abstract class BasicReceiver implements Circuit1x1ComponentModel {
	private Set<Direction> currentPowerSources = new HashSet<>();
	private Set<Direction> nextPowerSources = new HashSet<>();
	private boolean nowPowered = false;
	private boolean soonPowered = nowPowered;
	
	@Override
	public boolean isPowered() { return nowPowered; }
	
	protected Set<? extends Direction> getPowerSources() { return currentPowerSources; }
	
	@Override
	public boolean outputsTowards(Direction outputDir) { return false; }
	
	@Override
	public void update() {
		nowPowered = soonPowered;
		
		currentPowerSources.clear();
		currentPowerSources.addAll(nextPowerSources);
	}
	
	@Override
	public void tick(Map<Direction, CircuitCellModel> neighbors) {
		soonPowered = false;
		nextPowerSources.clear();
		
		for (Direction dir : neighbors.keySet()) {
			for (Circuit1x1ComponentModel component : neighbors.get(dir).getComponents()) {
				if (component.isPowered() && component.outputsTowards(dir.invert()) && canReceiveFrom(dir)) {
					soonPowered = true;
					nextPowerSources.add(dir);
				}
			}
		}
	}
	
	protected boolean canReceiveFrom(Direction direction) { return true; }
}
