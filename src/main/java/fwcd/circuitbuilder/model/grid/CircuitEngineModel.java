package fwcd.circuitbuilder.model.grid;

import java.util.HashSet;
import java.util.Set;

import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.model.grid.timediagram.TimeDiagramModel;

/**
 * An engine that ticks and updates components on a circuit grid.
 */
public class CircuitEngineModel {
	private static final boolean DEBUG_NETWORKS = false;
	private final CircuitGridModel grid;
	private final CircuitNetworksManager networkManager;
	
	private boolean autoCleanCells = true;
	
	public CircuitEngineModel(CircuitGridModel grid) {
		this.grid = grid;
		
		networkManager = new CircuitNetworksManager(new HashSet<>());
		grid.getAddCableListeners().add(networkManager::onAddCable);
		grid.getRemoveCableListeners().add(networkManager::onRemoveCable);
		grid.getClearListeners().add(networkManager::onClear);
	}
	
	public void tick() {
		// Pre ticking - Updating networks
		
		for (CableNetwork network : getCableNetworks()) {
			network.updateStatus(grid);
		}
		
		if (DEBUG_NETWORKS) {
			// TODO: Logging
			System.out.println("Networks: [\n" + getCableNetworks().stream().map(Object::toString).reduce((a, b) -> a + "\n" + b).orElse("") + "\n]");
		}
		
		// Main ticking
		
		grid.forEach1x1((cell, component) -> component.tick(grid.getNeighbors(cell.getPos())));
		grid.getLargeComponents().values().forEach(largeComponent -> largeComponent.tick());
		
		// Updating
		
		grid.forEach1x1((cell, component) -> component.update());
		getTimeDiagram().onTick();
		
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
		return networkManager.getNetworks();
	}
	
	public TimeDiagramModel getTimeDiagram() {
		return networkManager.getTimeDiagram();
	}
}
