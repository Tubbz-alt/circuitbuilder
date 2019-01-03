package fwcd.circuitbuilder.model.grid.cable;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.fructose.Option;

public class CableMatcher implements CircuitItemVisitor<Option<CableModel>> {
	public static final CableMatcher INSTANCE = new CableMatcher();
	
	private CableMatcher() {}
	
	@Override
	public Option<CableModel> visitItem(CircuitItemModel item) {
		return Option.empty();
	}
	
	@Override
	public Option<CableModel> visitCable(CableModel cable) {
		return Option.of(cable);
	}
}
