package fwcd.circuitbuilder.model.utils;

import fwcd.fructose.Observable;

public class SignalFunctionPlotModel {
	private final String name;
	private final SignalFunctionSegment segment;
	private final Observable<Double> phase = new Observable<>(0.0);
	
	public SignalFunctionPlotModel(String name, SignalFunctionSegment segment) {
		this.name = name;
		this.segment = segment;
	}
	
	public String getName() { return name; }
	
	public Observable<Double> getPhase() { return phase; }
	
	public SignalFunctionSegment getSegment() { return segment; }
}
