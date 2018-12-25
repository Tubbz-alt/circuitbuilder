package fwcd.circuitbuilder.view.grid.components;

import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.utils.RelativePos;

public class ItemContextMenuProvider implements CircuitItemVisitor<JPopupMenu> {
	private final CircuitGridModel grid;
	private final RelativePos pos;
	
	public ItemContextMenuProvider(CircuitGridModel grid, RelativePos pos) {
		this.grid = grid;
		this.pos = pos;
	}
	
	@Override
	public JPopupMenu visitItem(CircuitItemModel item) {
		return menuOf(
			menuItemOf("Delete", () -> grid.clearCell(pos))
		);
	}
	
	@Override
	public JPopupMenu visitCable(CableModel cable) {
		return extend(visitItem(cable),
			menuItemOf("Rename", this::performCableRename)
		);
	}
	
	private void performCableRename() {
		Set<CableNetwork> networks = grid.getCableNetworks().stream()
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
			JOptionPane.showInputDialog("Enter the new cable network name:", first.getName().orElse(""));
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
