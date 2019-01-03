package fwcd.circuitbuilder.model.grid;

import java.util.stream.Stream;

import fwcd.circuitbuilder.model.grid.cable.CableColorEqualityChecker;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Option;
import fwcd.fructose.StreamUtils;
import fwcd.fructose.structs.ArraySetStack;
import fwcd.fructose.structs.SetStack;

/**
 * A cell that holds a stack of 1x1 components.
 */
public class CircuitCellModel {
	private final RelativePos pos;
	private final SetStack<Circuit1x1ComponentModel> components = new ArraySetStack<>(new CableColorEqualityChecker());
	
	public CircuitCellModel(RelativePos pos) {
		this.pos = pos;
	}
	
	boolean place(Circuit1x1ComponentModel component) {
		if (!isEmpty()) {
			if (component.canBeStackedOnTopOf(components.peek())) {
				components.push(component);
				return true;
			} else if (component.canReplaceOtherComponent()) {
				components.rebase(component);
				return true;
			}
		} else {
			components.rebase(component);
			return true;
		}
		return false;
	}
	
	public Option<Circuit1x1ComponentModel> getComponent() { return components.optionalPeek(); }
	
	public RelativePos getPos() { return pos; }
	
	public boolean isEmpty() { return components.isEmpty(); }
	
	public Iterable<Circuit1x1ComponentModel> getComponents() { return components.asBottomToTopList(); }
	
	public Stream<Circuit1x1ComponentModel> streamComponents() { return StreamUtils.stream(components); }
	
	public boolean containsUnremovableComponents() {
		return streamComponents().anyMatch(it -> !it.canBeRemoved());
	}
}
