package fwcd.circuitbuilder.model.grid;

import java.util.HashSet;
import java.util.Set;

import fwcd.circuitbuilder.model.grid.cable.CableNetwork;

/**
 * An engine that ticks and updates components on a circuit grid.
 */
public class CircuitEngineModel {
	private static final boolean DEBUG_NETWORKS = false;
	private final CircuitGridModel grid;
	private final Set<CableNetwork> networks = new HashSet<>();
	
	private boolean autoCleanCells = true;
	
	public CircuitEngineModel(CircuitGridModel grid) {
		this.grid = grid;
		
		CircuitNetworksManager networkBuilder = new CircuitNetworksManager(networks);
		grid.getAddCableListeners().add(networkBuilder::onAddCable);
		grid.getRemoveCableListeners().add(networkBuilder::onRemoveCable);
		grid.getClearListeners().add(networkBuilder::onClear);
	}
	
	public void tick() {
		// Pre ticking - Updating networks
		
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
	
	/**
	 * A set of cable networks.
	 */
	public Set<? extends CableNetwork> getCableNetworks() {
		return networks;
	}
}
