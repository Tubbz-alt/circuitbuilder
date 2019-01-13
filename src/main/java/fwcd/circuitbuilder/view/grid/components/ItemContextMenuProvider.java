package fwcd.circuitbuilder.view.grid.components;

import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Option;

public class ItemContextMenuProvider implements CircuitItemVisitor<JPopupMenu> {
	private final JComponent baseComponent;
	private final CircuitGridModel grid;
	private final CircuitEngineModel engine;
	private final RelativePos pos;
	
	public ItemContextMenuProvider(JComponent baseComponent, CircuitGridModel grid, CircuitEngineModel engine, RelativePos pos) {
		this.baseComponent = baseComponent;
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
		SwingUtilities.invokeLater(() -> {
			JComponent component = new DebugItemInspector(item, pos, engine).getComponent();
			JOptionPane.showMessageDialog(baseComponent, component, "Inspector", JOptionPane.PLAIN_MESSAGE);
		});
	}
	
	private void performCableRename() {
		SwingUtilities.invokeLater(() -> {
			Set<CableNetwork> networks = engine.getCableNetworks().stream()
				.filter(it -> it.getPositions().contains(pos))
				.collect(Collectors.toSet());
			boolean confirmed = true;
			
			if (networks.size() > 1) {
				confirmed = JOptionPane.showConfirmDialog(
					baseComponent, "Are you sure you want to modify multiple cable networks at once?"
				) == JOptionPane.OK_OPTION;
			}
			
			if (confirmed && networks.size() > 0) {
				CableNetwork first = networks.iterator().next();
				String newName = JOptionPane.showInputDialog(baseComponent, "Enter the new cable network name:", first.getName().get().orElse(""));
				first.getName().set(Option.of(newName));
			}
		});
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
