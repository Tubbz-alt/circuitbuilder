package fwcd.circuitbuilder.view.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.utils.SignalFunctionSegment;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.View;

public class SignalFunctionPlot implements View {
	private final JPanel component;
	private final SignalFunctionSegment functionSegment;
	private final int height = 80;
	private final int padding = 10;
	
	public SignalFunctionPlot(SignalFunctionSegment functionSegment) {
		this.functionSegment = functionSegment;
		
		component = new RenderPanel(this::render);
		component.setMinimumSize(new Dimension(1, height));
		component.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		boolean[] values = functionSegment.getValues();
		int dx = ((int) canvasSize.getWidth() - (padding * 2)) / values.length;
		int height = (int) canvasSize.getHeight() - (padding * 2);
		
		g2d.setStroke(new BasicStroke(2F));
		g2d.setColor(Color.BLUE);
		
		for (int i = 0; i < values.length; i++) {
			AbsolutePos pos = toAbsolutePos(values, dx, height, i);
			AbsolutePos next = toAbsolutePos(values, dx, height, i + 1);
			
			g2d.drawLine(pos.getX(), pos.getY(), next.getX(), pos.getY());
			g2d.drawLine(next.getX(), pos.getY(), next.getX(), next.getY());
		}
	}
	
	private AbsolutePos toAbsolutePos(boolean[] values, int dx, int height, int i) {
		return new AbsolutePos(padding + (i * dx), values[Math.min(i, values.length - 1)] ? padding : (height - padding));
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
