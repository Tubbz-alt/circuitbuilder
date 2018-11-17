package com.fwcd.circuitbuilder.items.components;

import java.awt.Image;

import javax.swing.Icon;

import com.fwcd.fructose.swing.ResourceImage;

public class Lamp extends SimpleReceiver {
	private static final ResourceImage IMAGE_ON = new ResourceImage("/lampOn.png");
	private static final ResourceImage IMAGE_OFF = new ResourceImage("/lampOff.png");
	
	@Override
	public CircuitComponent copy() {
		return new Lamp();
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
