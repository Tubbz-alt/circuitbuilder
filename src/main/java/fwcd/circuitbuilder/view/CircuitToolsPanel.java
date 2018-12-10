package fwcd.circuitbuilder.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import fwcd.circuitbuilder.model.CircuitBuilderModel;
import fwcd.circuitbuilder.model.cable.CableColor;
import fwcd.circuitbuilder.model.cable.CableModel;
import fwcd.circuitbuilder.model.components.InverterModel;
import fwcd.circuitbuilder.model.components.LampModel;
import fwcd.circuitbuilder.model.components.LeverModel;
import fwcd.circuitbuilder.model.components.TickingClockModel;
import fwcd.circuitbuilder.model.components.XorModel;
import fwcd.circuitbuilder.view.tools.CircuitTool;
import fwcd.circuitbuilder.view.tools.Place1x1ItemTool;
import fwcd.circuitbuilder.view.tools.PlaceLargeItemTool;
import fwcd.circuitbuilder.view.tools.Screwdriver;
import fwcd.fructose.Option;
import fwcd.fructose.swing.DrawGraphicsButton;
import fwcd.fructose.swing.Renderable;
import fwcd.fructose.swing.SelectedButtonPanel;
import fwcd.fructose.swing.View;

/**
 * A view where the user can select a circuit tool.
 */
public class CircuitToolsPanel implements View {
	private JToolBar view;
	private final CircuitTool[] tools;

	public CircuitToolsPanel(CircuitBuilderModel model, CircuitBuilderContext context) {
		tools = new CircuitTool[] { new Place1x1ItemTool<>(() -> new CableModel(context.getSelectedColor().get())),
				new Place1x1ItemTool<>(InverterModel::new), new Place1x1ItemTool<>(LampModel::new),
			new Place1x1ItemTool<>(LeverModel::new),
			new Place1x1ItemTool<>(TickingClockModel::new),
			new PlaceLargeItemTool<>(XorModel::new),
			new Screwdriver()
		};
		
		view = new JToolBar(JToolBar.VERTICAL);
		view.setPreferredSize(new Dimension(50, 10));
		view.setFloatable(false);
		
		SelectedButtonPanel itemsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		itemsPanel.setFolding(true);
		
		for (CircuitTool tool : tools) {
			JButton button = tool.getImage()
				.map(ImageIcon::new)
				.map(JButton::new)
				.orElseGet(() -> new JButton(tool.getName()));
			itemsPanel.add(button, () -> context.getSelectedTool().set(Option.of(tool)));
		}
		
		view.add(itemsPanel.getComponent());
		
		SelectedButtonPanel colorsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		colorsPanel.setFolding(true);
		
		for (CableColor color : CableColor.values()) {
			Renderable circle = (g2d, canvasSize) -> {
				g2d.setColor(color.getColor(255).asAWTColor());
				
				int w = (int) canvasSize.getWidth();
				int h = (int) canvasSize.getHeight();
				int iconSize = Math.min(w, h);
				
				g2d.fillOval((w / 2) - (iconSize / 2), (h / 2) - (iconSize / 2), iconSize, iconSize);
			};
			
			JButton button = new DrawGraphicsButton(new Dimension(24, 24), circle);
			colorsPanel.add(button, () -> context.getSelectedColor().set(color));
		}
		
		view.add(colorsPanel.getComponent());
	}
	
	@Override
	public JComponent getComponent() {
		return view;
	}
}
