package fwcd.circuitbuilder.view.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.model.utils.SignalFunctionPlotModel;
import fwcd.circuitbuilder.model.utils.SignalFunctionSegment;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.fructose.Closer;
import fwcd.fructose.OptionInt;
import fwcd.fructose.swing.DashedStroke;
import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.View;

public class SignalFunctionPlotView implements View, AutoCloseable {
	private final JPanel component;
	private final Closer closer = new Closer();
	private final SignalFunctionPlotModel model;
	private int height = 100;
	private int padding = 12;
	private int nameYOffset = 10;
	private boolean showGridLines = true;
	
	public SignalFunctionPlotView(SignalFunctionPlotModel model) {
		this.model = model;
		
		component = new RenderPanel(this::render);
		component.setMinimumSize(new Dimension(1, height));
		component.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
		
		model.getPhase().subscribe(it -> repaintLater()).to(closer);
	}
	
	private void repaintLater() {
		SwingUtilities.invokeLater(component::repaint);
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		SignalFunctionSegment segment = model.getSegment();
		boolean[] values = segment.getRawValues();
		int total = segment.getCapacity().orElseGet(segment::getValueCount);
		int dx = ((int) canvasSize.getWidth() - (padding * 2)) / total;
		int xOffset = (int) ((model.getPhase().get() % 1.0) * dx);
		int width = (int) canvasSize.getWidth() - (padding * 2);
		int height = (int) canvasSize.getHeight() - (padding * 2);
		int count = Math.min(total, segment.getValueCount());
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString(segment.getName(), padding, padding);
		
		if (showGridLines) {
			g2d.setStroke(new DashedStroke(1, 2));
			g2d.setColor(Color.GRAY);
			
			for (int x = padding + (dx - xOffset); x < width; x += dx) {
				g2d.drawLine(x, padding + nameYOffset, x, (height - padding) - nameYOffset);
			}
		}
		
		g2d.setStroke(new BasicStroke(2F));
		g2d.setColor(Color.BLUE);
		
		for (int i = 0; i < count; i++) {
			AbsolutePos pos = toAbsolutePos(values, dx, xOffset, height, i);
			AbsolutePos next = toAbsolutePos(values, dx, xOffset, height, i + 1);
			
			g2d.drawLine(pos.getX(), pos.getY(), next.getX(), pos.getY());
			g2d.drawLine(next.getX(), pos.getY(), next.getX(), next.getY());
		}
	}
	
	private AbsolutePos toAbsolutePos(boolean[] values, int dx, int xOffset, int height, int i) {
		return new AbsolutePos(
			padding + Math.max(0, (i * dx) - xOffset),
			(values[Math.min(i, values.length - 1)]
				? padding + nameYOffset
				: (height - padding) - nameYOffset)
		);
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	public void setShowGridLines(boolean showGridLines) {
		this.showGridLines = showGridLines;
		repaintLater();
	}
	
	public void setHeight(int height) {
		this.height = height;
		repaintLater();
	}
	
	public void setPadding(int padding) {
		this.padding = padding;
		repaintLater();
	}
	
	@Override
	public void close() {
		closer.close();
	}
}
