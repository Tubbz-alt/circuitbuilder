package fwcd.circuitbuilder.view.logic.karnaugh;

import java.awt.Dimension;
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
	private final int cellWidth = 20;
	private final int cellHeight = 20;
	
	public KarnaughMapView(KarnaughMapModel model) {
		this.model = model;
		view = new RenderPanel(this::render);
		view.setPreferredSize(new Dimension(cellWidth * model.getColCount(), cellHeight * model.getRowCount()));
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		int rowCount = model.getRowCount();
		int colCount = model.getColCount();
		g2d.setFont(g2d.getFont().deriveFont((float) Math.min(cellWidth, cellHeight)));
		
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				int x = col * cellWidth;
				int y = row * cellHeight;
				g2d.drawString(Integer.toString(BoolUtils.toBit(model.getCell(col, row))), x, y + cellHeight);
			}
		}
	}
	
	@Override
	public JComponent getComponent() { return view; }
}
