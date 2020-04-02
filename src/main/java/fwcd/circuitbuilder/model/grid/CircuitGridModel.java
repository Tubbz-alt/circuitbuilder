package fwcd.circuitbuilder.model.grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.grid.cable.CableEvent;
import fwcd.circuitbuilder.model.grid.cable.CableMatcher;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.grid.components.CircuitLargeComponentModel;
import fwcd.circuitbuilder.model.grid.components.HybridComponent;
import fwcd.circuitbuilder.model.grid.components.IOComponentModel;
import fwcd.circuitbuilder.utils.ConcurrentMultiKeyHashMap;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.MultiKeyMap;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.EventListenerList;
import fwcd.fructose.ListenerList;
import fwcd.fructose.Option;
import fwcd.fructose.util.StreamUtils;

/**
 * A 2D-grid of circuit components.
 */
public class CircuitGridModel {
	private final ConcurrentMap<RelativePos, CircuitCellModel> cells = new ConcurrentHashMap<>();
	private final MultiKeyMap<RelativePos, CircuitLargeComponentModel> largeComponents = new ConcurrentMultiKeyHashMap<>();

	private transient ListenerList changeListeners;
	private transient ListenerList clearListeners;
	private transient EventListenerList<CableEvent> addCableListeners;
	private transient EventListenerList<CableEvent> removeCableListeners;

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
			for (RelativePos subPos : largeComponents.getSubKeys(pos)) {
				cells.remove(subPos);
			}
			
			largeComponents.removeAllMappings(pos);
		}
		
		changeListeners.fire();
	}
	
	public void putLarge(CircuitLargeComponentModel component, RelativePos pos) {
		Stream.<IOComponentModel>concat(
			component.getInputs().stream(),
			component.getOutputs().stream()
		)
			.collect(Collectors.groupingBy(it -> new RelativePos(pos.add(it.getDeltaPos()))))
			.entrySet()
			.forEach(positionedComponent -> {
				RelativePos componentPos = positionedComponent.getKey();
				List<IOComponentModel> stacked = positionedComponent.getValue();
				
				if (stacked.size() == 1) {
					put(stacked.get(0), componentPos);
				} else {
					put(new HybridComponent(stacked.stream().toArray(Circuit1x1ComponentModel[]::new)), componentPos);
				}
			});
		
		largeComponents.put(pos, component, component.getOccupiedPositions(pos));
	}
	
	public void put(Circuit1x1ComponentModel component, RelativePos pos) {
		CircuitCellModel cell = getCell(pos);
		Option<CableModel> removedCable = Option.of(StreamUtils.stream(cell.getComponents()).flatMap(it -> it.accept(CableMatcher.INSTANCE).stream()).findAny());
		
		if (cell.place(component)) {
			Map<Direction, CircuitCellModel> neighbors = getNeighbors(pos);
			component.onPlace(neighbors);
			
			for (CircuitCellModel neighborCell : neighbors.values()) {
				if (!neighborCell.isEmpty()) {
					for (Circuit1x1ComponentModel neighborComponent : neighborCell.getComponents()) {
						neighborComponent.onPlace(getNeighbors(neighborCell.getPos()));
					}
				}
			}
			
			Option<CableModel> addedCable = component.accept(CableMatcher.INSTANCE);
			removedCable.filter(it -> !addedCable.isPresent()).ifPresent(removed -> removeCableListeners.fire(new CableEvent(removed, pos)));
			addedCable.ifPresent(added -> addCableListeners.fire(new CableEvent(added, pos)));
			changeListeners.fire();
		}
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
	public MultiKeyMap<RelativePos, ? extends CircuitLargeComponentModel> getLargeComponents() { return largeComponents; }
	
	/**
	 * A read-only view of the cells on this grid.
	 * To add or remove cells, use {@code put} or
	 * {@code clearCell} instead.
	 */
	public Map<RelativePos, ? extends CircuitCellModel> getCells() { return cells; }
	
	public void clear() {
		cells.clear();
		largeComponents.clear();
		clearListeners.fire();
		changeListeners.fire();
	}
	
	void setChangeListeners(ListenerList changeListeners) { this.changeListeners = changeListeners; }
	
	void setClearListeners(ListenerList clearListeners) { this.clearListeners = clearListeners; }
	
	void setAddCableListeners(EventListenerList<CableEvent> addCableListeners) { this.addCableListeners = addCableListeners; }
	
	void setRemoveCableListeners(EventListenerList<CableEvent> removeCableListeners) { this.removeCableListeners = removeCableListeners; }
}
