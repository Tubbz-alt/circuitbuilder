package fwcd.circuitbuilder.view.grid.theme;

import java.util.Map;

/**
 * The raw Java representation of a JSON theme.
 */
public class JsonThemeData {
	private Map<String, String> itemImages;
	
	protected JsonThemeData() {}
	
	public Map<String, String> getItemImages() { return itemImages; }
}
