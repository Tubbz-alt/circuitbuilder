package fwcd.circuitbuilder.model.grid.components;

public abstract class ClockControlledFlipFlop extends BasicLargeComponent {
	private boolean inverted = false;
	
	public ClockControlledFlipFlop(int inputCount, int outputCount) {
		super(inputCount, outputCount);
	}
	
	@Override
	public boolean toggle() {
		inverted = !inverted;
		return true;
	}
	
	protected boolean applyInversion(boolean signal) {
		return signal ^ inverted;
	}
	
	public boolean isInverted() { return inverted; }
}
