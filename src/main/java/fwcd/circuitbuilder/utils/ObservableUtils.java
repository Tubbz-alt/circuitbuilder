package fwcd.circuitbuilder.utils;

import fwcd.fructose.Observable;

public class ObservableUtils {
	private ObservableUtils() {}
	
	public static void toggle(Observable<Boolean> observable) {
		observable.set(!observable.get());
	}
}
