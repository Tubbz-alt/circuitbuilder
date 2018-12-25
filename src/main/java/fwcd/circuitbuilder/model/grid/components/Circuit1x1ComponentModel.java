package fwcd.circuitbuilder.model.grid.components;

import java.util.Map;

import fwcd.circuitbuilder.model.grid.CircuitCellModel;
import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.cable.CableColor;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.fructose.Option;

/**
 * A placeable 1x1 circuit grid component.
 */
public interface Circuit1x1ComponentModel extends CircuitItemModel {
	boolean isPowered();
	
	boolean outputsTowards(Direction outputDir);
	
	/**
	 * Whether this component should be considered
	 * "standalone" (may be false for parts of multi-cell-components).
	 */
	default boolean isAtomic() { return true; }
	
	/**
	 * Whether this component can be "stacked" on
	 * top of other components on the grid.
	 */
	default boolean isStackable() { return false; }
	
	/**
	 * "Toggles" this component in some way. The precise meaning
	 * may depend on the implementation.
	 * 
	 * @return Whether the component could be toggled
	 */
	default boolean toggle() { return false; }
	
	/**
	 * Fetches a color associated with this component.
	 */
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
