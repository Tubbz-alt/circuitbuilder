package fwcd.circuitbuilder.view.formula;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.fructose.swing.View;
import fwcd.palm.PalmEditor;

public class FormulaEditorView implements View {
	private final JPanel component;
	
	public FormulaEditorView() {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		PalmEditor editor = new PalmEditor();
		// TODO: Auto-completion is experimental
		// editor.getModel().getCompletionProvider().set(new FormulaCompletionProvider(new MathematicalNotation()));
		// editor.getController().getCompletionController().setHideOnSpace(false);
		editor.getView().setFontSize(18);
		editor.getView().setShowLineHighlight(false);
		component.add(editor.getView().getComponent(), BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(buttonOf("Open in Grid Editor", () -> { /*TODO*/ }));
		buttons.add(buttonOf("Open in Graph Editor", () -> { /*TODO*/ }));
		component.add(buttons, BorderLayout.EAST);
	}
	
	private JButton buttonOf(String name, Runnable action) {
		JButton button = new JButton(name);
		button.addActionListener(e -> action.run());
		return button;
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
