package com.fwcd.circuitbuilder.model.cable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitItemVisitor;
import com.fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import com.fwcd.circuitbuilder.utils.Direction;
import com.fwcd.fructose.Option;
import com.fwcd.fructose.StreamUtils;

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
	
	@Override
	public void accept(CircuitItemVisitor visitor) { visitor.visitCable(this); }
}
