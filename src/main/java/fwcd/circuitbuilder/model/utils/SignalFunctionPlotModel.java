package fwcd.circuitbuilder.model.utils;

import fwcd.fructose.Observable;

public class SignalFunctionPlotModel {
	private final SignalFunctionSegment segment;
	private Observable<Double> phase = new Observable<>(0.0);
}
