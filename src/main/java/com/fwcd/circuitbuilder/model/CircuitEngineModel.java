package com.fwcd.circuitbuilder.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fwcd.circuitbuilder.model.cable.CableModel;
import com.fwcd.circuitbuilder.model.cable.CableNetwork;
import com.fwcd.circuitbuilder.utils.RelativePos;

/**
 * An engine that ticks and updates components
 * on a circuit grid.
 */
public class CircuitEngineModel {
	private final CircuitGridModel grid;
	private final List<CableNetwork> cableNetworks = new ArrayList<>();
	
	private boolean autoCleanCells = true;
	
	public CircuitEngineModel(CircuitGridModel grid) {
		this.grid = grid;
	}
	
	public void tick() {
		Set<RelativePos> networkCoverage = new HashSet<>();
		
		// Pre ticking - Grouping of cables using networks
		
		grid.forEach1x1((cell, component) -> {
			if (component instanceof CableModel) {
				RelativePos pos = cell.getPos();
				
				if (!networkCoverage.contains(pos)) {
					CableNetwork network = new CableNetwork();
					network.build(pos, grid);
					cableNetworks.add(network);
				}
			}
		});
		
		// Main ticking
		
		grid.forEach1x1((cell, component) -> component.tick(grid.getNeighbors(cell.getPos())));
		grid.getLargeComponents().values().forEach(nestedCircuit -> nestedCircuit.tick());
		
		// Updating
		
		grid.forEach1x1((cell, component) -> component.update());
		
		// Cleaning
		
		if (autoCleanCells) {
			grid.cleanCells();
		}
		
		grid.getChangeListeners().fire();
	}
}
