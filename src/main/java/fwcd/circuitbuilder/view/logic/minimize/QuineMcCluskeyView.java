package fwcd.circuitbuilder.view.logic.minimize;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.logic.TruthTable;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.minimize.Implicant;
import fwcd.circuitbuilder.model.logic.minimize.McCluskeyColumn;
import fwcd.fructose.swing.RenderPanel;
import fwcd.fructose.swing.View;

public class QuineMcCluskeyView implements View {
	private final JPanel component;
	private final List<McCluskeyColumn> columns = new ArrayList<>();
	private final int padding = 10;
	
	public QuineMcCluskeyView(LogicExpression expression) {
		component = new RenderPanel(this::render);
		generateColumns(expression);
	}
	
	private void generateColumns(LogicExpression expression) {
		TruthTable table = new TruthTable(expression);
		McCluskeyColumn column = new McCluskeyColumn(table.getInputCount(), table.getBinaryMinterms());
		while (!column.isCompletelySummarized()) {
			columns.add(column);
			column = column.next();
		}
		columns.add(column);
	}
	
	private void render(Graphics2D g2d, Dimension canvasSize) {
		FontMetrics metrics = g2d.getFontMetrics();
		int x = padding;
		int y = padding;
		
		for (McCluskeyColumn column : columns) {
			int maxColumnWidth = 0;
			
			for (Implicant implicant : column.getImplicants()) {
				String str = implicant.toTernaryRepresentation();
				g2d.drawString(str, x, y);
				
				maxColumnWidth = Math.max(maxColumnWidth, metrics.stringWidth(str));
				y += metrics.getHeight();
			}
			
			x += padding + maxColumnWidth;
			y = padding;
		}
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
}
