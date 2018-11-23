package com.fwcd.circuitbuilder.model.cable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitGridModel;
import com.fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import com.fwcd.circuitbuilder.utils.RelativePos;
import com.fwcd.fructose.Option;

public class CableNetwork {
	private final CableNetworkStatus status = new CableNetworkStatus();
	private Option<CableColor> color = Option.empty();
	private Map<RelativePos, CableModel> cables = new HashMap<>();
	private Set<CableModel> cableSet = new HashSet<>();
	
	private boolean add(RelativePos pos, CableModel cable) {
		Option<CableColor> cableColor = cable.getColor();
		
		if (!color.isPresent()) {
			color = cableColor;
		}
		
		boolean colorMatches = color
			.flatMap(c -> cableColor.map(c::equals))
			.orElse(false);
		
		if (colorMatches) {
			cables.put(pos, cable);
			cableSet.add(cable);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean contains(Circuit1x1ComponentModel cable) {
		return cableSet.contains(cable);
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
		for (Circuit1x1ComponentModel component : cell.getComponents()) {
			if (component instanceof CableModel && !contains(component)) {
				CableModel cable = (CableModel) component;
				if (add(cell.getPos(), cable)) {
					for (CircuitCellModel neighborCell : grid.getNeighbors(cell.getPos()).values()) {
						buildRecursively(neighborCell, grid);
					}
				}
			}
		}
	}
	
	public Set<? extends RelativePos> getPositions() { return cables.keySet(); }
	
	public CableNetworkStatus getStatus() { return status; }
}
