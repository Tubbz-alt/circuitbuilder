package com.fwcd.circuitbuilder.view.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.NoSuchElementException;

import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.model.cable.CableModel;
import com.fwcd.circuitbuilder.model.components.InverterModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.utils.Direction;
import com.fwcd.circuitbuilder.view.CircuitItemImageProvider;
import com.fwcd.fructose.Option;

public class CircuitItemRenderer implements CircuitItemVisitor {
	private final Graphics2D g2d;
	private final AbsolutePos pos;
	private final int unitSize;
	private Option<Image> image = Option.empty();
	
	public CircuitItemRenderer(Graphics2D g2d, AbsolutePos pos, int unitSize) {
		this.g2d = g2d;
		this.pos = pos;
		this.unitSize = unitSize;
	}
	
	@Override
	public void visitCable(CableModel cable) {
		new CableView(cable, unitSize).render(g2d, pos);
	}
	
	@Override
	public void visitInverter(InverterModel inverter) {
		g2d.drawImage(imageOf(inverter), getTransform(pos, inverter.getFacing()), null);
	}
	
	@Override
	public void visitItem(CircuitItemModel item) {
		g2d.drawImage(imageOf(item), pos.getX(), pos.getY(), null);
	}
	
	private AffineTransform getTransform(AbsolutePos pos, Direction direction) {
		AffineTransform transform = new AffineTransform();
		transform.translate(pos.getX(), pos.getY());
		
		int halfSize = unitSize / 2;
		
		switch (direction) {
			case LEFT: transform.rotate(Math.toRadians(-90), halfSize, halfSize); break;
			case UP: break;
			case RIGHT: transform.rotate(Math.toRadians(90), halfSize, halfSize); break;
			case DOWN: transform.rotate(Math.toRadians(180), halfSize, halfSize); break;
			default: throw new RuntimeException("Invalid direction.");
		}
		
		return transform;
	}
	
	private Image imageOf(CircuitItemModel item) {
		item.accept(new CircuitItemImageProvider(it -> image = Option.of(it)));
		if (image.isPresent()) {
			return image.unwrap();
		} else {
			throw new NoSuchElementException("No image registered for " + item.getClass().getSimpleName() + " in CircuitItemImageProvider");
		}
	}
}