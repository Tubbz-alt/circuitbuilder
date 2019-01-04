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
	private static final int MIN_HEIGHT = 50;
	private final JPanel component;
	private final SignalFunctionSegment functionSegment;
	
	public SignalFunctionPlot(SignalFunctionSegment functionSegment) {
		this.functionSegment = functionSegment;
		
		component = new RenderPanel(this::render);
		component.setMinimumSize(new Dimension(1, MIN_HEIGHT));
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		boolean[] values = functionSegment.getValues();
		int dx = ((int) canvasSize.getWidth()) / values.length;
		int height = (int) canvasSize.getHeight();
		AbsolutePos lastPos = null;
		
		g2d.setStroke(new BasicStroke(2F));
		g2d.setColor(Color.BLUE);
		
		for (int i = 0; i < values.length; i++) {
			AbsolutePos pos = new AbsolutePos(i * dx, values[i] ? 0 : height);
			
			if (i > 0) {
				g2d.drawLine(lastPos.getX(), lastPos.getY(), pos.getX(), pos.getY());
			}
			
			lastPos = pos;
		}
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
