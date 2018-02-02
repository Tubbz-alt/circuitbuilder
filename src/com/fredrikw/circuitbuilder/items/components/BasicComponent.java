package com.fredrikw.circuitbuilder.items.components;

public abstract class BasicComponent implements CircuitComponent {
	@Override
	public void onRightClick() {
		
	}
	
	@Override
	public boolean isRenderedDirectly() {
		return true;
	}
	
	@Override
	public boolean canBeRemoved() {
		return true;
	}
}
