package com.fwcd.circuitbuilder.view.components;

import java.awt.Graphics2D;

import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.model.cable.CableModel;
import com.fwcd.circuitbuilder.utils.AbsolutePos;

public class CircuitItemRenderer implements CircuitItemVisitor {
	private final Graphics2D g2d;
	private final AbsolutePos pos;
	private final int unitSize;
	
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
		new CircuitItemView(item).render(g2d, pos);
	}
}