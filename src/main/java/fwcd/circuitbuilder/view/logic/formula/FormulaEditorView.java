package fwcd.circuitbuilder.view.logic.formula;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.logic.LogicEditorModel;
import fwcd.circuitbuilder.view.utils.DocumentChangeListener;
import fwcd.fructose.swing.StatusBar;
import fwcd.fructose.swing.View;
import fwcd.palm.PalmEditor;
import fwcd.palm.model.editor.PalmDocument;

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
		
		PalmDocument document = editor.getView().getModel().getDocument();
		document.addDocumentListener((DocumentChangeListener) e -> model.getRawFormula().set(document.getText()));
		
		component.add(editor.getView().getComponent(), BorderLayout.CENTER);
		
		StatusBar statusBar = new StatusBar();
		model.getErrorMessage().listen(msg -> msg.ifPresentOrElse(it -> statusBar.display(it, Color.ORANGE), statusBar::reset));
		component.add(statusBar.getComponent(), BorderLayout.SOUTH);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
