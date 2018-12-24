package fwcd.circuitbuilder.view.grid;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import fwcd.circuitbuilder.model.grid.cable.CableColor;
import fwcd.circuitbuilder.view.grid.theme.CircuitGridTheme;
import fwcd.circuitbuilder.view.grid.theme.json.JsonTheme;
import fwcd.circuitbuilder.view.grid.theme.json.JsonThemesData;
import fwcd.circuitbuilder.view.grid.tools.CircuitTool;
import fwcd.fructose.Observable;
import fwcd.fructose.Option;

/**
 * Contains UI application state associated with the circuit
 * grid builder.
 */
public class CircuitGridContext {
	private static final Gson GSON = new Gson();
	private final Observable<Option<CircuitTool>> selectedTool = new Observable<>(Option.empty());
	private final Observable<CableColor> selectedColor = new Observable<>(CableColor.RED);
	
	private final Map<String, CircuitGridTheme> availableThemes;
	private final Observable<CircuitGridTheme> selectedTheme;
	
	public CircuitGridContext() {
		JsonThemesData rawThemes = GSON.fromJson("/themes/themes.json", JsonThemesData.class);
		availableThemes = rawThemes.getThemes().entrySet().stream()
			.collect(Collectors.toMap(
				entry -> entry.getKey(),
				entry -> new JsonTheme(entry.getValue().getName(), entry.getValue().getPath())
			));
		selectedTheme = new Observable<>(availableThemes.get(rawThemes.getDefaultTheme()));
	}
	
	public Observable<CableColor> getSelectedColor() { return selectedColor; }
	
	public Observable<Option<CircuitTool>> getSelectedTool() { return selectedTool; }
	
	public Observable<CircuitGridTheme> getSelectedTheme() { return selectedTheme; }
	
	public Map<String, CircuitGridTheme> getAvailableThemes() { return availableThemes; }
}
