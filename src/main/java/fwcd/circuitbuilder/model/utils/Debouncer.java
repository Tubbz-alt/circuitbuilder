package fwcd.circuitbuilder.model.utils;

public class Debouncer {
	private final long ms;
	private long last = System.currentTimeMillis();
	
	public Debouncer(long ms) {
		this.ms = ms;
	}
	
	public void runMaybe(Runnable task) {
		long now = System.currentTimeMillis();
		if ((now - last) > ms) {
			task.run();
			last = now;
		}
	}
}
