package com.fwcd.circuitbuilder.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.model.cable.CableColor;
import com.fwcd.circuitbuilder.view.tools.CircuitTool;
import com.fwcd.fructose.swing.DrawGraphicsButton;
import com.fwcd.fructose.swing.Renderable;
import com.fwcd.fructose.swing.SelectedButtonPanel;
import com.fwcd.fructose.swing.View;

public class CircuitToolsPanel implements View {
	private JToolBar view;
	private static final CircuitTool[] TOOLS = {
		new Place1x1ItemTool<>(CableModel::new),
		new Place1x1ItemTool<>(InverterModel::new),
		new Place1x1ItemTool<>(LampModel::new),
		new Place1x1ItemTool<>(LeverModel::new),
		new Place1x1ItemTool<>(TickingClockModel::new),
		new PlaceLargeItemTool<>(XorModel::new)
			new Screwdriver(),
	};
	
	public CircuitToolsPanel() {
		view = new JToolBar(JToolBar.VERTICAL);
		view.setPreferredSize(new Dimension(50, 10));
		view.setFloatable(false);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener((l) -> parent.clearGrid());
		view.add(clearButton);
		
		SelectedButtonPanel itemsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		
		for (CircuitItemModel item : TOOLS) {
			itemsPanel.add(new JButton(item.getIcon()), () -> parent.selectItem(item));
		}
		
		view.add(itemsPanel.getComponent());
		view.add(new JSeparator());
		
		SelectedButtonPanel colorsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		
		for (CableColor color : CableColor.values()) {
			Renderable circle = (g2d, canvasSize) -> {
				g2d.setColor(color.getColor(255).asAWTColor());
				
				int w = (int) canvasSize.getWidth();
				int h = (int) canvasSize.getHeight();
				int iconSize = Math.min(w, h);
				
				g2d.fillOval((w / 2) - (iconSize / 2), (h / 2) - (iconSize / 2), iconSize, iconSize);
			};
			
			JButton button = new DrawGraphicsButton(new Dimension(24, 24), circle);
			colorsPanel.add(button, () -> parent.setSelectedColor(color));
		}
		
		view.add(colorsPanel.getComponent());
	}
	
	@Override
	public JComponent getComponent() {
		return view;
	}
}
