package com.fwcd.circuitbuilder.model.components;

import java.util.Map;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.utils.Direction;

public class InverterModel implements Circuit1x1ComponentModel {
	private Direction facing = Direction.UP;
	private boolean nowEmitting = true;
	private boolean soonEmitting = nowEmitting;
	
	@Override
	public void tick(Map<Direction, CircuitCellModel> neighbors) {
		soonEmitting = true;
		
		for (Direction dir : neighbors.keySet()) {
			if (dir.equals(facing.invert())) {
				for (Circuit1x1ComponentModel component : neighbors.get(dir).getComponents()) {
					if (component.isPowered() && component.outputsTowards(facing)) {
						soonEmitting = false;
					}
				}
			}
		}
	}
	
	@Override
	public void toggle() {
		facing = facing.cycle();
	}
	
	@Override
	public void update() {
		nowEmitting = soonEmitting;
	}
		
	@Override
	public String getName() { return "Inverter"; }
	
	@Override
	public boolean isPowered() { return nowEmitting; }
	
	@Override
	public void accept(CircuitItemVisitor visitor) { visitor.visitInverter(this); }
	
	public Direction getFacing() { return facing; }
	
	@Override
	public boolean outputsTowards(Direction outputDir) { return outputDir.equals(facing); }
}
