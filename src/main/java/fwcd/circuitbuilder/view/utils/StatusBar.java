package fwcd.circuitbuilder.view.utils;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fwcd.fructose.swing.View;

/**
 * A status bar component that displays a
 * message with a colored background.
 */
public class StatusBar implements View {
	private final JPanel component;
	private final JLabel label;
	
	public StatusBar() {
		component = new JPanel();
		component.setLayout(new GridBagLayout());
		
		label = new JLabel();
		component.add(label);
	}
	
	public void setBackground(Color color) {
		component.setBackground(color);
	}
	
	public void setTextColor(Color color) {
		label.setForeground(color);
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
