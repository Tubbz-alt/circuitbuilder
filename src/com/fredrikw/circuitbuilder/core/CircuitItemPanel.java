package com.fredrikw.circuitbuilder.core;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import com.fredrikw.circuitbuilder.items.CircuitItem;
import com.fredrikw.circuitbuilder.items.components.Cable;
import com.fredrikw.circuitbuilder.items.components.CableColor;
import com.fredrikw.circuitbuilder.items.components.Inverter;
import com.fredrikw.circuitbuilder.items.components.Lamp;
import com.fredrikw.circuitbuilder.items.components.Lever;
import com.fredrikw.circuitbuilder.items.components.TickingClock;
import com.fredrikw.circuitbuilder.items.nestedcircuits.predefined.XORCircuit;
import com.fredrikw.circuitbuilder.tools.Screwdriver;
import com.fwcd.fructose.swing.DrawGraphicsButton;
import com.fwcd.fructose.swing.Rendereable;
import com.fwcd.fructose.swing.SelectedButtonPanel;
import com.fwcd.fructose.swing.Viewable;

public class CircuitItemPanel implements Viewable {
	private JToolBar view;
	private final CircuitItem[] items = {
			new Screwdriver(),
			new Cable(),
			new Inverter(),
			new Lamp(),
			new Lever(),
			new TickingClock(),
			new XORCircuit()
	};
	
	public CircuitItemPanel(CircuitBuilderApp parent) {
		view = new JToolBar(JToolBar.VERTICAL);
		view.setPreferredSize(new Dimension(50, 10));
		view.setFloatable(false);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener((l) -> parent.clearGrid());
		view.add(clearButton);
		
		SelectedButtonPanel itemsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		
		for (CircuitItem item : items) {
			itemsPanel.add(new JButton(item.getIcon()), () -> parent.selectItem(item));
		}
		
		view.add(itemsPanel.getView());
		view.add(new JSeparator());
		
		SelectedButtonPanel colorsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		
		for (CableColor color : CableColor.values()) {
			Rendereable circle = (g2d, canvasSize) -> {
				g2d.setColor(color.getAWTColor(255));
				
				int w = (int) canvasSize.getWidth();
				int h = (int) canvasSize.getHeight();
				int iconSize = Math.min(w, h);
				
				g2d.fillOval((w / 2) - (iconSize / 2), (h / 2) - (iconSize / 2), iconSize, iconSize);
			};
			
			JButton button = new DrawGraphicsButton(new Dimension(24, 24), circle);
			colorsPanel.add(button, () -> parent.setSelectedColor(color));
		}
		
		view.add(colorsPanel.getView());
	}
	
	@Override
	public JComponent getView() {
		return view;
	}
}
