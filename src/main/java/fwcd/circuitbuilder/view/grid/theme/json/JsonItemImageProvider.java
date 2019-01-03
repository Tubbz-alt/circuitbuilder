package fwcd.circuitbuilder.view.grid.theme.json;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.AndModel;
import fwcd.circuitbuilder.model.grid.components.EqvModel;
import fwcd.circuitbuilder.model.grid.components.InputComponentModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.model.grid.components.LampModel;
import fwcd.circuitbuilder.model.grid.components.LeverModel;
import fwcd.circuitbuilder.model.grid.components.NandModel;
import fwcd.circuitbuilder.model.grid.components.NorModel;
import fwcd.circuitbuilder.model.grid.components.OrModel;
import fwcd.circuitbuilder.model.grid.components.OutputComponentModel;
import fwcd.circuitbuilder.model.grid.components.TickingClockModel;
import fwcd.circuitbuilder.model.grid.components.XorModel;
import fwcd.fructose.Option;

/**
 * Parses and reads item images from the JSON model.
 */
public class JsonItemImageProvider implements CircuitItemVisitor<Option<Image>> {
	private final String baseResourcePath;
	private final Map<String, String> fileMap;
	private final Map<String, Image> cachedImages = new HashMap<>();
	
	public JsonItemImageProvider(String baseResourcePath, Map<String, String> fileMap) {
		this.baseResourcePath = baseResourcePath;
		this.fileMap = fileMap;
	}
	
	private Image imageFromJsonKey(String key) {
		Image image = cachedImages.get(key);
		if (image == null) {
			try {
				String path = baseResourcePath + File.separator + fileMap.get(key);
				try (InputStream stream = JsonItemImageProvider.class.getResourceAsStream(path)) {
					if (stream == null) {
						throw new IllegalStateException("Missing image resource: " + path);
					}
					image = ImageIO.read(stream);
					cachedImages.put(key, image);
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		return image;
	}
	
	@Override
	public Option<Image> visitCable(CableModel cable) {
		return Option.of(imageFromJsonKey("cable"));
	}
	
	@Override
	public Option<Image> visitInputComponent(InputComponentModel input) {
		return Option.of(imageFromJsonKey("input"));
	}
	
	@Override
	public Option<Image> visitInverter(InverterModel inverter) {
		return Option.of(imageFromJsonKey(inverter.isPowered() ? "inverterOn" : "inverterOff"));
	}
	
	@Override
	public Option<Image> visitLamp(LampModel lamp) {
		return Option.of(imageFromJsonKey(lamp.isPowered() ? "lampOn" : "lampOff"));
	}
	
	@Override
	public Option<Image> visitLever(LeverModel lever) {
		return Option.of(imageFromJsonKey(lever.isPowered() ? "leverOn" : "leverOff"));
	}
	
	@Override
	public Option<Image> visitOutputComponent(OutputComponentModel output) {
		return Option.of(imageFromJsonKey("output"));
	}
	
	@Override
	public Option<Image> visitTickingClock(TickingClockModel clock) {
		return Option.of(imageFromJsonKey(clock.isPowered() ? "clockOn" : "clockOff"));
	}
	
	@Override
	public Option<Image> visitXor(XorModel xor) {
		return Option.of(imageFromJsonKey("xor"));
	}
	
	@Override
	public Option<Image> visitAnd(AndModel and) {
		return Option.of(imageFromJsonKey("and"));
	}
	
	@Override
	public Option<Image> visitEqv(EqvModel eqv) {
		return Option.of(imageFromJsonKey("eqv"));
	}
	
	@Override
	public Option<Image> visitNand(NandModel nand) {
		return Option.of(imageFromJsonKey("nand"));
	}
	
	@Override
	public Option<Image> visitNor(NorModel nor) {
		return Option.of(imageFromJsonKey("nor"));
	}
	
	@Override
	public Option<Image> visitOr(OrModel or) {
		return Option.of(imageFromJsonKey("or"));
	}
	
	@Override
	public Option<Image> visitItem(CircuitItemModel item) {
		return Option.empty();
	}
}
