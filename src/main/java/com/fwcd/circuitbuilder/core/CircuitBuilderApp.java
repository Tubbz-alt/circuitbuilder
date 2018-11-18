package com.fwcd.circuitbuilder.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.fwcd.circuitbuilder.items.CircuitItem;
import com.fwcd.circuitbuilder.model.cable.CableColor;
import com.fwcd.fructose.Option;

public class CircuitBuilderApp {
	private final JFrame view;
	
	private CircuitItemPanel itemPanel;
	private CircuitGrid grid;
	
	private Option<CircuitItem> selectedItem = Option.empty();
	private CableColor selectedColor;
	
	// TODO: Serialization
	
	public CircuitBuilderApp() {
		view = new JFrame("CircuitBuilder");
		view.setSize(800, 600);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setLayout(new BorderLayout());
		
		itemPanel = new CircuitItemPanel(this);
		view.add(itemPanel.getComponent(), BorderLayout.WEST);
		
		grid = new CircuitGrid(this);
		view.add(grid.getComponent(), BorderLayout.CENTER);
		
		view.setVisible(true);
	}

	public void selectItem(CircuitItem item) {
		selectedItem = Option.of(item);
	}

	public Option<CircuitItem> getSelectedItem() {
		return selectedItem;
	}

	public void clearGrid() {
		grid.clear();
	}

	public CableColor getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(CableColor selectedColor) {
		this.selectedColor = selectedColor;
	}
}
