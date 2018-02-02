package com.fredrikw.circuitbuilder.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.eclipse.jdt.annotation.Nullable;

import com.fredrikw.circuitbuilder.items.CircuitItem;
import com.fredrikw.circuitbuilder.items.components.CableColor;

public class CircuitBuilderApp {
	private final JFrame view;
	
	private CircuitItemPanel itemPanel;
	private CircuitGrid grid;
	
	@Nullable
	private CircuitItem selectedItem;
	private CableColor selectedColor;
	
	// TODO: Serialization
	
	public CircuitBuilderApp() {
		view = new JFrame("CircuitBuilder");
		view.setSize(800, 600);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setLayout(new BorderLayout());
		
		itemPanel = new CircuitItemPanel(this);
		view.add(itemPanel.getView(), BorderLayout.WEST);
		
		grid = new CircuitGrid(this);
		view.add(grid.getView(), BorderLayout.CENTER);
		
		view.setVisible(true);
	}

	public void selectItem(CircuitItem item) {
		selectedItem = item;
	}

	public CircuitItem getSelectedItem() {
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
