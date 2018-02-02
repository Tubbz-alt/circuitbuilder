package com.fredrikw.circuitbuilder.core;

import java.util.Iterator;
import java.util.Optional;

import com.fredrikw.circuitbuilder.items.components.Cable;
import com.fredrikw.circuitbuilder.items.components.CableColorEqualityChecker;
import com.fredrikw.circuitbuilder.items.components.CircuitComponent;
import com.fredrikw.circuitbuilder.utils.Direction;
import com.fredrikw.circuitbuilder.utils.RelativePos;
import com.fwcd.fructose.structs.ArraySetStack;
import com.fwcd.fructose.structs.SetStack;

public class CircuitCell implements Iterable<CircuitComponent> {
	private final CircuitGrid grid;
	private final RelativePos pos;
	
	private SetStack<CircuitComponent> components = new ArraySetStack<>(new CableColorEqualityChecker());
	
	public CircuitCell(CircuitGrid grid, RelativePos pos) {
		this.grid = grid;
		this.pos = pos;
	}
	
	public CircuitCell getNeighbor(Direction direction) {
		return grid.getCell(new RelativePos(pos.add(direction.getVector())));
	}
	
	public boolean isEmpty() {
		return components.isEmpty();
	}
	
	public void place(CircuitComponent component) {
		if (component instanceof Cable) {
			components.push(component);
		} else {
			components.rebase(component);
		}
	}
	
	public Optional<CircuitComponent> getComponent() {
		return components.optionalPeek();
	}
	
	public RelativePos getPos() {
		return pos;
	}
	
	@Override
	public String toString() {
		return pos.toString();
	}

	public void clear() {
		grid.clearCell(pos);
	}

	@Override
	public Iterator<CircuitComponent> iterator() {
		return components.asBottomToTopList().iterator();
	}

	public boolean containsUnremovableComponents() {
		for (CircuitComponent component : components) {
			if (!component.canBeRemoved()) {
				return true;
			}
		}
		
		return false;
	}
}
