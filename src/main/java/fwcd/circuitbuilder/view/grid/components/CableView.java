package fwcd.circuitbuilder.view.grid.components;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.view.utils.PositionedRenderable;

public class CableView implements PositionedRenderable {
	private final CableModel model;
	private final int unitSize;
	private final CableDrawOptions options;
	
	public CableView(CableModel model, int unitSize, CableDrawOptions options) {
		this.model = model;
		this.unitSize = unitSize;
		this.options = options;
	}
	
	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		int thickness = options.getThickness();
		int halfThickness = thickness / 2;
		int halfUnitSize = unitSize / 2;
		
		int colorStrength = model.isPowered() ? 255 : 50;
		int x = pos.getX();
		int y = pos.getY();
		int centerX = x + halfUnitSize;
		int centerY = y + halfUnitSize;
		
		g2d.setColor(model.getColor().unwrap().getColor(colorStrength).asAWTColor()); // Signal based color
		
		if (model.getConnections().size() > 2) {
			if (options.drawDots()) {
				int dotRadius = thickness * 2;
				g2d.fillOval(centerX - dotRadius, centerY - dotRadius, dotRadius * 2, dotRadius * 2);
			} else {
				g2d.fillRect(centerX - halfThickness, centerY - halfThickness, centerX + halfThickness, centerY + halfThickness);
			}
		}
		
		g2d.setStroke(new BasicStroke(thickness));
		
		for (Direction connection : model.getConnections()) {
			switch (connection) {
				case LEFT: g2d.drawLine(x, centerY, centerX, centerY); break;
				case RIGHT: g2d.drawLine(centerX, centerY, x + unitSize, centerY); break;
				case DOWN: g2d.drawLine(centerX, centerY, centerX, y + unitSize); break;
				case UP: g2d.drawLine(centerX, y, centerX, centerY); break;
			}
		}
	}
}
