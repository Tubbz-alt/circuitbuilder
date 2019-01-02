package fwcd.circuitbuilder.model.grid.cable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Option;

public class CableNetwork {
	private final CableNetworkStatus status = new CableNetworkStatus();
	private Option<String> name = Option.empty();
	private Option<CableColor> color = Option.empty();
	private Map<RelativePos, CableModel> cables = new HashMap<>();
	private Set<CableModel> cableSet = new LinkedHashSet<>();
	
	/**
	 * Merges the cables and metadata from the other network
	 * into this one.
	 */
	public void merge(CableNetwork other) {
		name = name.or(other::getName);
		cables.putAll(other.cables);
		cableSet.addAll(other.cableSet);
	}
	
	/**
	 * Extracts cables that are not directly connected to
	 * this network into new, continuous networks.
	 */
	public Set<CableNetwork> splitOffDisconnectedCables(CircuitGridModel grid) {
		Stream.Builder<CableNetwork> stream = Stream.builder();
		
		splitOffDisconnectedCablesInto(stream, grid, cables.keySet());
		
		return stream.build().collect(Collectors.toSet());
	}
	
	private void splitOffDisconnectedCablesInto(Stream.Builder<CableNetwork> stream, CircuitGridModel grid, Set<RelativePos> positions) {
		Set<RelativePos> visited = new HashSet<>();
		aggregateConnectedSegment(grid.getCell(positions.iterator().next()), grid, visited, positions);
		
		CableNetwork splitted = new CableNetwork();
		splitted.name = name;
		splitted.color = color;
		
		for (RelativePos pos : visited) {
			CableModel cable = Objects.requireNonNull(splitted.cables.remove(pos));
			splitted.add(pos, cable);
		}
		
		splitted.splitOffDisconnectedCablesInto(stream, grid, splitted.cables.keySet());
		stream.accept(splitted);
	}
	
	private void aggregateConnectedSegment(CircuitCellModel cell, CircuitGridModel grid, Set<RelativePos> visited, Set<RelativePos> positions) {
		RelativePos pos = cell.getPos();
		if (!visited.contains(pos) && positions.contains(pos)) {
			for (CircuitCellModel neighborCell : grid.getNeighbors(cell.getPos()).values()) {
				aggregateConnectedSegment(neighborCell, grid, visited, positions);
			}
		}
	}
	
	public boolean add(RelativePos pos, CableModel cable) {
		Option<CableColor> cableColor = cable.getColor();
		
		if (!color.isPresent()) {
			color = cableColor;
		}
		
		boolean colorMatches = color
			.flatMap(c -> cableColor.map(c::equals))
			.orElse(false);
		
		if (colorMatches) {
			cables.put(pos, cable);
			cableSet.add(cable);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canBeExtendedTo(RelativePos pos) {
		return cables.keySet().stream()
			.anyMatch(it ->
				(Math.abs(pos.getX() - it.getX()) <= 1 && pos.getY() == it.getY())
				|| (Math.abs(pos.getY() - it.getY()) <= 1 && pos.getX() == it.getX())
			);
	}
	
	private boolean contains(Circuit1x1ComponentModel cable) {
		return cableSet.contains(cable);
	}
	
	public void updateStatus(CircuitGridModel grid) {
		status.setPowered(isPowered(grid));
	}
	
	private boolean isPowered(CircuitGridModel grid) {
		for (RelativePos cablePos : cables.keySet()) {
			CableModel cable = cables.get(cablePos);
			
			if (cable.isConnectedToEmitter(grid.getNeighbors(cablePos))) {
				return true;
			}
		}
		
		return false;
	}
	
	public void build(RelativePos pos, CircuitGridModel grid) {
		buildRecursively(grid.getCell(pos), grid);
	}
	
	private void buildRecursively(CircuitCellModel cell, CircuitGridModel grid) {
		for (Circuit1x1ComponentModel component : cell.getComponents()) {
			if (component instanceof CableModel && !contains(component)) {
				CableModel cable = (CableModel) component;
				if (add(cell.getPos(), cable)) {
					for (CircuitCellModel neighborCell : grid.getNeighbors(cell.getPos()).values()) {
						buildRecursively(neighborCell, grid);
					}
				}
			}
		}
	}
	
	public Set<? extends RelativePos> getPositions() { return cables.keySet(); }
	
	public Stream<RelativePos> streamPositions() { return cables.keySet().stream(); }
	
	public CableNetworkStatus getStatus() { return status; }
	
	public Option<String> getName() { return name; }
	
	public Option<CableColor> getColor() { return color; }
	
	public void setName(Option<String> name) { this.name = name; }
	
	public void setName(String name) { this.name = Option.of(name); }
}
