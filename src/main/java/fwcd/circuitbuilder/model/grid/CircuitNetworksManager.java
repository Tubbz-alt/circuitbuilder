package fwcd.circuitbuilder.model.grid;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.grid.cable.CableEvent;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.cable.CableNetwork;
import fwcd.circuitbuilder.model.grid.timediagram.TimeDiagramModel;
import fwcd.circuitbuilder.utils.RelativePos;

public class CircuitNetworksManager {
	private final Set<CableNetwork> networks;
	private final TimeDiagramModel timeDiagram = new TimeDiagramModel();
	
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
			addNetwork(network);
		} else {
			Iterator<CableNetwork> iterator = extendableNetworks.iterator();
			CableNetwork first = iterator.next();
			first.add(event.getPos(), event.getCable());
			
			while (iterator.hasNext()) {
				CableNetwork network = iterator.next();
				first.merge(network);
				removeNetwork(network);
			}
		}
	}
	
	public void onRemoveCable(CableEvent event) {
		RelativePos pos = event.getPos();
		Iterator<CableNetwork> iterator = networks.iterator();
		boolean removedCable = false;
		
		while (!removedCable && iterator.hasNext()) {
			CableNetwork network = iterator.next();
			
			if (network.colorMatches(event.getCable())) {
				removedCable = network.getPositions().contains(pos);
				
				if (removedCable) {
					removeNetwork(network);
					
					if (!network.isEmpty()) {
						Set<CableNetwork> splitted = network.splitAt(pos);
						addAllNetworks(splitted);
					}
					
					network.remove(pos);
				}
			}
		}
	}
	
	private void addNetwork(CableNetwork network) {
		networks.add(network);
		timeDiagram.onAdd(network);
	}
	
	private void addAllNetworks(Iterable<? extends CableNetwork> added) {
		for (CableNetwork network : added) {
			addNetwork(network);
		}
	}
	
	private void removeNetwork(CableNetwork network) {
		networks.remove(network);
		timeDiagram.onRemove(network);
	}
	
	public void onClear() {
		networks.stream()
			.flatMap(it -> it.getCables().stream())
			.forEach(CableModel::clearNetworkStatus);
		networks.clear();
		timeDiagram.onClear();
	}
	
	public Set<? extends CableNetwork> getNetworks() {
		return networks;
	}
	
	public TimeDiagramModel getTimeDiagram() {
		return timeDiagram;
	}
}
