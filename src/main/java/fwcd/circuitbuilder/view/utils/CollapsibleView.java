package fwcd.circuitbuilder.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.utils.Direction;
import fwcd.fructose.swing.Viewable;

public class CollapsibleView implements Viewable {
	private final JPanel component;
	private final JButton expander;
	private final JComponent wrapped;
	private final ToggledView toggledView;
	
	private final String expandSymbol;
	private final String collapseSymbol;
	private final Direction expandDirection;
	private boolean collapsed = true;
	
	private CollapsibleView(ToggledView toggledView, String expandSymbol, String collapseSymbol, Direction expandDirection) {
		this.toggledView = toggledView;
		this.expandSymbol = expandSymbol;
		this.collapseSymbol = collapseSymbol;
		this.expandDirection = expandDirection;
		
		wrapped = toggledView.getComponent();
		
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		JPanel expanderPanel = new JPanel();
		expanderPanel.setLayout(new GridBagLayout());
		
		expander = new CollapserButton(expandSymbol);
		expander.setBackground(Color.DARK_GRAY);
		expander.setForeground(Color.WHITE);
		expander.setBorder(BorderFactory.createEmptyBorder());
		expander.setContentAreaFilled(false);
		expander.setOpaque(true);
		expander.addActionListener(l -> {
			SwingUtilities.invokeLater(() -> setCollapsed(!collapsed));
		});
		expanderPanel.add(expander);
		component.add(expanderPanel, getExpanderBorder());
		
		setCollapsed(true);
	}
	
	private Object getExpanderBorder() {
		switch (expandDirection) {
			case UP: return BorderLayout.SOUTH;
			case DOWN: return BorderLayout.NORTH;
			case LEFT: return BorderLayout.EAST;
			case RIGHT: return BorderLayout.WEST;
			default: throw new IllegalStateException("Unsupported expandDirection: " + expandDirection);
		}
	}

	private void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
		
		if (collapsed) {
			expander.setText(expandSymbol);
			component.remove(wrapped);
		} else {
			expander.setText(collapseSymbol);
			component.add(wrapped, BorderLayout.CENTER);
		}
		
		toggledView.onUpdateVisibility(!collapsed);
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	public static class Builder {
		private final ToggledView toggledView;
		private String expandSymbol = "+";
		private String collapseSymbol = "-";
		private Direction expandDirection = Direction.LEFT;
		
		public Builder(Viewable view) { this(view.getComponent()); }
		
		public Builder(JComponent component) { this(new SimpleToggledView(component)); }
		
		public Builder(ToggledView toggledView) { this.toggledView = toggledView; }
		
		public Builder expandSymbol(String expandSymbol) {
			this.expandSymbol = expandSymbol;
			return this;
		}
		
		public Builder collapseSymbol(String collapseSymbol) {
			this.collapseSymbol = collapseSymbol;
			return this;
		}
		
		public Builder expandDirection(Direction expandDirection) {
			this.expandDirection = expandDirection;
			return this;
		}
		
		public CollapsibleView build() {
			return new CollapsibleView(toggledView, expandSymbol, collapseSymbol, expandDirection);
		}
	}
}
