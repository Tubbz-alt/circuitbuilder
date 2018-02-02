package com.fredrikw.circuitbuilder.items.components;

import java.awt.Image;

import javax.swing.Icon;

import com.fredrikw.circuitbuilder.utils.Direction;
import com.fwcd.fructose.swing.ResourceImage;

public class Lever extends SimpleEmitter {
	private static final ResourceImage IMAGE_ON = new ResourceImage("/resources/leverOn.png");
	private static final ResourceImage IMAGE_OFF = new ResourceImage("/resources/leverOff.png");
	
	private boolean isPowered = false;
	
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
		return new Lever();
	}

	@Override
	public void onRightClick() {
		isPowered = !isPowered;
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
