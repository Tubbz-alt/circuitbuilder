package com.fwcd.circuitbuilder.items.nestedcircuits;

import java.awt.Image;

import javax.swing.Icon;

import com.fwcd.circuitbuilder.items.components.CircuitComponent;
import com.fwcd.circuitbuilder.items.components.SimpleReceiver;
import com.fwcd.circuitbuilder.utils.RelativePos;
import com.fwcd.fructose.swing.ResourceImage;

public class NestedCircuitInput extends SimpleReceiver {
	private static final ResourceImage IMAGE = new ResourceImage("/resources/inIcon.png");
	
	private final RelativePos relativeDeltaPos;

	public NestedCircuitInput(RelativePos relativeDeltaPos) {
		this.relativeDeltaPos = relativeDeltaPos;
	}
	
	@Override
	public CircuitComponent copy() {
		return new NestedCircuitInput(relativeDeltaPos);
	}

	@Override
	public Icon getIcon() {
		return IMAGE.getAsIcon();
	}

	@Override
	protected Image getEnabledImage() {
		return IMAGE.get();
	}

	@Override
	protected Image getDisabledImage() {
		return IMAGE.get();
	}
	
	@Override
	public boolean isRenderedDirectly() {
		return false;
	}

	public RelativePos getRelativeDeltaPos() {
		return relativeDeltaPos;
	}

	@Override
	public boolean canBeRemoved() {
		return false;
	}
}
