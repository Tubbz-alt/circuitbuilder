package com.fwcd.circuitbuilder.items.components;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import com.fwcd.circuitbuilder.core.CircuitCell;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.utils.Direction;
import com.fwcd.fructose.Option;
import com.fwcd.fructose.swing.ResourceImage;

public class Cable extends BasicComponent {
	private static final ResourceImage IMAGE = new ResourceImage("/cable.png");

	private Set<Direction> connectedDirs = new HashSet<>();
	
	private boolean soonPowered = false;
	private boolean nowPowered = false;
	private boolean connectedToEmitter = false;

	private CableColor color = CableColor.RED;
	
	private Option<CableNetwork> network = Option.empty();
	
	@Override
	public Icon getIcon() {
		return IMAGE.getAsIcon();
	}

	private boolean canConnectTo(CircuitCell cell) {
		boolean canConnect = false;
		
		for (CircuitComponent component : cell) {
			if (component instanceof Cable) {
				canConnect |= ((Cable) component).color == color;
			} else {
				return true;
			}
		}
		
		return canConnect;
	}
	
	@Override
	public void onPlace(Map<Direction, CircuitCell> neighbors) {
		for (Direction dir : neighbors.keySet()) {
			if (!neighbors.get(dir).isEmpty() && canConnectTo(neighbors.get(dir))) {
				connectedDirs.add(dir);
			}
		}
	}

	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		final int subSize = UNIT_SIZE / 3;

		int colorStrength = isPowered() ? 255 : 50;
		
		g2d.setColor(color.getAWTColor(colorStrength)); // Signal based color
		g2d.fillRect(pos.getX() + subSize, pos.getY() + subSize, subSize, subSize);
		
		for (Direction connection : connectedDirs) {
			switch (connection) {
			
			case LEFT:
				g2d.fillRect(pos.getX(), pos.getY() + subSize, subSize, subSize);
				break;
			case RIGHT:
				g2d.fillRect(pos.getX() + (subSize * 2), pos.getY() + subSize, subSize, subSize);
				break;
			case DOWN:
				g2d.fillRect(pos.getX() + subSize, pos.getY() + (subSize * 2), subSize, subSize);
				break;
			case UP:
				g2d.fillRect(pos.getX() + subSize, pos.getY(), subSize, subSize);
				break;
			
			}
		}
	}

	@Override
	public CircuitComponent copy() {
		return new Cable();
	}

	@Override
	public void update() {
		nowPowered = soonPowered;
	}
	
	public void setNetwork(CableNetwork network) {
		this.network = Option.of(network);
	}
	
	public Option<CableNetwork> getNetwork() {
		return network;
	}
	
	@Override
	public void tick(Map<Direction, CircuitCell> neighbors) {
		soonPowered = network.filter(CableNetwork::isPowered).isPresent();
	}

	protected boolean isConnectedToEmitter(Map<Direction, CircuitCell> neighbors) {
		connectedToEmitter = false;
		
		for (Direction dir : neighbors.keySet()) {
			CircuitCell neighborCell = neighbors.get(dir);
			
			for (CircuitComponent component : neighborCell) {
				boolean isPowered = component.isPowered();
				boolean inputsToThis = component.outputsTowards(dir.invert());
				boolean isEmitter = !(component instanceof Cable);
				
				if (isPowered && inputsToThis && isEmitter) {
					connectedToEmitter = true;
				}
			}
		}
		
		return connectedToEmitter;
	}
	
	@Override
	public boolean isPowered() {
		return nowPowered;
	}

	@Override
	public boolean outputsTowards(Direction outputDir) {
		return connectedDirs.contains(outputDir) && nowPowered;
	}

	public void setColor(CableColor color) {
		this.color = color;
	}
	
	public CableColor getColor() {
		return color;
	}
}
