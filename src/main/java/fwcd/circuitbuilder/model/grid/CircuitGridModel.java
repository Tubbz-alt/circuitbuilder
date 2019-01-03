package fwcd.circuitbuilder.model.grid;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import fwcd.circuitbuilder.model.grid.cable.CableEvent;
import fwcd.circuitbuilder.model.grid.cable.CableMatcher;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.grid.components.CircuitLargeComponentModel;
import fwcd.circuitbuilder.model.grid.components.InputComponentModel;
import fwcd.circuitbuilder.model.grid.components.OutputComponentModel;
import fwcd.circuitbuilder.utils.ConcurrentMultiKeyHashMap;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.MultiKeyMap;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.EventListenerList;
import fwcd.fructose.ListenerList;

/**
 * A 2D-grid of circuit components.
 */
public class CircuitGridModel {
	private final Map<RelativePos, CircuitCellModel> cells = new ConcurrentHashMap<>();
	private final MultiKeyMap<RelativePos, CircuitLargeComponentModel> largeComponents = new ConcurrentMultiKeyHashMap<>();
	
	private final ListenerList changeListeners = new ListenerList();
	private final ListenerList clearListeners = new ListenerList();
	private final EventListenerList<CableEvent> addCableListeners = new EventListenerList<>();
	private final EventListenerList<CableEvent> removeCableListeners = new EventListenerList<>();
	
	/**
	 * Removes empty cells from the registered cells.<br><br>
	 * 
	 * (They will be re-initialized once they are needed again)
	 */
	public void cleanCells() {
		boolean cleaned = false;
		
		for (RelativePos key : cells.keySet()) {
			if (cells.containsKey(key) && cells.get(key).isEmpty()) {
				cells.remove(key);
				cleaned = true;
			}
		}
		
		if (cleaned) {
			changeListeners.fire();
		}
	}
	
	public boolean isCellEmpty(RelativePos pos) {
		return cells.containsKey(pos) ? Objects.requireNonNull(cells.get(pos)).isEmpty() : true;
	}
	
	public CircuitCellModel getCell(RelativePos pos) {
		cells.putIfAbsent(pos, new CircuitCellModel(pos));
		return Objects.requireNonNull(cells.get(pos));
	}
	
	public Map<Direction, CircuitCellModel> getNeighbors(RelativePos pos) {
		Map<Direction, CircuitCellModel> neighbors = new HashMap<>();
		
		for (Direction dir : Direction.values()) {
			neighbors.put(dir, getCell(pos.follow(dir)));
		}
		
		return neighbors;
	}
	
	public void clearCell(RelativePos pos) {
		if (!isCellEmpty(pos) && !cells.get(pos).containsUnremovableComponents()) {
			CircuitCellModel cell = cells.remove(pos);
			cell.streamComponents()
				.flatMap(it -> it.accept(CableMatcher.INSTANCE).stream())
				.forEach(cable -> removeCableListeners.fire(new CableEvent(cable, pos)));
		}
		
		if (largeComponents.containsKey(pos)) {
			for (RelativePos subPos : largeComponents.getAllMappings(pos)) {
				cells.remove(subPos);
			}
			
			largeComponents.removeAllMappings(pos);
		}
		
		changeListeners.fire();
	}
	
	public void putLarge(CircuitLargeComponentModel component, RelativePos pos) {
		for (InputComponentModel input : component.getInputs()) {
			RelativePos inputPos = new RelativePos(pos.add(input.getDeltaPos()));
			put(input, inputPos);
		}

		for (OutputComponentModel output : component.getOutputs()) {
			RelativePos outputPos = new RelativePos(pos.add(output.getDeltaPos()));
			put(output, outputPos);
		}
		
		largeComponents.put(pos, component, component.getOccupiedPositions(pos));
	}
	
	public void put(Circuit1x1ComponentModel component, RelativePos pos) {
		getCell(pos).place(component);
		
		Map<Direction, CircuitCellModel> neighbors = getNeighbors(pos);
		component.onPlace(neighbors);
		
		for (CircuitCellModel cell : neighbors.values()) {
			if (!cell.isEmpty()) {
				for (Circuit1x1ComponentModel neighborComponent : cell.getComponents()) {
					neighborComponent.onPlace(getNeighbors(cell.getPos()));
				}
			}
		}
		
		component.accept(CableMatcher.INSTANCE).ifPresent(cable -> addCableListeners.fire(new CableEvent(cable, pos)));
		changeListeners.fire();
	}
	
	public void forEach1x1(BiConsumer<CircuitCellModel, Circuit1x1ComponentModel> consumer) {
		for (CircuitCellModel cell : cells.values()) {
			cell.getComponents().forEach(component -> consumer.accept(cell, component));
		}
	}
	
	/**
	 * A read-only view of all large components on this grid.
	 * To add or remove cells, use {@code putLarge} or
	 * {@code clearCell} instead.
	 */
	public MultiKeyMap<? extends RelativePos, ? extends CircuitLargeComponentModel> getLargeComponents() { return largeComponents; }
	
	/**
	 * A read-only view of the cells on this grid.
	 * To add or remove cells, use {@code put} or
	 * {@code clearCell} instead.
	 */
	public Map<? extends RelativePos, ? extends CircuitCellModel> getCells() { return cells; }
	
	public void clear() {
		cells.clear();
		largeComponents.clear();
		clearListeners.fire();
		changeListeners.fire();
	}
	
	public ListenerList getChangeListeners() { return changeListeners; }
	
	public ListenerList getClearListeners() { return clearListeners; }
	
	public EventListenerList<CableEvent> getAddCableListeners() { return addCableListeners; }
	
	public EventListenerList<CableEvent> getRemoveCableListeners() { return removeCableListeners; }
}
