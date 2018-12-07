package fwcd.circuitbuilder.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.components.CircuitLargeComponentModel;
import fwcd.circuitbuilder.model.components.InputComponentModel;
import fwcd.circuitbuilder.model.components.OutputComponentModel;
import fwcd.circuitbuilder.utils.ConcurrentMultiKeyHashMap;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.MultiKeyMap;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.ListenerList;

/**
 * A 2D-grid of circuit components.
 */
public class CircuitGridModel {
	private final Map<RelativePos, CircuitCellModel> cells = new ConcurrentHashMap<>();
	private final MultiKeyMap<RelativePos, CircuitLargeComponentModel> largeComponents = new ConcurrentMultiKeyHashMap<>();
	private final ListenerList changeListeners = new ListenerList();
	
	/**
	 * Removes empty cells from the registered cells.<br><br>
	 * 
	 * (They will be re-initialized once they are needed again)
	 */
	public void cleanCells() {
		for (RelativePos key : cells.keySet()) {
			if (cells.containsKey(key) && cells.get(key).isEmpty()) {
				cells.remove(key);
			}
		}
	}
	
	public boolean isCellEmpty(RelativePos pos) {
		return cells.containsKey(pos) ? cells.get(pos).isEmpty() : true;
	}
	
	public CircuitCellModel getCell(RelativePos pos) {
		cells.putIfAbsent(pos, new CircuitCellModel(pos));
		return cells.get(pos);
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
			cells.remove(pos);
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
		
		changeListeners.fire();
	}
	
	public void forEach1x1(BiConsumer<CircuitCellModel, Circuit1x1ComponentModel> consumer) {
		for (CircuitCellModel cell : cells.values()) {
			cell.getComponents().forEach(component -> consumer.accept(cell, component));
		}
	}
	
	public ListenerList getChangeListeners() { return changeListeners; }
	
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
		changeListeners.fire();
	}
}
