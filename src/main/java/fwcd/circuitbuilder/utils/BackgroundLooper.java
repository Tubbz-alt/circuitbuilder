package fwcd.circuitbuilder.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A thread that recurrently runs a specified task
 * in the background.
 */
public class BackgroundLooper {
	private final int delayMs;
	private final Runnable task;
	private final String name;
	private final Timer timer;
	private boolean started = false;
	
	public BackgroundLooper(String name, int delayMs, Runnable task) {
		this.name = name;
		this.delayMs = delayMs;
		this.task = task;
		timer = new Timer(name);
	}
	
	public void start() {
		if (started) {
			throw new IllegalStateException("Attempted to restart a running BackgroundLooper");
		}
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				task.run();
			}
		}, delayMs, delayMs);
	}
	
	public String getName() {
		return name;
	}
	
	public void stop() {
		timer.cancel();
	}
}
