package fwcd.circuitbuilder.view;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import fwcd.circuitbuilder.model.CircuitBuilderModel;
import fwcd.circuitbuilder.utils.ObservableUtils;
import fwcd.circuitbuilder.view.utils.KeyUtils;
import fwcd.fructose.swing.Viewable;

public class CircuitBuilderMenuBar implements Viewable {
	private static final String FILE_EXTENSION = "json";
	private final JMenuBar component;
	private final JFileChooser fileChooser;
	private final CircuitBuilderModel model;
	
	public CircuitBuilderMenuBar(CircuitBuilderView view, CircuitBuilderModel model, CircuitBuilderAppContext context) {
		this.model = model;
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("CircuitBuilder JSON", FILE_EXTENSION));

		component = new JMenuBar();
		component.add(menuOf("File",
			itemOf("New Grid", KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyUtils.CTRL_OR_META_DOWN_MASK), model.getGrid().getInner()::clear),
			itemOf("Open Grid", KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyUtils.CTRL_OR_META_DOWN_MASK), this::showOpenDialog),
			itemOf("Save Grid", KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyUtils.CTRL_OR_META_DOWN_MASK), this::showSaveDialog)
		));
		component.add(menuOf("Debug",
			itemOf("Show/Hide Cable Networks", () -> ObservableUtils.toggle(context.getGridContext().getShowNetworks()))
		));
	}
	
	private void showSaveDialog() {
		if (fileChooser.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				if (!file.getName().endsWith("." + FILE_EXTENSION)) {
					file = new File(file.getAbsolutePath() + "." + FILE_EXTENSION);
				}
				try {
					model.getGrid().saveGridTo(file.toPath());
				} catch (IOException e) {
					showErrorDialog(e);
				}
			}
		}
	}
	
	private void showOpenDialog() {
		if (fileChooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				try {
					model.getGrid().loadGridFrom(file.toPath());
				} catch (IOException e) {
					showErrorDialog(e);
				}
			}
		}
	}
	
	private void showErrorDialog(Exception e) {
		JOptionPane.showMessageDialog(component, e.getMessage(), "A " + e.getClass().getSimpleName() + " has occurred", JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
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
