package fwcd.circuitbuilder.view.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.model.utils.SignalFunctionSegment;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.fructose.OptionInt;
import fwcd.fructose.swing.DashedStroke;
import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.View;

public class SignalFunctionPlot implements View {
	private final JPanel component;
	private final SignalFunctionSegment functionSegment;
	private final String name;
	private int height = 100;
	private int padding = 12;
	private int nameYOffset = 10;
	private OptionInt valueCount = OptionInt.empty();
	private boolean showGridLines = true;
	
	public SignalFunctionPlot(String name, SignalFunctionSegment functionSegment) {
		this.name = name;
		this.functionSegment = functionSegment;
		
		component = new RenderPanel(this::render);
		component.setMinimumSize(new Dimension(1, height));
		component.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
	}
	
	private void repaint() {
		SwingUtilities.invokeLater(component::repaint);
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		boolean[] values = functionSegment.getValues();
		int total = valueCount.orElse(values.length);
		int dx = ((int) canvasSize.getWidth() - (padding * 2)) / total;
		int height = (int) canvasSize.getHeight() - (padding * 2);
		int count = Math.min(total, values.length);
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString(name, padding, padding);
		
		if (showGridLines) {
			g2d.setStroke(new DashedStroke(1, 2));
			g2d.setColor(Color.GRAY);
			
			for (int i = 0; i < total; i++) {
				int x = padding + (i * dx);
				g2d.drawLine(x, padding + nameYOffset, x, (height - padding) - nameYOffset);
			}
		}
		
		g2d.setStroke(new BasicStroke(2F));
		g2d.setColor(Color.BLUE);
		
		for (int i = 0; i < count; i++) {
			AbsolutePos pos = toAbsolutePos(values, dx, height, i);
			AbsolutePos next = toAbsolutePos(values, dx, height, i + 1);
			
			g2d.drawLine(pos.getX(), pos.getY(), next.getX(), pos.getY());
			g2d.drawLine(next.getX(), pos.getY(), next.getX(), next.getY());
		}
	}
	
	private AbsolutePos toAbsolutePos(boolean[] values, int dx, int height, int i) {
		return new AbsolutePos(padding + (i * dx), (values[Math.min(i, values.length - 1)] ? padding + nameYOffset : (height - padding) - nameYOffset));
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	public void setValueCount(int valueCount) { setValueCount(OptionInt.of(valueCount)); }
	
	public void setValueCount(OptionInt valueCount) {
		this.valueCount = valueCount;
		repaint();
	}
	
	public void setShowGridLines(boolean showGridLines) {
		this.showGridLines = showGridLines;
		repaint();
	}
	
	public void setHeight(int height) {
		this.height = height;
		repaint();
	}
	
	public void setPadding(int padding) {
		this.padding = padding;
		repaint();
	}
}
