package fwcd.circuitbuilder.view.grid.theme.json;

import java.util.Map;

/**
 * The raw Java representation of a JSON theme.
 */
public class JsonThemeData {
	private Map<String, String> itemImages;
	private double gridLineOpacity;
	private int cableThickness;
	private boolean cableDots;
	
	protected JsonThemeData() {}
	
	public Map<String, String> getItemImages() { return itemImages; }
	
	public double getGridLineOpacity() { return gridLineOpacity; }
	
	public int getCableThickness() { return cableThickness; }
	
	public boolean drawCableDots() { return cableDots; }
}
