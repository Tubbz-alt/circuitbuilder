package fwcd.circuitbuilder.model.grid;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.grid.cable.CableEvent;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;

public class CircuitNetworksManager {
	private final Set<CableNetwork> networks;
	
	public CircuitNetworksManager(Set<CableNetwork> networks) {
		this.networks = networks;
	}
	
	public void onAddCable(CableEvent event) {
		Set<CableNetwork> extendableNetworks = networks.stream()
			.filter(network -> network.colorMatches(event.getCable()) && network.canBeExtendedTo(event.getPos()))
			.collect(Collectors.toSet());
		
		if (extendableNetworks.isEmpty()) {
			CableNetwork network = new CableNetwork();
			network.add(event.getPos(), event.getCable());
			networks.add(network);
		} else {
			Iterator<CableNetwork> iterator = networks.iterator();
			CableNetwork first = iterator.next();
			
			while (iterator.hasNext()) {
				first.merge(iterator.next());
				iterator.remove();
			}
		}
	}
	
	public void onRemoveCable(CableEvent event) {
		for (CableNetwork network : networks) {
			if (network.colorMatches(event.getCable())) {
				network.remove(event.getPos());
				
				// TODO: Splitting
				
				if (network.isEmpty()) {
					networks.remove(network);
				}
			}
		}
	}
	
	public void onClear() {
		networks.stream()
			.map(it -> it.getCableSet().stream())
			.collect(Collectors.toSet());
		networks.clear();
	}
}
