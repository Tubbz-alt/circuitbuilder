package com.fwcd.circuitbuilder.model.components;

import java.util.Map;

import com.fwcd.circuitbuilder.model.CircuitCellModel;
import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.model.cable.CableColor;
import com.fwcd.circuitbuilder.utils.Direction;
import com.fwcd.fructose.Option;

/**
 * A placeable 1x1 circuit grid component.
 */
public interface Circuit1x1ComponentModel extends CircuitItemModel {
	boolean isPowered();
	
	boolean outputsTowards(Direction outputDir);
	
	default boolean isStackable() { return false; }
	
	default void toggle() {}
	
	default Option<CableColor> getColor() { return Option.empty(); }
	
	/**
	 * Will be called once when this item
	 * is placed and once when a neighbor cell item
	 * in one of the four directions is
	 * placed/removed/changed.
	 * 
	 * @param neighbors - The four neighbor cells
	 */
	default void onPlace(Map<Direction, CircuitCellModel> neighbors) {}
	
	/**
	 * Applies all updates from tick().
	 */
	default void update() {}
	
	/**
	 * A method that will be called every "tick".<br><br>
	 * 
	 * <b>NOTE that any updates (especially regarding
	 * output signals through this
	 * method should be saved into a seperate state
	 * that will ONLY be applied when update() is called!!</b>
	 */
	default void tick(Map<Direction, CircuitCellModel> neighbors) {}
	
	default boolean isEmitter() { return true; }
	
	default boolean canBeRemoved() { return true; }
}
