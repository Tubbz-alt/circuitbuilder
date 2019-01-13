package fwcd.circuitbuilder.view.grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.utils.Direction;
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
	private final CircuitEngineModel engine;
	private final CircuitGridContext context;
	
	private final int unitSize = 24;
	private final CircuitGridCoordinateMapper coordMap = new CircuitGridCoordinateMapper(unitSize);
	
	private Option<AbsolutePos> mousePos = Option.empty();
	private int mouseButton = 0;
	
	public CircuitGridView(CircuitGridModel model, CircuitEngineModel engine, CircuitGridContext context) {
		this.model = model;
		this.engine = engine;
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
						boolean handled = false;
						
						if (mouseButton == MouseEvent.BUTTON1) {
							// Delegate left click to tool
							handled = tool.onLeftClick(model, cell);
						} else if (mouseButton == MouseEvent.BUTTON3) {
							// Delegate right click to tool
							handled = tool.onRightClick(model, cell);
							
							if (!handled) {
								// Display content menu if the tool did not handle the request
								CircuitItemVisitor<JPopupMenu> ctxMenuProvider = new ItemContextMenuProvider(component, model, engine, pos);
								
								cell.getComponent()
									.ifPresent(it -> it.accept(ctxMenuProvider).show(component, e.getX(), e.getY()));
							}
						}
						
						if (handled) {
							// Notify neighbor cells about change
							notifyNeighborsAround(pos);
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

	private void notifyNeighborsAround(RelativePos pos) {
		Map<Direction, CircuitCellModel> neighbors = model.getNeighbors(pos);
		for (CircuitCellModel neighborCell : neighbors.values()) {
			for (Circuit1x1ComponentModel neighborComponent : neighborCell.getComponents()) {
				neighborComponent.onPlace(model.getNeighbors(neighborCell.getPos()));
			}
		}
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
		// Draw background
		
		Color gridLineColor = context.getSelectedTheme().get().getGridLineColor();
		g2d.setColor(gridLineColor);
		
		if (gridLineColor.getAlpha() > 0) {
			for (int absX = 0; absX < canvasSize.getWidth(); absX += unitSize) {
				for (int absY = 0; absY < canvasSize.getHeight(); absY += unitSize) {
					g2d.drawRect(absX, absY, unitSize, unitSize);
				}
			}
		}
		
		// Draw 1x1 components
		
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
		
		// Draw large components
		
		model.getLargeComponents().forEachMainKey((pos, largeComponent) -> {
			renderItem(largeComponent, g2d, pos);
		});
		
		// Draw cable networks
		
		boolean showNetworks = context.getShowNetworks().get();
		g2d.setFont(g2d.getFont().deriveFont(14F));
		
		for (CableNetwork network : engine.getCableNetworks()) {
			String name = network.getName().get().orElse("");
			
			g2d.setColor(Color.BLACK);
			network.streamPositions()
				.filter(pos -> model.isCellEmpty(new RelativePos(pos.getX(), pos.getY() - 1)))
				.sorted(Comparator.comparing(pos -> pos.getX() + pos.getY()))
				.findAny()
				.map(coordMap::toAbsolutePos)
				.ifPresent(pos -> g2d.drawString(name, pos.getX(), pos.getY()));
			
			if (showNetworks) {
				int hash = network.hashCode();
				g2d.setColor(new Color(Math.abs(hash * 74878) % 255, Math.abs(hash * 91828) % 255, Math.abs(hash * 412341) % 255, 128));
				network.streamPositions()
					.map(coordMap::toAbsolutePos)
					.forEach(pos -> g2d.fillRect(pos.getX(), pos.getY(), unitSize, unitSize));
			}
		}
		
		// Draw tool cursor
		
		Option<CircuitTool> selectedTool = context.getSelectedTool().get();
		Option<AbsolutePos> itemPos = mousePos.map(it -> new AbsolutePos(it.sub(unitSize / 2, unitSize / 2)));
		
		itemPos.ifPresent(pos -> selectedTool.ifPresent(tool -> tool.render(g2d, pos)));
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
