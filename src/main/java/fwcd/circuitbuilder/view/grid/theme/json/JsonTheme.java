package fwcd.circuitbuilder.view.grid.theme.json;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import com.google.gson.Gson;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.utils.GsonFactory;
import fwcd.circuitbuilder.view.grid.theme.CircuitGridTheme;
import fwcd.fructose.Option;

/**
 * A grid theme read from a (resource) JSON-file.
 */
public class JsonTheme implements CircuitGridTheme {
	private static final Gson GSON = GsonFactory.newGson();
	private final String name;
	private final Color gridLineColor;
	private final JsonItemImageProvider itemImageProvider;
	private final int cableThickness;
	private final boolean cableDots;
	
	public JsonTheme(String name, String resourcePath) {
		this.name = name;
		JsonThemeData data;
		
		try (InputStream stream = JsonTheme.class.getResourceAsStream(resourcePath);
			Reader reader = new BufferedReader(new InputStreamReader(stream))) {
			data = GSON.fromJson(reader, JsonThemeData.class);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		double opacity = data.getGridLineOpacity();
		gridLineColor = new Color(0, 0, 0, (float) opacity);
		itemImageProvider = new JsonItemImageProvider(findParent(resourcePath), data.getItemImages());
		cableThickness = data.getCableThickness();
		cableDots = data.drawCableDots();
	}
	
	private String findParent(String resourcePath) {
		return resourcePath.substring(0, resourcePath.lastIndexOf("/"));
	}
	
	@Override
	public CircuitItemVisitor<Option<Image>> getItemImageProvider() { return itemImageProvider; }
	
	@Override
	public String getName() { return name; }
	
	@Override
	public Color getGridLineColor() { return gridLineColor; }
	
	@Override
	public int getCableThickness() { return cableThickness; }
	
	@Override
	public boolean drawCableDots() { return cableDots; }
}
