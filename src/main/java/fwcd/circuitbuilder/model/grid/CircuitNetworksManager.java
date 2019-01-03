package fwcd.circuitbuilder.model.grid;

import java.util.Set;

import fwcd.circuitbuilder.model.grid.cable.CableEvent;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;

public class CircuitNetworksManager {
	private final Set<CableNetwork> networks;
	
	public CircuitNetworksManager(Set<CableNetwork> networks) {
		this.networks = networks;
	}
	
	public void onAddCable(CableEvent event) {
		
	}
	
	public void onRemoveCable(CableEvent event) {
		
	}
	
	public void onClear() {
		networks.clear();
	}
}
