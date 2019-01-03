package fwcd.circuitbuilder.model.grid.cable;

public class CableNetworkStatus {
	private boolean powered = false;
	
	public void setPowered(boolean powered) { this.powered = powered; }
	
	public boolean isPowered() { return powered; }
	
	@Override
	public String toString() { return "Status: " + (powered ? "powered" : "not powered"); }
}
