package com.fwcd.circuitbuilder.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.eclipse.jdt.annotation.Nullable;

import com.fwcd.circuitbuilder.items.CircuitItem;
import com.fwcd.circuitbuilder.items.components.Cable;
import com.fwcd.circuitbuilder.items.components.CableNetwork;
import com.fwcd.circuitbuilder.items.components.CircuitComponent;
import com.fwcd.circuitbuilder.items.nestedcircuits.NestedCircuit;
import com.fwcd.circuitbuilder.items.nestedcircuits.NestedCircuitInput;
import com.fwcd.circuitbuilder.items.nestedcircuits.NestedCircuitOutput;
import com.fwcd.circuitbuilder.tools.CircuitTool;
import com.fwcd.circuitbuilder.utils.AbsolutePos;
import com.fwcd.circuitbuilder.utils.ConcurrentMultiKeyHashMap;
import com.fwcd.circuitbuilder.utils.Direction;
import com.fwcd.circuitbuilder.utils.Grid;
import com.fwcd.circuitbuilder.utils.MultiKeyMap;
import com.fwcd.circuitbuilder.utils.RelativePos;
import com.fwcd.fructose.swing.RenderPanel;
import com.fwcd.fructose.swing.Rendereable;
import com.fwcd.fructose.swing.Viewable;

public class CircuitGrid implements Viewable, Rendereable, Grid {
	private final JPanel view;
	private final CircuitBuilderApp parent;
	
	private Map<RelativePos, CircuitCell> cells = new ConcurrentHashMap<>();
	private MultiKeyMap<RelativePos, NestedCircuit> nestedCircuits = new ConcurrentMultiKeyHashMap<>();

	@Nullable
	private AbsolutePos mousePos;
	private int mouseButton = 0;
	
	public CircuitGrid(CircuitBuilderApp parent) {
		this.parent = parent;
		view = new RenderPanel(this);
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() != MouseEvent.NOBUTTON) {
					mouseButton = e.getButton();
				}
				
				RelativePos gridPos = toRelativePos(mousePos);
				CircuitItem selectedItem = parent.getSelectedItem();

				if (mouseButton == MouseEvent.BUTTON1 && selectedItem != null) {
					if (selectedItem instanceof CircuitComponent) {
						put(((CircuitComponent) selectedItem).copy(), gridPos);
					} else if (selectedItem instanceof NestedCircuit) {
						put(((NestedCircuit) selectedItem).copy(), gridPos);
					} else if (selectedItem instanceof CircuitTool) {
						((CircuitTool) selectedItem).onLeftClick(getCell(gridPos));
					}
				} else if (mouseButton == MouseEvent.BUTTON3) {
					if (selectedItem != null && selectedItem instanceof CircuitTool) {
						((CircuitTool) selectedItem).onRightClick(getCell(gridPos));
					} else {
						getCell(gridPos).getComponent().ifPresent((component) -> component.onRightClick());
					}
				}
				
				view.repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
				mousePressed(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseButton = 0;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos = new AbsolutePos(e.getX(), e.getY());
				view.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mousePos = null;
				view.repaint();
			}
			
		};
		view.addMouseListener(mouseAdapter);
		view.addMouseMotionListener(mouseAdapter);
		
		// Start threads
		
		new CircuitTicker(this).start();
		new CellCleaner(this).start();
	}
	
	/**
	 * Removes empty cells from the registered cells.<br><br>
	 * 
	 * (The would be re-initialized once they are needed)
	 */
	public void cleanCells() {
		for (RelativePos key : cells.keySet()) {
			if (cells.containsKey(key) && cells.get(key).isEmpty()) {
				cells.remove(key);
			}
		}
	}
	
	public boolean isCellEmpty(RelativePos pos) {
		return cells.containsKey(pos) ? cells.get(pos).isEmpty() : true;
	}
	
	public CircuitCell getCell(RelativePos pos) {
		cells.putIfAbsent(pos, new CircuitCell(this, pos));
		return cells.get(pos);
	}
	
	public Map<Direction, CircuitCell> getNeighbors(RelativePos pos) {
		Map<Direction, CircuitCell> neighbors = new HashMap<>();
		
		for (Direction dir : Direction.values()) {
			neighbors.put(dir, getCell(pos.follow(dir)));
		}
		
		return neighbors;
	}

	public void clearCell(RelativePos pos) {
		if (!isCellEmpty(pos) && !cells.get(pos).containsUnremovableComponents()) {
			cells.remove(pos);
		}
		
		if (nestedCircuits.containsKey(pos)) {
			for (RelativePos subPos : nestedCircuits.getAllMappings(pos)) {
				cells.remove(subPos);
			}
			
			nestedCircuits.removeAllMappings(pos);
		}
	}
	
	public void put(NestedCircuit component, RelativePos pos) {
		for (NestedCircuitInput input : component.getInputs()) {
			RelativePos inputPos = new RelativePos(pos.add(input.getRelativeDeltaPos()));
			put(input, inputPos);
		}

		for (NestedCircuitOutput output : component.getOutputs()) {
			RelativePos outputPos = new RelativePos(pos.add(output.getRelativeDeltaPos()));
			put(output, outputPos);
		}
		
		nestedCircuits.put(pos, component, component.getOccupiedPositions(pos));
	}
	
	public void put(CircuitComponent component, RelativePos pos) {
		if (component instanceof Cable) {
			((Cable) component).setColor(parent.getSelectedColor());
		}
		
		getCell(pos).place(component);
		
		Map<Direction, CircuitCell> neighbors = getNeighbors(pos);
		component.onPlace(neighbors);
		
		for (CircuitCell cell : neighbors.values()) {
			if (!cell.isEmpty()) {
				for (CircuitComponent neighborComponent : cell) {
					neighborComponent.onPlace(getNeighbors(cell.getPos()));
				}
			}
		}
		
		view.repaint();
	}
	
	private void forEachCell(BiConsumer<CircuitCell, CircuitComponent> consumer) {
		for (CircuitCell cell : cells.values()) {
			cell.forEach((component) -> consumer.accept(cell, component));
		}
	}
	
	protected void tick() {
		Set<CableNetwork> networks = new HashSet<>();
		
		// Pre ticking - Grouping of cables using networks
		
		forEachCell((cell, component) -> {
			if (component instanceof Cable) {
				Cable cable = (Cable) component;
				
				if (cable.getNetwork() == null || networks.contains(cable.getNetwork())) {
					CableNetwork network = new CableNetwork(this);
					network.build(cell.getPos());
					
					networks.add(network);
				}
			}
		});
		
		// Main ticking
		
		forEachCell((cell, component) -> component.tick(getNeighbors(cell.getPos())));
		nestedCircuits.values().forEach((nestedCircuit) -> nestedCircuit.tick());
		
		// Updating
		
		forEachCell((cell, component) -> component.update());
		
		view.repaint();
	}
	
	@Override
	public JComponent getView() {
		return view;
	}

	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		g2d.setColor(Color.GRAY);
		
		for (int absX=0; absX<canvasSize.getWidth(); absX+=CircuitItem.UNIT_SIZE) {
			for (int absY=0; absY<canvasSize.getHeight(); absY+=CircuitItem.UNIT_SIZE) {
				g2d.drawRect(absX, absY, CircuitItem.UNIT_SIZE, CircuitItem.UNIT_SIZE);
			}
		}
		
		for (RelativePos cellPos : cells.keySet()) {
			for (CircuitComponent component : cells.get(cellPos)) {
				if (component.isRenderedDirectly()) {
					component.render(g2d, toAbsolutePos(cellPos));
				}
			}
		}
		
		nestedCircuits.forEachMainKey((pos, circuit) -> {
			circuit.render(g2d, toAbsolutePos(pos));
		});
		
		CircuitItem selectedItem = parent.getSelectedItem();
		if (mousePos != null && selectedItem != null) {
			selectedItem.render(g2d, new AbsolutePos(mousePos.sub(CircuitItem.UNIT_SIZE / 2, CircuitItem.UNIT_SIZE / 2)));
		}
	}

	@Override
	public RelativePos toRelativePos(AbsolutePos absolutePos) {
		return new RelativePos(absolutePos.getX() / CircuitItem.UNIT_SIZE, absolutePos.getY() / CircuitItem.UNIT_SIZE);
	}

	@Override
	public AbsolutePos toAbsolutePos(RelativePos relativePos) {
		return new AbsolutePos(relativePos.getX() * CircuitItem.UNIT_SIZE, relativePos.getY() * CircuitItem.UNIT_SIZE);
	}

	public void clear() {
		cells.clear();
		nestedCircuits.clear();
	}
}
