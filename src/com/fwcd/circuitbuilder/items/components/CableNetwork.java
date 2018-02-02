package com.fwcd.circuitbuilder.items.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fwcd.circuitbuilder.core.CircuitCell;
import com.fwcd.circuitbuilder.core.CircuitGrid;
import com.fwcd.circuitbuilder.utils.RelativePos;

public class CableNetwork {
	private final CircuitGrid grid;
	
	private CableColor color = null;
	private Map<RelativePos, Cable> cables = new HashMap<>();
	private Set<Cable> cableSet = new HashSet<>();
	
	public CableNetwork(CircuitGrid grid) {
		this.grid = grid;
	}
	
	private boolean add(RelativePos pos, Cable cable) {
		if (color == null) {
			color = cable.getColor();
		}
		
		if (cable.getColor() == color) {
			cables.put(pos, cable);
			cableSet.add(cable);
			return true;
		} else {
			return false;
		}
	}
	
	public void build(RelativePos startPos) {
		buildRecursively(grid.getCell(startPos));
	}
	
	private void buildRecursively(CircuitCell cell) {
		for (CircuitComponent component : cell) {
			if (component instanceof Cable && !contains((Cable) component)) {
				if (add(cell.getPos(), (Cable) component)) {
					((Cable) component).setNetwork(this);
					for (CircuitCell neighborCell : grid.getNeighbors(cell.getPos()).values()) {
						buildRecursively(neighborCell);
					}
				}
			}
		}
	}
	
	public boolean contains(Cable cable) {
		return cableSet.contains(cable);
	}
	
	public boolean isPowered() {
		for (RelativePos cablePos : cables.keySet()) {
			Cable cable = cables.get(cablePos);
			
			if (cable.isConnectedToEmitter(grid.getNeighbors(cablePos))) {
				return true;
			}
		}
		
		return false;
	}
}
