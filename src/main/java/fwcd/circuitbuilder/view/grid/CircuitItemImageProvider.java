package fwcd.circuitbuilder.view.grid;

import java.awt.Image;

import fwcd.circuitbuilder.model.CircuitItemModel;
import fwcd.circuitbuilder.model.CircuitItemVisitor;
import fwcd.circuitbuilder.model.cable.CableModel;
import fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.components.InputComponentModel;
import fwcd.circuitbuilder.model.components.InverterModel;
import fwcd.circuitbuilder.model.components.LampModel;
import fwcd.circuitbuilder.model.components.LeverModel;
import fwcd.circuitbuilder.model.components.OutputComponentModel;
import fwcd.circuitbuilder.model.components.TickingClockModel;
import fwcd.circuitbuilder.model.components.XorModel;
import fwcd.fructose.Option;
import fwcd.fructose.swing.ResourceImage;

public class CircuitItemImageProvider implements CircuitItemVisitor<Option<Image>> {
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
	private final boolean alwaysUsePoweredImage;
	
	public CircuitItemImageProvider(boolean alwaysUsePoweredImage) {
		this.alwaysUsePoweredImage = alwaysUsePoweredImage;
	}
	
	public CircuitItemImageProvider() {
		this(false);
	}
	
	@Override
	public Option<Image> visitCable(CableModel cable) {
		return Option.of(CABLE);
	}
	
	@Override
	public Option<Image> visitTickingClock(TickingClockModel clock) {
		return Option.of(clock.isPowered() ? CLOCK_ON : CLOCK_OFF);
	}
	
	@Override
	public Option<Image> visitInputComponent(InputComponentModel input) {
		return Option.of(IN_ICON);
	}
	
	@Override
	public Option<Image> visitOutputComponent(OutputComponentModel output) {
		return Option.of(OUT_ICON);
	}
	
	@Override
	public Option<Image> visitLamp(LampModel lamp) {
		return Option.of(isPowered(lamp) ? LAMP_ON : LAMP_OFF);
	}
	
	@Override
	public Option<Image> visitInverter(InverterModel inverter) {
		return Option.of(isPowered(inverter) ? INVERTER_ON : INVERTER_OFF);
	}
	
	@Override
	public Option<Image> visitLever(LeverModel lever) {
		return Option.of(isPowered(lever) ? LEVER_ON : LEVER_OFF);
	}
	
	@Override
	public Option<Image> visitXor(XorModel xor) {
		return Option.of(XOR_IMAGE);
	}
	
	@Override
	public Option<Image> visitItem(CircuitItemModel item) {
		return Option.empty();
	}
	
	private boolean isPowered(Circuit1x1ComponentModel component) {
		return component.isPowered() || alwaysUsePoweredImage;
	}
}
