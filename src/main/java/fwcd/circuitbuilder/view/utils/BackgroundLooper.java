package fwcd.circuitbuilder.view.utils;

import javax.swing.Timer;

/**
 * A thread that recurrently runs a specified task
 * in the background.
 */
public class BackgroundLooper {
	private final Timer timer;
	private boolean started = false;
	
	public BackgroundLooper(int delayMs, Runnable task) {
		timer = new Timer(delayMs, l -> task.run());
		timer.setRepeats(true);
	}
	
	public void start() {
		if (started) {
			throw new IllegalStateException("Attempted to restart a running BackgroundLooper");
		}
		
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}
}
