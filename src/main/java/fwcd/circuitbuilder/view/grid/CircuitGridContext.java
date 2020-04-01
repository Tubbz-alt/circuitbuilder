package fwcd.circuitbuilder.view.grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import fwcd.circuitbuilder.model.grid.cable.CableColor;
import fwcd.circuitbuilder.model.utils.CircuitBuilderGsonFactory;
import fwcd.circuitbuilder.view.grid.theme.CircuitGridTheme;
import fwcd.circuitbuilder.view.grid.theme.json.JsonTheme;
import fwcd.circuitbuilder.view.grid.theme.json.JsonThemesData;
import fwcd.circuitbuilder.view.grid.tools.CircuitTool;
import fwcd.fructose.Observable;
import fwcd.fructose.Option;

/**
 * Contains UI application state associated with the circuit grid builder.
 */
public class CircuitGridContext {
	private static final Gson GSON = CircuitBuilderGsonFactory.newGson();
	private static final String THEMES_BASE_PATH = "/themes";
	
	private final Observable<Option<CircuitTool>> selectedTool = new Observable<>(Option.empty());
	private final Observable<CableColor> selectedColor = new Observable<>(CableColor.RED);

	private final Map<String, CircuitGridTheme> availableThemes;
	private final Observable<CircuitGridTheme> selectedTheme;
	private final Observable<Boolean> showNetworks = new Observable<>(false);

	public CircuitGridContext() {
		JsonThemesData rawThemes;
		
		try (InputStream stream = CircuitGridContext.class.getResourceAsStream(THEMES_BASE_PATH + "/themes.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			rawThemes = GSON.fromJson(reader, JsonThemesData.class);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		availableThemes = rawThemes.getThemes().entrySet().stream()
			.collect(Collectors.toMap(
				entry -> entry.getKey(),
				entry -> new JsonTheme(entry.getValue().getName(), THEMES_BASE_PATH + "/" + entry.getValue().getPath())
			));
		selectedTheme = new Observable<>(availableThemes.get(rawThemes.getDefaultTheme()));
	}
	
	public Observable<CableColor> getSelectedColor() { return selectedColor; }
	
	public Observable<Option<CircuitTool>> getSelectedTool() { return selectedTool; }
	
	public Observable<CircuitGridTheme> getSelectedTheme() { return selectedTheme; }
	
	public Observable<Boolean> getShowNetworks() { return showNetworks; }
	
	public Map<String, CircuitGridTheme> getAvailableThemes() { return availableThemes; }
}
