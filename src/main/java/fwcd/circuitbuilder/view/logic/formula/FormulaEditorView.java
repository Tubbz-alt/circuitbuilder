package fwcd.circuitbuilder.view.logic.formula;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.logic.LogicEditorModel;
import fwcd.fructose.swing.View;
import fwcd.palm.PalmEditor;

public class FormulaEditorView implements View {
	private final JPanel component;
	
	public FormulaEditorView(LogicEditorModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		PalmEditor editor = new PalmEditor();
		// TODO: Auto-completion is experimental
		// editor.getModel().getCompletionProvider().set(new FormulaCompletionProvider(new MathematicalNotation()));
		// editor.getController().getCompletionController().setHideOnSpace(false);
		editor.getView().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		editor.getView().setShowLineHighlight(false);
		component.add(editor.getView().getComponent(), BorderLayout.CENTER);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
