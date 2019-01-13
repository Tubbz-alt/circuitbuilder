package fwcd.circuitbuilder.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fwcd.fructose.swing.View;

public class CollapsibleView implements View {
	private static final String EXPAND_SYMBOL = "<";
	private static final String COLLAPSE_SYMBOL = ">";
	
	private final JPanel component;
	private final JButton expander;
	private final JComponent wrapped;
	private final ToggledView toggledView;
	
	private boolean collapsed = true;
	
	public CollapsibleView(ToggledView toggledView) {
		this.toggledView = toggledView;
		
		wrapped = toggledView.getComponent();
		
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		JPanel expanderPanel = new JPanel();
		expanderPanel.setLayout(new GridBagLayout());
		
		expander = new CollapserButton(EXPAND_SYMBOL);
		expander.setBackground(Color.DARK_GRAY);
		expander.setForeground(Color.WHITE);
		expander.setBorder(BorderFactory.createEmptyBorder());
		expander.setContentAreaFilled(false);
		expander.setOpaque(true);
		expander.addActionListener(l -> {
			SwingUtilities.invokeLater(() -> setCollapsed(!collapsed));
		});
		expanderPanel.add(expander);
		component.add(expanderPanel, BorderLayout.WEST);
		
		setCollapsed(true);
	}
	
	private void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
		
		if (collapsed) {
			expander.setText(EXPAND_SYMBOL);
			component.remove(wrapped);
		} else {
			expander.setText(COLLAPSE_SYMBOL);
			component.add(wrapped, BorderLayout.CENTER);
		}
		
		toggledView.onUpdateVisibility(!collapsed);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
