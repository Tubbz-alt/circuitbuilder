package fwcd.circuitbuilder.model.grid.cable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Observable;
import fwcd.fructose.Option;

public class CableNetwork {
	private final CableNetworkStatus status = new CableNetworkStatus();
	private final Observable<Option<String>> name = new Observable<>(Option.empty());
	private Option<CableColor> color = Option.empty();
	private Map<RelativePos, CableModel> cables = new HashMap<>();
	
	/**
	 * Merges the cables and metadata from the other network
	 * into this one.
	 */
	public void merge(CableNetwork other) {
		name.set(name.get().or(other.getName()::get));
		cables.putAll(other.cables);
		
		for (CableModel cable : cables.values()) {
			cable.setNetworkStatus(status);
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
			return true;
		} else {
			return false;
		}
	}
	
	private Set<RelativePos> getConnectedSegment(RelativePos pos, Set<RelativePos> ignored) {
		if (ignored.contains(pos)) {
			return Collections.emptySet();
		} else {
			Set<RelativePos> positions = new HashSet<>();
			findConnectedSegment(pos, positions, ignored);
			return positions;
		}
	}
	
	private void findConnectedSegment(RelativePos pos, Set<RelativePos> visited, Set<RelativePos> ignored) {
		visited.add(pos);
		for (Direction direction : cables.get(pos).getConnections()) {
			RelativePos neighbor = new RelativePos(pos.add(direction.getVector()));
			if (!visited.contains(neighbor) && !ignored.contains(neighbor) && cables.keySet().contains(neighbor)) {
				findConnectedSegment(neighbor, visited, ignored);
			}
		}
	}

	public Set<CableNetwork> splitAt(RelativePos splitPos) {
		CableModel cable = cables.remove(splitPos);
		
		if (cable == null) {
			return Collections.singleton(this);
		} else {
			Set<RelativePos> startPositions = cable.getConnections().stream()
				.map(Direction::getVector)
				.map(splitPos::add)
				.map(RelativePos::new)
				.filter(cables.keySet()::contains)
				.collect(Collectors.toSet());
			Set<CableNetwork> networks = new HashSet<>(4);	
			Set<RelativePos> visited = new HashSet<>();
			
			for (RelativePos startPos : startPositions) {
				Set<RelativePos> connectedSegment = getConnectedSegment(startPos, visited);
				
				if (!connectedSegment.isEmpty()) {
					CableNetwork network = new CableNetwork();
					network.getName().set(name.get());
					network.color = color;
					
					for (RelativePos connectedPos : connectedSegment) {
						network.add(connectedPos, cables.get(connectedPos));
						visited.add(connectedPos);
					}
					
					networks.add(network);
				}
			}
			
			return networks;
		}
	}
	
	public boolean canBeExtendedTo(RelativePos pos) {
		return cables.keySet().stream()
			.anyMatch(it ->
				(Math.abs(pos.getX() - it.getX()) <= 1 && pos.getY() == it.getY())
				|| (Math.abs(pos.getY() - it.getY()) <= 1 && pos.getX() == it.getX())
			);
	}
	
	private boolean contains(RelativePos pos) {
		return cables.keySet().contains(pos);
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
		RelativePos pos = cell.getPos();
		for (Circuit1x1ComponentModel component : cell.getComponents()) {
			component.accept(CableMatcher.INSTANCE)
				.filter(it -> !contains(pos))
				.ifPresent(cable -> {
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
			return true;
		}
	}
	
	public boolean colorMatches(CableModel cable) {
		return color.flatMap(it -> cable.getColor().map(it::equals)).orElse(false);
	}
	
	public CableModel cableAt(RelativePos pos) { return cables.get(pos); }
	
	public Collection<? extends CableModel> getCables() { return cables.values(); }
	
	public Set<? extends RelativePos> getPositions() { return cables.keySet(); }
	
	public Stream<RelativePos> streamPositions() { return cables.keySet().stream(); }
	
	public CableNetworkStatus getStatus() { return status; }
	
	public Observable<Option<String>> getName() { return name; }
	
	public Option<CableColor> getColor() { return color; }

	public boolean isEmpty() { return cables.isEmpty(); }
	
	public String toDebugString() { return name.get().orElse("<Network>") + " " + getPositions() + " >> " + getStatus().isPowered(); }
	
	@Override
	public String toString() { return name.get().orElse("<Network>"); }
}
