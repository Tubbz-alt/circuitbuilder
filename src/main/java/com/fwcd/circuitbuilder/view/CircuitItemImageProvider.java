package com.fwcd.circuitbuilder.view;

import java.awt.Image;
import java.util.function.Consumer;

import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.model.cable.CableModel;
import com.fwcd.circuitbuilder.model.components.InputComponentModel;
import com.fwcd.circuitbuilder.model.components.InverterModel;
import com.fwcd.circuitbuilder.model.components.LampModel;
import com.fwcd.circuitbuilder.model.components.LeverModel;
import com.fwcd.circuitbuilder.model.components.OutputComponentModel;
import com.fwcd.circuitbuilder.model.components.TickingClockModel;
import com.fwcd.circuitbuilder.model.components.XorModel;
import com.fwcd.fructose.swing.ResourceImage;

/**
 * Associates an image to be rendered on the grid
 * with a circuit item.
 */
public class CircuitItemImageProvider implements CircuitItemVisitor {
	private static final Image CABLE = new ResourceImage("/cable.png").get();
	private static final Image CLOCK_OFF = new ResourceImage("/clockOff.png").get();
	private static final Image CLOCK_ON = new ResourceImage("/clockOn.png").get();
	private static final Image IN_ICON = new ResourceImage("/inIcon.png").get();
	private static final Image INVERTER_OFF = new ResourceImage("/inverterOff.png").get();
	private static final Image INVERTER_ON = new ResourceImage("/inverterOn.png").get();
	private static final Image LAMP_OFF = new ResourceImage("/lampOff.png").get();
	private static final Image LAMP_ON = new ResourceImage("/lampOn.png").get();
	private static final Image LEVER_OFF = new ResourceImage("/leverOff.png").get();
	private static final Image LEVER_ON = new ResourceImage("/leverOn.png").get();
	private static final Image OUT_ICON = new ResourceImage("/outIcon.png").get();
	private static final Image XOR_IMAGE = new ResourceImage("/xorImage.png").get();
	private final Consumer<Image> consumer;
	
	public CircuitItemImageProvider(Consumer<Image> consumer) {
		this.consumer = consumer;
	}
	
	@Override
	public void visitCable(CableModel cable) {
		consumer.accept(CABLE);
	}
	
	@Override
	public void visitTickingClock(TickingClockModel clock) {
		consumer.accept(clock.isPowered() ? CLOCK_ON : CLOCK_OFF);
	}
	
	@Override
	public void visitInputComponent(InputComponentModel input) {
		consumer.accept(IN_ICON);
	}
	
	@Override
	public void visitOutputComponent(OutputComponentModel output) {
		consumer.accept(OUT_ICON);
	}
	
	@Override
	public void visitLamp(LampModel lamp) {
		consumer.accept(lamp.isPowered() ? LAMP_ON : LAMP_OFF);
	}
	
	@Override
	public void visitInverter(InverterModel inverter) {
		consumer.accept(inverter.isPowered() ? INVERTER_ON : INVERTER_OFF);
	}
	
	@Override
	public void visitLever(LeverModel lever) {
		consumer.accept(lever.isPowered() ? LEVER_ON : LEVER_OFF);
	}
	
	@Override
	public void visitXor(XorModel xor) {
		consumer.accept(XOR_IMAGE);
	}
}
