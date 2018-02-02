package com.fwcd.circuitbuilder.items.nestedcircuits;

import java.awt.Image;

import javax.swing.Icon;

import com.fwcd.circuitbuilder.items.components.CircuitComponent;
import com.fwcd.circuitbuilder.items.components.SimpleEmitter;
import com.fwcd.circuitbuilder.utils.Direction;
import com.fwcd.circuitbuilder.utils.RelativePos;
import com.fwcd.fructose.swing.ResourceImage;

public class NestedCircuitOutput extends SimpleEmitter {
	private static final ResourceImage IMAGE = new ResourceImage("/resources/outIcon.png");
	
	private final RelativePos relativeDeltaPos;
	private boolean enabled = false;
	
	public NestedCircuitOutput(RelativePos relativeDeltaPos) {
		this.relativeDeltaPos = relativeDeltaPos;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public CircuitComponent copy() {
		return new NestedCircuitOutput(relativeDeltaPos);
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
	public boolean isPowered() {
		return enabled;
	}

	@Override
	public boolean outputsTowards(Direction outputDir) {
		return outputDir == Direction.RIGHT;
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
