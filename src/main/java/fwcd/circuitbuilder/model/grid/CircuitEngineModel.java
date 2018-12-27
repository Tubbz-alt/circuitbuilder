package fwcd.circuitbuilder.model.grid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.grid.cable.CableColor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An engine that ticks and updates components on a circuit grid.
 */
public class CircuitEngineModel {
	private static final boolean DEBUG_NETWORKS = false;
	private final CircuitGridModel grid;
	
	private boolean autoCleanCells = true;
	
	public CircuitEngineModel(CircuitGridModel grid) {
		this.grid = grid;
	}
	
	public void tick() {
		Set<CableNetwork> networks = grid.getCableNetworks();
		Map<CableColor, Map<RelativePos, CableNetwork>> networkCoverage = new HashMap<>();
		
		// Pre ticking - Grouping of cables using networks
		
		grid.forEach1x1((cell, component) -> {
			if (component instanceof CableModel) {
				CableModel cable = (CableModel) component;
				RelativePos pos = cell.getPos();
				CableColor color = cable.getColor().unwrap();
				
				networkCoverage.putIfAbsent(color, new HashMap<>());
				Map<RelativePos, CableNetwork> colorCoverage = networkCoverage.get(color);
				CableNetwork network;
				
				if (colorCoverage.containsKey(pos)) {
					network = colorCoverage.get(pos);
				} else {
					Set<CableNetwork> extendables = networks.stream()
						.filter(it -> it.canBeExtendedTo(pos) && it.getColor().filter(color::equals).isPresent())
						.collect(Collectors.toSet());
					
					if (!extendables.isEmpty()) {
						// Merge extendable networks into one
						while (extendables.size() > 1) {
							Iterator<CableNetwork> iterator = extendables.iterator();
							CableNetwork a = iterator.next();
							CableNetwork b = iterator.next();
							
							a.merge(b);
							networks.remove(b);
							iterator.remove();
						}
						
						network = extendables.iterator().next();
						network.add(pos, cable);
					} else {
						// Otherwise create a new network
						network = new CableNetwork();
						network.build(pos, grid);
					}
					
					for (RelativePos networkPos : network.getPositions()) {
						colorCoverage.put(networkPos, network);
					}
					
					networks.add(network);
				}
				cable.setNetworkStatus(network.getStatus());
			}
		});
		
		for (CableNetwork network : networks) {
			network.updateStatus(grid);
		}
		
		if (DEBUG_NETWORKS) {
			// TODO: Logging
			System.out.println("Networks: [\n" + networks.stream().map(it -> "  Network " + it.getPositions() + " >> " + it.getStatus().isPowered()).reduce((a, b) -> a + "\n" + b).orElse("") + "\n]");
		}
		
		// Main ticking
		
		grid.forEach1x1((cell, component) -> component.tick(grid.getNeighbors(cell.getPos())));
		grid.getLargeComponents().values().forEach(largeComponent -> largeComponent.tick());
		
		// Updating
		
		grid.forEach1x1((cell, component) -> component.update());
		
		// Cleaning
		
		if (autoCleanCells) {
			grid.cleanCells();
		}
		
		grid.getChangeListeners().fire();
	}
}
