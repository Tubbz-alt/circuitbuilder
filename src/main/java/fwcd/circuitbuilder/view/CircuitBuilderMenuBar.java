package fwcd.circuitbuilder.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import fwcd.circuitbuilder.model.CircuitBuilderModel;
import fwcd.fructose.swing.View;

public class CircuitBuilderMenuBar implements View {
	private final JMenuBar component;
	
	public CircuitBuilderMenuBar(CircuitBuilderView view, CircuitBuilderModel model) {
		component = new JMenuBar();
		component.add(menuOf("File",
			itemOf("New", () -> model.getGrid().clear())
		));
	}
	
	private JMenu menuOf(String name, JMenuItem... items) {
		JMenu menu = new JMenu(name);
		for (JMenuItem item : items) {
			menu.add(item);
		}
		return menu;
	}
	
	private JMenuItem itemOf(String text, Runnable action) {
		JMenuItem item = new JMenuItem(text);
		item.addActionListener(l -> action.run());
		return item;
	}
	
	private JMenuItem itemOf(String text, KeyStroke accelerator, Runnable action) {
		JMenuItem item = itemOf(text, action);
		item.setAccelerator(accelerator);
		return item;
	}
	
	@Override
	public JMenuBar getComponent() { return component; }
}
