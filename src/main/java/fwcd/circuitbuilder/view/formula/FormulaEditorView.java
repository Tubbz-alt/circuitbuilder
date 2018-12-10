package fwcd.circuitbuilder.view.formula;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import fwcd.fructose.swing.View;

public class FormulaEditorView implements View {
	private final JTextArea component;
	
	public FormulaEditorView() {
		component = new JTextArea();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
