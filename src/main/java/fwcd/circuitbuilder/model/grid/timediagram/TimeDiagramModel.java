package fwcd.circuitbuilder.model.grid.timediagram;

import fwcd.circuitbuilder.model.utils.SignalFunctionSegment;
import fwcd.fructose.Observable;
import fwcd.fructose.structs.ObservableList;

public class TimeDiagramModel {
	private final ObservableList<SignalFunctionSegment> segments = new ObservableList<>();
	private final Observable<Double> phase = new Observable<>(0.0);
	
	public ObservableList<SignalFunctionSegment> getSegments() { return segments; }
	
	public Observable<Double> getPhase() { return phase; }
}
