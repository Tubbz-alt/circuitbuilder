package fwcd.circuitbuilder;

import fwcd.circuitbuilder.view.CircuitBuilderFrame;

public class CircuitBuilderMain {
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		new CircuitBuilderFrame("CircuitBuilder", 900, 700);
	}
}
