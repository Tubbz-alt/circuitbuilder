package fwcd.circuitbuilder.view.grid.components;

import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.utils.RelativePos;

public class ItemContextMenuProvider implements CircuitItemVisitor<JPopupMenu> {
	private final CircuitGridModel grid;
	private final CircuitEngineModel engine;
	private final RelativePos pos;
	
	public ItemContextMenuProvider(CircuitGridModel grid, CircuitEngineModel engine, RelativePos pos) {
		this.grid = grid;
		this.engine = engine;
		this.pos = pos;
	}
	
	@Override
	public JPopupMenu visitItem(CircuitItemModel item) {
		return menuOf(
			menuItemOf("Delete", () -> grid.clearCell(pos)),
			menuItemOf("Inspect", () -> showItemInspector(item))
		);
	}
	
	@Override
	public JPopupMenu visitCable(CableModel cable) {
		return extend(visitItem(cable),
			menuItemOf("Rename", this::performCableRename)
		);
	}
	
	private void showItemInspector(CircuitItemModel item) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Class<? extends CircuitItemModel> itemClass = item.getClass();
		Field[] fields = itemClass.getDeclaredFields();
		
		for (Field field : fields) {
			field.setAccessible(true);
			
			String text;
			try {
				Object value = field.get(item);
				text = field.getName() + ":  " + value.toString() + " (@" + Integer.toHexString(value.hashCode()) + ")";
			} catch (ReflectiveOperationException e) {
				text = "Error: " + e.getMessage();
			}
			
			panel.add(new JLabel(text));
		}
		
		JOptionPane.showMessageDialog(null, panel, itemClass.getSimpleName(), JOptionPane.PLAIN_MESSAGE);
	}
	
	private void performCableRename() {
		Set<CableNetwork> networks = engine.getCableNetworks().stream()
			.filter(it -> it.getPositions().contains(pos))
			.collect(Collectors.toSet());
		boolean confirmed = true;
		
		if (networks.size() > 1) {
			confirmed = JOptionPane.showConfirmDialog(
				null, "Are you sure you want to modify multiple cable networks at once?"
			) == JOptionPane.OK_OPTION;
		}
		
		if (confirmed && networks.size() > 0) {
			CableNetwork first = networks.iterator().next();
			String newName = JOptionPane.showInputDialog("Enter the new cable network name:", first.getName().orElse(""));
			first.setName(newName);
		}
	}
	
	private JMenuItem menuItemOf(String name, Runnable action) {
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(l -> action.run());
		return item;
	}
	
	private JPopupMenu extend(JPopupMenu menu, JMenuItem... items) {
		menu.addSeparator();
		for (JMenuItem item : items) {
			menu.add(item);
		}
		return menu;
	}
	
	private JPopupMenu menuOf(JMenuItem... items) {
		JPopupMenu menu = new JPopupMenu();
		for (JMenuItem item : items) {
			menu.add(item);
		}
		return menu;
	}
}
