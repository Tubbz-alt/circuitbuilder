package fwcd.circuitbuilder.view.grid.theme;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import com.google.gson.Gson;

import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.fructose.Option;

/**
 * A grid theme read from a (resource) JSON-file.
 */
public class JsonTheme implements CircuitGridTheme {
	private static final Gson GSON = new Gson();
	private final JsonThemeData data;
	private final JsonItemImageProvider itemImageProvider;
	
	public JsonTheme(String resourcePath) {
		try (InputStream stream = JsonTheme.class.getResourceAsStream(resourcePath);
			Reader reader = new BufferedReader(new InputStreamReader(stream))) {
			data = GSON.fromJson(reader, JsonThemeData.class);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		itemImageProvider = new JsonItemImageProvider(resourcePath, data.getItemImages());
	}
	
	@Override
	public CircuitItemVisitor<Option<Image>> getItemImageProvider() {
		return itemImageProvider;
	}
}
