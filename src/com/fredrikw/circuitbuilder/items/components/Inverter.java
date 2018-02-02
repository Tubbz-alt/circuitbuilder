package com.fredrikw.circuitbuilder.items.components;

import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.Icon;

import com.fredrikw.circuitbuilder.core.CircuitCell;
import com.fredrikw.circuitbuilder.utils.AbsolutePos;
import com.fredrikw.circuitbuilder.utils.Direction;
import com.fwcd.fructose.swing.ResourceImage;

public class Inverter extends BasicComponent {
	private static final ResourceImage IMAGE_ENABLED = new ResourceImage("/resources/inverterOn.png");
	private static final ResourceImage IMAGE_DISABLED = new ResourceImage("/resources/inverterOff.png");
	
	private Direction facing = Direction.UP;
	
	private boolean nowEmitting = true;
	private boolean soonEmitting = nowEmitting;
	
	@Override
	public Icon getIcon() {
		return IMAGE_ENABLED.getAsIcon();
	}

	@Override
	public void onPlace(Map<Direction, CircuitCell> neighbors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		ResourceImage currentImage = nowEmitting ? IMAGE_ENABLED : IMAGE_DISABLED;
		g2d.drawImage(currentImage.get(), facing.getTransform(pos), null);
	}

	@Override
	public CircuitComponent copy() {
		return new Inverter();
	}

	@Override
	public void onRightClick() {
		facing = facing.cycle();
	}

	@Override
	public void update() {
		nowEmitting = soonEmitting;
	}
	
	@Override
	public void tick(Map<Direction, CircuitCell> neighbors) {
		soonEmitting = true;
		
		for (Direction dir : neighbors.keySet()) {
			if (dir == facing.invert()) {
				for (CircuitComponent component : neighbors.get(dir)) {
					if (component.isPowered() && component.outputsTowards(facing)) {
						soonEmitting = false;
					}
				}
			}
		}
	}

	@Override
	public boolean isPowered() {
		return nowEmitting;
	}

	@Override
	public boolean outputsTowards(Direction outputDir) {
		return outputDir == facing;
	}
}
