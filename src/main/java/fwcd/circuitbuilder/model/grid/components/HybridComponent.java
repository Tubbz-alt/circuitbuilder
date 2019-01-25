package fwcd.circuitbuilder.model.grid.components;

import java.util.ArrayList;
import java.util.List;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;

public class HybridComponent implements Circuit1x1ComponentModel {
	private final List<Circuit1x1ComponentModel> delegates = new ArrayList<>();
	
	public HybridComponent(Circuit1x1ComponentModel... initialDelegates) {
		for (Circuit1x1ComponentModel delegate : initialDelegates) {
			delegates.add(delegate);
		}
	}
	
	public void add(Circuit1x1ComponentModel delegate) {
		delegates.add(delegate);
	}
	
	public void remove(Circuit1x1ComponentModel delegate) {
		delegates.remove(delegate);
	}
	
	@Override
	public boolean outputsTowards(Direction outputDir) {
		return delegates.stream().anyMatch(it -> it.outputsTowards(outputDir));
	}
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) {
		return visitor.visitHybrid(this);
	}
	
	public List<? extends Circuit1x1ComponentModel> getDelegates() {
		return delegates;
	}
	
	@Override
	public String getName() {
		return "Hybrid";
	}
	
	@Override
	public boolean isPowered() {
		return delegates.stream().anyMatch(it -> it.isPowered());
	}
	
	@Override
	public boolean canBeRemoved() {
		return delegates.stream().allMatch(it -> it.canBeRemoved());
	}
	
	@Override
	public boolean canBeStackedOnTopOf(Circuit1x1ComponentModel other) {
		return delegates.stream().allMatch(it -> it.canBeStackedOnTopOf(other));
	}
}
