package fwcd.circuitbuilder.model.grid.cable;

import fwcd.circuitbuilder.utils.RelativePos;

public class CableEvent {
	private final CableModel cable;
	private final RelativePos pos;
	
	public CableEvent(CableModel cable, RelativePos pos) {
		this.cable = cable;
		this.pos = pos;
	}
	
	public CableModel getCable() { return cable; }
	
	public RelativePos getPos() { return pos; }
}
