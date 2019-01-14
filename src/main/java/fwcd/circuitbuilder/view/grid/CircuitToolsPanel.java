package fwcd.circuitbuilder.view.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableColor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.AndGateModel;
import fwcd.circuitbuilder.model.grid.components.EqvGateModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.model.grid.components.LampModel;
import fwcd.circuitbuilder.model.grid.components.LeverModel;
import fwcd.circuitbuilder.model.grid.components.NandGateModel;
import fwcd.circuitbuilder.model.grid.components.NorGateModel;
import fwcd.circuitbuilder.model.grid.components.OrGateModel;
import fwcd.circuitbuilder.model.grid.components.RsFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.ClockModel;
import fwcd.circuitbuilder.model.grid.components.XorGateModel;
import fwcd.circuitbuilder.view.grid.tools.CircuitTool;
import fwcd.circuitbuilder.view.grid.tools.Place1x1ItemTool;
import fwcd.circuitbuilder.view.grid.tools.PlaceLargeItemTool;
import fwcd.circuitbuilder.view.grid.tools.Screwdriver;
import fwcd.fructose.Option;
import fwcd.fructose.swing.DrawGraphicsButton;
import fwcd.fructose.swing.Renderable;
import fwcd.fructose.swing.SelectedButtonPanel;
import fwcd.fructose.swing.View;

/**
 * A view where the user can select a circuit tool.
 */
public class CircuitToolsPanel implements View {
	private static final Dimension BUTTON_SIZE = new Dimension(24, 24);
	private final JToolBar view;
	private final CircuitTool[] tools;

	public CircuitToolsPanel(CircuitGridModel model, CircuitGridContext context) {
		CircuitItemVisitor<Option<Image>> imageProvider = context.getSelectedTheme().get().getItemImageProvider();
		tools = new CircuitTool[] {
			new Place1x1ItemTool<>(() -> new CableModel(context.getSelectedColor().get()), imageProvider),
			new Place1x1ItemTool<>(InverterModel::new, imageProvider),
			new Place1x1ItemTool<>(LampModel::new, imageProvider),
			new Place1x1ItemTool<>(LeverModel::new, imageProvider),
			new Place1x1ItemTool<>(ClockModel::new, imageProvider),
			new PlaceLargeItemTool<>(OrGateModel::new, imageProvider),
			new PlaceLargeItemTool<>(AndGateModel::new, imageProvider),
			new PlaceLargeItemTool<>(XorGateModel::new, imageProvider),
			new PlaceLargeItemTool<>(EqvGateModel::new, imageProvider),
			new PlaceLargeItemTool<>(NandGateModel::new, imageProvider),
			new PlaceLargeItemTool<>(NorGateModel::new, imageProvider),
			new PlaceLargeItemTool<>(RsFlipFlopModel::new, imageProvider),
			new Screwdriver()
		};
		
		view = new JToolBar(JToolBar.VERTICAL);
		view.setPreferredSize(new Dimension(50, 10));
		view.setFloatable(false);
		
		SelectedButtonPanel itemsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		
		for (CircuitTool tool : tools) {
			JButton button = tool.getImage()
				.map(this::scaleImageToButtonSize)
				.map(ImageIcon::new)
				.map(JButton::new)
				.filter(it -> tool.useImageButton())
				.orElseGet(() -> new JButton(tool.getSymbol()));
			itemsPanel.add(button, () -> context.getSelectedTool().set(Option.of(tool)));
		}
		
		view.add(itemsPanel.getComponent());
		
		SelectedButtonPanel colorsPanel = new SelectedButtonPanel(false, Color.LIGHT_GRAY);
		
		for (CableColor color : CableColor.values()) {
			Renderable circle = (g2d, canvasSize) -> {
				g2d.setColor(color.getColor(255).asAWTColor());
				
				int w = (int) canvasSize.getWidth();
				int h = (int) canvasSize.getHeight();
				int iconSize = Math.min(w, h);
				
				g2d.fillOval((w / 2) - (iconSize / 2), (h / 2) - (iconSize / 2), iconSize, iconSize);
			};
			
			JButton button = new DrawGraphicsButton(BUTTON_SIZE, circle);
			colorsPanel.add(button, () -> context.getSelectedColor().set(color));
		}
		
		view.add(colorsPanel.getComponent());
	}
	
	private Image scaleImageToButtonSize(Image image) {
		int oldWidth = image.getWidth(null);
		int oldHeight = image.getHeight(null);
		int newWidth = (int) BUTTON_SIZE.getWidth();
		int newHeight = (oldHeight * newWidth) / oldWidth;
		
		if (oldWidth == newWidth) {
			return image;
		} else {
			BufferedImage result = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = result.createGraphics();
			
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
			g2d.dispose();
			
			return result;
		}
	}
	
	@Override
	public JComponent getComponent() {
		return view;
	}
}
