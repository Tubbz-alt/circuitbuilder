package fwcd.circuitbuilder.view.logic.karnaugh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.logic.karnaugh.KarnaughMapModel;
import fwcd.circuitbuilder.model.utils.BoolUtils;
import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.View;

public class KarnaughMapView implements View {
	private final JPanel view;
	private final KarnaughMapModel model;
	private final int cellWidth = 30;
	private final int cellHeight = 30;
	
	public KarnaughMapView(KarnaughMapModel model) {
		this.model = model;
		view = new RenderPanel(this::render);
		view.setPreferredSize(new Dimension(cellWidth * model.getColCount(), cellHeight * model.getRowCount()));
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		FontMetrics metrics = g2d.getFontMetrics();
		int rowCount = model.getRowCount();
		int colCount = model.getColCount();
		
		g2d.setFont(g2d.getFont().deriveFont(16F));
		g2d.setColor(Color.BLACK);
		
		String xVars = model.getXAxisVariables().reduce((a, b) -> a + ", " + b).orElse("");
		String yVars = model.getYAxisVariables().reduce((a, b) -> a + ", " + b).orElse("");
		int xOffset = metrics.stringWidth(yVars) * 2;
		int yOffset = metrics.getHeight() * 2;
		
		g2d.drawLine(0, 0, xOffset, yOffset);
		g2d.drawString(xVars, xOffset / 2, yOffset / 2);
		g2d.drawString(yVars, xOffset / 2 - metrics.stringWidth(yVars), yOffset / 2 + metrics.getHeight());
		
		// Draw row/column labels
		
		g2d.setFont(g2d.getFont().deriveFont(14F));
		
		int[] xCode = model.getXAxisCode().toArray();
		int[] yCode = model.getYAxisCode().toArray();
		int xBits = model.getXAxisBitCount();
		int yBits = model.getYAxisBitCount();
		
		for (int col = 0; col < colCount; col++) {
			String str = BoolUtils.toBinaryString(xCode[col], xBits);
			g2d.drawString(str, xOffset + (cellWidth * col), yOffset);
		}
		
		for (int row = 0; row < rowCount; row++) {
			String str = BoolUtils.toBinaryString(yCode[row], yBits);
			g2d.drawString(str, xOffset - metrics.stringWidth(str), yOffset + (cellHeight * row) + (cellHeight / 2));
		}
		
		// Draw cells
		
		g2d.setFont(g2d.getFont().deriveFont((float) Math.min(cellWidth, cellHeight) * 0.8F));
		
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				String str = Integer.toString(BoolUtils.toBit(model.getCell(col, row)));
				int x = xOffset + (col * cellWidth);
				int y = yOffset + (row * cellHeight);
				
				g2d.drawRect(x, y, cellWidth, cellHeight);
				g2d.drawString(str, x + ((cellWidth - metrics.stringWidth(str)) / 2), y + cellHeight - (metrics.getAscent() / 2));
			}
		}
	}
	
	@Override
	public JComponent getComponent() { return view; }
}
