package com.fwcd.circuitbuilder.tools;

import java.awt.Graphics2D;

import javax.swing.Icon;

import com.fwcd.circuitbuilder.core.CircuitCell;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.fructose.swing.ResourceImage;

public class Screwdriver implements CircuitTool {
	private static final ResourceImage IMAGE = new ResourceImage("/resources/screwdriver.png");
	
	@Override
	public Icon getIcon() {
		return IMAGE.getAsIcon();
	}

	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		g2d.drawImage(IMAGE.get(), pos.getX() + (UNIT_SIZE / 2), pos.getY() - (UNIT_SIZE / 2), null);
	}

	@Override
	public void onLeftClick(CircuitCell cell) {
		cell.clear();
	}
}
