package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;

public class RsLatchModel extends BasicLargeComponent {
	private static final int INPUT_COUNT = 3;
	private static final int OUTPUT_COUNT = 2;
	private boolean q = false;
	private boolean qStar = true;
	
	public RsLatchModel() {
		super(INPUT_COUNT, OUTPUT_COUNT);
	}
	
	@Override
	public String getName() { return "RS-FF"; }
	
	@Override
	public String getSymbol() { return "RS"; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitRsLatch(this); }
	
	@Override
	protected boolean[] compute(boolean[] inputs) {
		q = !inputs[1] && !qStar && inputs[0];
		qStar = !inputs[1] && !q && inputs[2];
		return new boolean[] {q, qStar};
	}
}
