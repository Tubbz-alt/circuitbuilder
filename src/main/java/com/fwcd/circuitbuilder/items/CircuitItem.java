package com.fwcd.circuitbuilder.items;

import java.awt.Graphics2D;

import javax.swing.Icon;

import com.fwcd.circuitbuilder.utils.AbsolutePos;

/**
 * The root interface for all components and
 * tools. Register an item in {@link CircuitItemPickerView}.
 * 
 * @author Fredrik
 *
 */
public interface CircuitItem {
	static final int UNIT_SIZE = 24;
	
	Icon getIcon();
	
	void render(Graphics2D g2d, AbsolutePos pos);
}
