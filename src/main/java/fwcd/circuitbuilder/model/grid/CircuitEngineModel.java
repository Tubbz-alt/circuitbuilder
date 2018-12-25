package fwcd.circuitbuilder.model.grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.grid.cable.CableColor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * An engine that ticks and updates components
 * on a circuit grid.
 */
public class CircuitEngineModel {
	private static final boolean DEBUG_NETWORKS = false;
	private final CircuitGridModel grid;
	
	private boolean autoCleanCells = true;
	
	public CircuitEngineModel(CircuitGridModel grid) {
		this.grid = grid;
	}
	
	public void tick() {
		List<CableNetwork> networks = grid.getCableNetworks();
		Map<CableColor, Map<RelativePos, CableNetwork>> networkCoverage = new HashMap<>();
		networks.clear();
		
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
					network = new CableNetwork();
					network.build(pos, grid);
					
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
			System.out.println("Networks: " + networks.stream().map(it -> "Network " + it.getPositions() + " >> " + it.getStatus().isPowered()).collect(Collectors.toList()));
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
