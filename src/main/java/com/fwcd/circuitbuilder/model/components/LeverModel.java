package com.fwcd.circuitbuilder.model.components;

import com.fwcd.circuitbuilder.model.CircuitItemVisitor;

public class LeverModel extends BasicEmitter {
	@Override
	public void toggle() {
		setPowered(!isPowered());
	}
	
	@Override
	public void accept(CircuitItemVisitor visitor) { visitor.visitLever(this); }
}
