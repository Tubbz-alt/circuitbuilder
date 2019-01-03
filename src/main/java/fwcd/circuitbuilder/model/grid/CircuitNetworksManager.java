package fwcd.circuitbuilder.model.grid;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.grid.cable.CableEvent;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
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
			Iterator<CableNetwork> iterator = extendableNetworks.iterator();
			CableNetwork first = iterator.next();
			first.add(event.getPos(), event.getCable());
			
			while (iterator.hasNext()) {
				CableNetwork network = iterator.next();
				first.merge(network);
				networks.remove(network);
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
			.flatMap(it -> it.getCables().stream())
			.forEach(CableModel::clearNetworkStatus);
		networks.clear();
	}
}
