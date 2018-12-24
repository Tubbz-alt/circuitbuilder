package fwcd.circuitbuilder.view.grid.theme.json;

import java.util.Map;

/**
 * The raw Java representation of the top-level
 * theme list JSON.
 */
public class JsonThemesData {
	private Map<String, JsonThemeInfoData> themes;
	private String defaultTheme;
	
	protected JsonThemesData() {}
	
	public Map<String, JsonThemeInfoData> getThemes() { return themes; }
	
	public String getDefaultTheme() { return defaultTheme; }
}
