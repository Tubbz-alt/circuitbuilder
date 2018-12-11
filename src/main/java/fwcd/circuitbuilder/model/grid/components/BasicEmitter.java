package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.utils.Direction;

/**
 * Provides a basic implementation for simple,
 * emitting components.
 */
public abstract class BasicEmitter implements Circuit1x1ComponentModel {
	private boolean powered = false;
	
	@Override
	public boolean isEmitter() { return true; }
	
	@Override
	public boolean outputsTowards(Direction outputDir) { return true; }
	
	protected void setPowered(boolean powered) { this.powered = powered; }
	
	@Override
	public boolean isPowered() { return powered; }
}
