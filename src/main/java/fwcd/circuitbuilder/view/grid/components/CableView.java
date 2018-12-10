package fwcd.circuitbuilder.view.grid.components;

import java.awt.Graphics2D;

import fwcd.circuitbuilder.model.cable.CableModel;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.view.utils.PositionedRenderable;

public class CableView implements PositionedRenderable {
	private final CableModel model;
	private final int unitSize;
	
	public CableView(CableModel model, int unitSize) {
		this.model = model;
		this.unitSize = unitSize;
	}
	
	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		final int subSize = unitSize / 3;

		int colorStrength = model.isPowered() ? 255 : 50;
		
		g2d.setColor(model.getColor().unwrap().getColor(colorStrength).asAWTColor()); // Signal based color
		g2d.fillRect(pos.getX() + subSize, pos.getY() + subSize, subSize, subSize);
		
		for (Direction connection : model.getConnections()) {
			switch (connection) {
				case LEFT: g2d.fillRect(pos.getX(), pos.getY() + subSize, subSize, subSize); break;
				case RIGHT: g2d.fillRect(pos.getX() + (subSize * 2), pos.getY() + subSize, subSize, subSize); break;
				case DOWN: g2d.fillRect(pos.getX() + subSize, pos.getY() + (subSize * 2), subSize, subSize); break;
				case UP: g2d.fillRect(pos.getX() + subSize, pos.getY(), subSize, subSize); break;
			}
		}
	}
}
