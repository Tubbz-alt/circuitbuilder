package com.fwcd.circuitbuilder.core;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A thread that cleans up empty cells in the
 * grid at a specific rate in the background.
 * 
 * @author Fredrik
 *
 */
public class CellCleaner {
	private final CircuitGrid grid;
	private final Timer timer = new Timer("Cell Cleaner");
	
	private final int cleanupDelayMs = 30000;
	
	public CellCleaner(CircuitGrid grid) {
		this.grid = grid;
	}
	
	public void start() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				grid.cleanCells();
			}
			
		}, cleanupDelayMs, cleanupDelayMs);
	}
}
