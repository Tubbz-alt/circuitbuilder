package fwcd.circuitbuilder.view.logic;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.fructose.swing.View;

public class LogicEditorView implements View {
	private final JPanel component;
	
	public LogicEditorView() {
		component = new JPanel();
		
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
