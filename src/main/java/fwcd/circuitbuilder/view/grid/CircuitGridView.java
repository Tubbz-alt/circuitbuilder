package fwcd.circuitbuilder.view.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.circuitbuilder.view.grid.components.CircuitItemRenderer;
import fwcd.circuitbuilder.view.grid.components.ItemContextMenuProvider;
import fwcd.circuitbuilder.view.grid.tools.CircuitTool;
import fwcd.fructose.Option;
import fwcd.fructose.swing.MouseHandler;
import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.View;

/**
 * A rendered 2D-grid of circuit components.
 */
public class CircuitGridView implements View {
	private final JPanel component;
	private final CircuitGridModel model;
	private final CircuitGridContext context;
	
	private final int unitSize = 24;
	private final CircuitGridCoordinateMapper coordMap = new CircuitGridCoordinateMapper(unitSize);
	
	private Option<AbsolutePos> mousePos = Option.empty();
	private int mouseButton = 0;
	
	public CircuitGridView(CircuitGridModel model, CircuitGridContext context) {
		this.model = model;
		this.context = context;
		component = new RenderPanel(this::render);
		setupMouseHandler();
		model.getChangeListeners().add(this::repaint);
	}
	
	private void setupMouseHandler() {
		new MouseHandler() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() != MouseEvent.NOBUTTON) {
					mouseButton = e.getButton();
				}
				
				Option<RelativePos> gridPos = mousePos.map(coordMap::toRelativePos);
				Option<CircuitTool> selectedTool = context.getSelectedTool().get();
				
				gridPos.ifPresent(pos -> {
					selectedTool.ifPresent(tool -> {
						CircuitCellModel cell = model.getCell(pos);
						if (mouseButton == MouseEvent.BUTTON1) {
							tool.onLeftClick(model, cell);
						} else if (mouseButton == MouseEvent.BUTTON3) {
							boolean handled = tool.onRightClick(model, cell);
							if (!handled) {
								cell.getComponent()
									.ifPresent(it -> it.accept(new ItemContextMenuProvider(model, pos)).show(component, e.getX(), e.getY()));
							}
						}
					});
				});
				
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
				mousePressed(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseButton = 0;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				int oldX = mousePos.mapToInt(AbsolutePos::getX).orElse(0);
				int oldY = mousePos.mapToInt(AbsolutePos::getY).orElse(0);
				int newX = e.getX();
				int newY = e.getY();
				Option<CircuitTool> tool = context.getSelectedTool().get();
				int width = tool.flatMapToInt(CircuitTool::getWidth).orElse(0);
				int height = tool.flatMapToInt(CircuitTool::getHeight).orElse(0);
				int topLeftX = Math.min(oldX, newX) - width;
				int topLeftY = Math.min(oldY, newY) - height;
				int bottomRightX = Math.max(oldX, newX) + width;
				int bottomRightY = Math.max(oldY, newY) + height;
				
				mousePos = Option.of(new AbsolutePos(newX, newY));
				repaint(
					topLeftX,
					topLeftY,
					bottomRightX - topLeftX,
					bottomRightY - topLeftY
				);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mousePos = Option.empty();
				repaint();
			}
		}.connect(component);
	}
	
	private void repaint() {
		SwingUtilities.invokeLater(component::repaint);
	}
	
	private void repaint(int x, int y, int width, int height) {
		SwingUtilities.invokeLater(() -> component.repaint(x, y, width, height));
	}
	
	private void renderItem(CircuitItemModel item, Graphics2D g2d, RelativePos pos) {
		item.accept(new CircuitItemRenderer(g2d, coordMap.toAbsolutePos(pos), unitSize, context.getSelectedTheme().get()));
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		Color gridLineColor = context.getSelectedTheme().get().getGridLineColor();
		g2d.setColor(gridLineColor);
		
		if (gridLineColor.getAlpha() > 0) {
			for (int absX = 0; absX < canvasSize.getWidth(); absX += unitSize) {
				for (int absY = 0; absY < canvasSize.getHeight(); absY += unitSize) {
					g2d.drawRect(absX, absY, unitSize, unitSize);
				}
			}
		}
		
		for (RelativePos cellPos : model.getCells().keySet()) {
			CircuitCellModel cell = model.getCells().get(cellPos);
			if (cell != null) {
				for (Circuit1x1ComponentModel circuitComponent : cell.getComponents()) {
					if (circuitComponent != null && circuitComponent.isAtomic()) {
						renderItem(circuitComponent, g2d, cellPos);
					}
				}
			}
		}
		
		model.getLargeComponents().forEachMainKey((pos, largeComponent) -> {
			renderItem(largeComponent, g2d, pos);
		});
		
		Option<CircuitTool> selectedTool = context.getSelectedTool().get();
		Option<AbsolutePos> itemPos = mousePos.map(it -> new AbsolutePos(it.sub(unitSize / 2, unitSize / 2)));
		
		itemPos.ifPresent(pos -> selectedTool.ifPresent(tool -> tool.render(g2d, pos)));
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
