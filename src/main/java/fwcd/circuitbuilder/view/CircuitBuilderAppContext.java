package fwcd.circuitbuilder.view;

import java.util.ArrayDeque;
import java.util.Queue;

import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.view.grid.CircuitGridContext;

/**
 * Maintains window-level "global" UI state.
 */
public class CircuitBuilderAppContext {
	private final CircuitGridContext gridContext = new CircuitGridContext();
	private final Queue<Runnable> queue = new ArrayDeque<>();
	private boolean launched = false;
	
	public void runAfterLaunch(Runnable task) {
		if (launched) {
			runTask(task);
		} else {
			queue.offer(task);
		}
	}
	
	void pollAndRunLaunchTasks() {
		while (!queue.isEmpty()) {
			runTask(queue.poll());
		}
	}
	
	private void runTask(Runnable task) {
		SwingUtilities.invokeLater(task);
	}
	
	public CircuitGridContext getGridContext() {
		return gridContext;
	}
}
