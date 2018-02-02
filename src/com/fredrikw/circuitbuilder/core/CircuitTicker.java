package com.fredrikw.circuitbuilder.core;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Updates the signals on the circuit at a
 * fixed rate.
 * 
 * @author Fredrik
 *
 */
public class CircuitTicker {
	private final CircuitGrid grid;
	private final Timer timer = new Timer("Circuit Ticker");
	private final int tickDelayMs = 50;
	
	public CircuitTicker(CircuitGrid grid) {
		this.grid = grid;
	}
	
	public void start() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				grid.tick();
			}
			
		}, tickDelayMs, tickDelayMs);
	}
}
