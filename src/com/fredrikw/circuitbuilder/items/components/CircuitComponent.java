package com.fredrikw.circuitbuilder.items.components;

import java.util.Map;

import com.fredrikw.circuitbuilder.core.CircuitCell;
import com.fredrikw.circuitbuilder.items.CircuitItem;
import com.fredrikw.circuitbuilder.utils.Direction;

/**
 * The interface for all placeable 1x1 components on
 * the grid.
 * 
 * @author Fredrik
 *
 */
public interface CircuitComponent extends CircuitItem {
	/**
	 * Will be called once when this item
	 * is placed and once when a neighbor cell item
	 * in one of the four directions is
	 * placed/removed/changed.
	 * 
	 * @param neighbors - The four neighbor cells
	 */
	void onPlace(Map<Direction, CircuitCell> neighbors);
	
	/**
	 * Applies all updates from tick().
	 */
	void update();
	
	/**
	 * @return Whether this component should be rendered directly on the grid (may be false for multi-cell-components)
	 */
	boolean isRenderedDirectly();
	
	/**
	 * A method that will be called every "tick".<br><br>
	 * 
	 * <b>NOTE that any updates (especially regarding
	 * output signals through this
	 * method should be saved into a seperate state
	 * that will ONLY be applied when update() is called!!</b>
	 */
	void tick(Map<Direction, CircuitCell> neighbors);
	
	boolean isPowered();
	
	boolean outputsTowards(Direction outputDir);
	
	void onRightClick();
	
	boolean canBeRemoved();

	/**
	 * Copies this circuit component. In most cases it should
	 * be sufficient to simply provide a new instance of the
	 * implementation class.
	 */
	CircuitComponent copy();
}
