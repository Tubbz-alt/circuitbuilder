package com.fwcd.circuitbuilder.view.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.NoSuchElementException;

import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.model.cable.CableModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
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
	public void visitItem(CircuitItemModel item) {
		item.accept(new CircuitItemImageProvider(it -> image = Option.of(it)));
		if (image.isPresent()) {
			g2d.drawImage(image.unwrap(), pos.getX(), pos.getY(), null);
		} else {
			throw new NoSuchElementException("No image registered for " + item.getClass().getSimpleName() + " in CircuitItemImageProvider");
		}
	}
}