package com.fredrikw.circuitbuilder.items.components;

import java.awt.Image;
import java.util.Map;

import javax.swing.Icon;

import com.fredrikw.circuitbuilder.core.CircuitCell;
import com.fredrikw.circuitbuilder.utils.Direction;
import com.fwcd.fructose.swing.ResourceImage;

public class TickingClock extends SimpleEmitter {
	private static final ResourceImage IMAGE_ON = new ResourceImage("/resources/clockOn.png");
	private static final ResourceImage IMAGE_OFF = new ResourceImage("/resources/clockOff.png");
	
	private boolean isPowered = false;
	
	private int[] tickDelayModes = {5, 1, 20};
	private int tickDelayModeIndex = 0;
	
	private int maxTickDelay = tickDelayModes[tickDelayModeIndex];
	private int tickDelay = 0;
	
	@Override
	public boolean isPowered() {
		return isPowered;
	}

	@Override
	public boolean outputsTowards(Direction outputDir) {
		return true;
	}

	@Override
	public CircuitComponent copy() {
		return new TickingClock();
	}
	
	@Override
	public void onRightClick() {
		tickDelayModeIndex++;
		maxTickDelay = tickDelayModes[tickDelayModeIndex % tickDelayModes.length];
	}

	@Override
	public void tick(Map<Direction, CircuitCell> neighbors) {
		if (tickDelay <= 0) {
			isPowered = !isPowered;
			tickDelay = maxTickDelay;
		} else {
			tickDelay--;
		}
	}

	@Override
	public Icon getIcon() {
		return IMAGE_ON.getAsIcon();
	}

	@Override
	protected Image getEnabledImage() {
		return IMAGE_ON.get();
	}

	@Override
	protected Image getDisabledImage() {
		return IMAGE_OFF.get();
	}
}
