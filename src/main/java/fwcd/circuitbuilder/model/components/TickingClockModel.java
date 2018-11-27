package fwcd.circuitbuilder.model.components;

import java.util.Map;

import fwcd.circuitbuilder.model.CircuitCellModel;
import fwcd.circuitbuilder.model.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;

public class TickingClockModel extends BasicEmitter {
	private int[] tickDelayModes = {5, 1, 20};
	private int tickDelayModeIndex = 0;
	
	private int maxTickDelay = tickDelayModes[tickDelayModeIndex];
	private int tickDelay = 0;
	
	@Override
	public void toggle() {
		tickDelayModeIndex = (tickDelayModeIndex + 1) % tickDelayModes.length;
		maxTickDelay = tickDelayModes[tickDelayModeIndex];
	}
		
	@Override
	public String getName() { return "TickingClock"; }
	
	@Override
	public void tick(Map<Direction, CircuitCellModel> neighbors) {
		if (tickDelay <= 0) {
			setPowered(!isPowered());
			tickDelay = maxTickDelay;
		} else {
			tickDelay--;
		}
	}
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitTickingClock(this); }
}
