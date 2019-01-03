package fwcd.circuitbuilder.model.grid.cable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.fructose.Option;
import fwcd.fructose.StreamUtils;

public class CableModel implements Circuit1x1ComponentModel {
	private final Set<Direction> connections = new HashSet<>();
	
	private CableColor color;
	private boolean soonPowered = false;
	private boolean nowPowered = false;
	private boolean connectedToEmitter = false;
	private Option<CableNetworkStatus> networkStatus = Option.empty();
	
	public CableModel(CableColor color) {
		this.color = color;
	}
	
	public void setNetworkStatus(CableNetworkStatus networkStatus) {
		this.networkStatus = Option.of(networkStatus);
	}
	
	public void clearNetworkStatus() {
		networkStatus = Option.empty();
	}
	
	private boolean canConnectTo(CircuitCellModel cell) {
		return StreamUtils.stream(cell.getComponents())
			.anyMatch(it -> it.getColor().map(c -> c.equals(color)).orElse(true));
	}
	
	@Override
	public String getName() { return "Cable"; }
	
	@Override
	public void onPlace(Map<Direction, CircuitCellModel> neighbors) {
		for (Direction dir : neighbors.keySet()) {
			if (!neighbors.get(dir).isEmpty() && canConnectTo(neighbors.get(dir))) {
				connections.add(dir);
			}
		}
	}
	
	@Override
	public void update() {
		nowPowered = soonPowered;
	}
	
	/**
	 * Fetches the color of this cable. It is always safe
	 * to unwrap the returned {@link Option} if this
	 * method is called on CableModel.
	 */
	@Override
	public Option<CableColor> getColor() { return Option.of(color); }
	
	@Override
	public boolean isPowered() { return nowPowered; }
	
	@Override
	public boolean isEmitter() { return false; }
	
	@Override
	public boolean isStackable() { return true; }
	
	@Override
	public void tick(Map<Direction, CircuitCellModel> neighbors) {
		soonPowered = networkStatus.filter(CableNetworkStatus::isPowered).isPresent();
	}
	
	@Override
	public boolean outputsTowards(Direction outputDir) {
		return connections.contains(outputDir);
	}
	
	boolean isConnectedToEmitter(Map<Direction, CircuitCellModel> neighbors) {
		connectedToEmitter = false;
		
		for (Direction dir : neighbors.keySet()) {
			CircuitCellModel neighborCell = neighbors.get(dir);
			
			for (Circuit1x1ComponentModel component : neighborCell.getComponents()) {
				boolean isPowered = component.isPowered();
				boolean inputsToThis = component.outputsTowards(dir.invert());
				boolean isEmitter = component.isEmitter();
				
				if (isPowered && inputsToThis && isEmitter) {
					connectedToEmitter = true;
				}
			}
		}
		
		return connectedToEmitter;
	}
	
	public Set<? extends Direction> getConnections() { return connections; }
	
	@Override
	public <T> T accept(CircuitItemVisitor<T> visitor) { return visitor.visitCable(this); }
}
