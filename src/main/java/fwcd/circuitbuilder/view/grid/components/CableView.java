package fwcd.circuitbuilder.view.grid.components;

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
		g2d.fillRect(centerX - halfThickness, centerY - halfThickness, thickness, thickness);
		
		for (Direction connection : model.getConnections()) {
			switch (connection) {
				case LEFT: g2d.fillRect(x, centerY - halfThickness, halfUnitSize, thickness); break;
				case RIGHT: g2d.fillRect(centerX - halfThickness, centerY - halfThickness, halfUnitSize, thickness); break;
				case DOWN: g2d.fillRect(centerX - halfThickness, centerY - halfThickness, thickness, halfUnitSize); break;
				case UP: g2d.fillRect(centerX - halfThickness, y, thickness, halfUnitSize); break;
			}
		}
	}
}
