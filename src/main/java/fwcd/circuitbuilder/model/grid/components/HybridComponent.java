package fwcd.circuitbuilder.model.grid.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.utils.Direction;

/**
 * A compound component that is only powered
 * when there are any powered emitters inside.
 */
public class HybridComponent implements Circuit1x1ComponentModel {
	private final List<Circuit1x1ComponentModel> delegates = new ArrayList<>();
	
	public HybridComponent(Iterable<? extends Circuit1x1ComponentModel> initialDelegates) {
		for (Circuit1x1ComponentModel delegate : initialDelegates) {
			delegates.add(delegate);
		}
	}
	
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
	public boolean isAtomic() {
		return delegates.stream().allMatch(Circuit1x1ComponentModel::isAtomic);
	}
	
	@Override
	public void tick(Map<Direction, CircuitCellModel> neighbors) {
		for (Circuit1x1ComponentModel delegate : delegates) {
			delegate.tick(neighbors);
		}
	}
	
	@Override
	public String getSymbol() {
		return "Hyb";
	}
	
	@Override
	public void update() {
		for (Circuit1x1ComponentModel delegate : delegates) {
			delegate.update();
		}
	}
	
	@Override
	public boolean isEmitter() {
		return delegates.stream().anyMatch(Circuit1x1ComponentModel::isEmitter);
	}
	
	@Override
	public void onPlace(Map<Direction, CircuitCellModel> neighbors) {
		for (Circuit1x1ComponentModel delegate : delegates) {
			delegate.onPlace(neighbors);
		}
	}
	
	@Override
	public boolean toggle() {
		boolean result = false;
		for (Circuit1x1ComponentModel delegate : delegates) {
			result |= delegate.toggle();
		}
		return result;
	}
	
	@Override
	public boolean canReplaceOtherComponent() {
		return delegates.stream().allMatch(Circuit1x1ComponentModel::canReplaceOtherComponent);
	}
	
	@Override
	public boolean canConnectFrom(Direction direction) {
		return delegates.stream().anyMatch(it -> it.canConnectFrom(direction));
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
		return delegates.stream().anyMatch(it -> it.isEmitter() && it.isPowered());
	}
	
	@Override
	public boolean canBeRemoved() {
		return delegates.stream().allMatch(it -> it.canBeRemoved());
	}
	
	@Override
	public boolean canBeStackedOnTopOf(Circuit1x1ComponentModel other) {
		return delegates.stream().allMatch(it -> it.canBeStackedOnTopOf(other));
	}
	
	@Override
	public String toString() {
		return "Hybrid " + delegates;
	}
}
