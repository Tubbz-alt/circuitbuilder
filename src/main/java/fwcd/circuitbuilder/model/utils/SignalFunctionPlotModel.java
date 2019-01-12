package fwcd.circuitbuilder.model.utils;

import fwcd.fructose.Observable;

public class SignalFunctionPlotModel {
	private final SignalFunctionSegment segment;
	private final Observable<Double> phase;
	
	public SignalFunctionPlotModel(SignalFunctionSegment segment) {
		this(segment, new Observable<>(0.0));
	}
	
	public SignalFunctionPlotModel(SignalFunctionSegment segment, Observable<Double> phase) {
		this.segment = segment;
		this.phase = phase;
	}
	
	public Observable<Double> getPhase() { return phase; }
	
	public SignalFunctionSegment getSegment() { return segment; }
}
