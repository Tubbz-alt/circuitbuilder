package fwcd.circuitbuilder.model.grid.cable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.Direction;
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
		
		for (Map.Entry<RelativePos, CableModel> entry : other.cables.entrySet()) {
			RelativePos pos = entry.getKey();
			if (!cables.keySet().contains(pos)) {
				CableModel cable = entry.getValue();
				cable.setNetworkStatus(status);
				cables.put(pos, cable);
				cableSet.add(cable);
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
			cable.setNetworkStatus(status);
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
			component.accept(CableMatcher.INSTANCE)
				.filter(it -> !contains(it))
				.ifPresent(cable -> {
					RelativePos pos = cell.getPos();
					if (add(pos, cable)) {
						for (Direction connection : cable.getConnections()) {
							buildRecursively(grid.getCell(new RelativePos(pos.add(connection.getVector()))), grid);
						}
					}
				});
		}
	}
	
	public boolean remove(RelativePos pos) {
		CableModel cable = cables.remove(pos);
		if (cable == null) {
			return false;
		} else {
			cable.clearNetworkStatus();
			cableSet.remove(cable);
			return true;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		CableNetwork other = (CableNetwork) obj;
		return cableSet.equals(other.cableSet);
	}
	
	@Override
	public int hashCode() {
		return cableSet.hashCode() * 7;
	}
	
	public boolean colorMatches(CableModel cable) {
		return color.flatMap(it -> cable.getColor().map(it::equals)).orElse(false);
	}
	
	public Set<? extends CableModel> getCableSet() { return cableSet; }
	
	public Set<? extends RelativePos> getPositions() { return cables.keySet(); }
	
	public Stream<RelativePos> streamPositions() { return cables.keySet().stream(); }
	
	public CableNetworkStatus getStatus() { return status; }
	
	public Option<String> getName() { return name; }
	
	public Option<CableColor> getColor() { return color; }
	
	public void setName(Option<String> name) { this.name = name; }
	
	public void setName(String name) { this.name = Option.of(name); }

	public boolean isEmpty() { return cableSet.isEmpty(); }
}
