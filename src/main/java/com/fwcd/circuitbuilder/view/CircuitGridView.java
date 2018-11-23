package com.fwcd.circuitbuilder.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitGridModel;
import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.utils.RelativePos;
import com.fwcd.circuitbuilder.view.components.CircuitItemRenderer;
import com.fwcd.circuitbuilder.view.tools.CircuitTool;
import com.fwcd.fructose.Option;
import com.fwcd.fructose.swing.MouseHandler;
import com.fwcd.fructose.swing.RenderPanel;
import com.fwcd.fructose.swing.View;

/**
 * A rendered 2D-grid of circuit components.
 */
public class CircuitGridView implements View {
	private final JPanel component;
	private final CircuitGridModel model;
	private final CircuitBuilderContext context;
	
	private final int unitSize = 24;
	private final CircuitGridCoordinateMapper coordMap = new CircuitGridCoordinateMapper(unitSize);
	
	private Option<AbsolutePos> mousePos = Option.empty();
	private int mouseButton = 0;
	
	public CircuitGridView(CircuitGridModel model, CircuitBuilderContext context) {
		this.model = model;
		this.context = context;
		component = new RenderPanel(this::render);
		setupMouseHandler();
		model.getChangeListeners().add(component::repaint);
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
							tool.onRightClick(model, cell);
						}
					});
				});
				
				component.repaint();
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
				mousePos = Option.of(new AbsolutePos(e.getX(), e.getY()));
				component.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mousePos = Option.empty();
				component.repaint();
			}
		}.connect(component);
	}
	
	private void renderItem(CircuitItemModel item, Graphics2D g2d, RelativePos pos) {
		item.accept(new CircuitItemRenderer(g2d, coordMap.toAbsolutePos(pos), unitSize));
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		g2d.setColor(Color.GRAY);
		
		for (int absX = 0; absX < canvasSize.getWidth(); absX += unitSize) {
			for (int absY = 0; absY < canvasSize.getHeight(); absY += unitSize) {
				g2d.drawRect(absX, absY, unitSize, unitSize);
			}
		}
		
		for (RelativePos cellPos : model.getCells().keySet()) {
			for (Circuit1x1ComponentModel circuitComponent : model.getCells().get(cellPos).getComponents()) {
				if (circuitComponent != null && circuitComponent.isAtomic()) {
					renderItem(circuitComponent, g2d, cellPos);
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
