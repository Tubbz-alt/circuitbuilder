package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

public class RsFlipFlop extends BasicLargeComponent {
	private static final int INPUT_COUNT = 2;
	private static final int OUTPUT_COUNT = 2;
	private boolean q = false;
	private boolean qStar = true;
	
	public RsFlipFlop() {
		super(INPUT_COUNT, OUTPUT_COUNT);
	}
	
	@Override
	public String getName() { return "RS-FF"; }
	
	@Override
	public String getSymbol() { return "RS"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitRsFlipFlop(this); }
	
	@Override
	protected boolean[] compute(boolean[] inputs) {
		q = !qStar && inputs[0];
		qStar = !q && inputs[1];
		return new boolean[] {q, qStar};
	}
}
