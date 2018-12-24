package fwcd.circuitbuilder.view.grid.theme.json;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.InvalidPathException;
import java.util.Map;

import javax.imageio.ImageIO;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.InputComponentModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.model.grid.components.LampModel;
import fwcd.circuitbuilder.model.grid.components.LeverModel;
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
	
	public JsonItemImageProvider(String baseResourcePath, Map<String, String> fileMap) {
		this.baseResourcePath = baseResourcePath;
		this.fileMap = fileMap;
	}
	
	private Option<Image> imageFromJsonKey(String key) {
		try {
			String path = baseResourcePath + File.separator + fileMap.get(key);
			try (InputStream stream = JsonItemImageProvider.class.getResourceAsStream(path)) {
				return Option.of(ImageIO.read(stream));
			}
		} catch (InvalidPathException | IOException e) {
			return Option.empty();
		}
	}
	
	@Override
	public Option<Image> visitCable(CableModel cable) {
		return imageFromJsonKey("cable");
	}
	
	@Override
	public Option<Image> visitInputComponent(InputComponentModel input) {
		return imageFromJsonKey("input");
	}
	
	@Override
	public Option<Image> visitInverter(InverterModel inverter) {
		return imageFromJsonKey(inverter.isPowered() ? "inverterOn" : "inverterOff");
	}
	
	@Override
	public Option<Image> visitLamp(LampModel lamp) {
		return imageFromJsonKey(lamp.isPowered() ? "lampOn" : "lampOff");
	}
	
	@Override
	public Option<Image> visitLever(LeverModel lever) {
		return imageFromJsonKey(lever.isPowered() ? "leverOn" : "leverOff");
	}
	
	@Override
	public Option<Image> visitOutputComponent(OutputComponentModel output) {
		return imageFromJsonKey("output");
	}
	
	@Override
	public Option<Image> visitTickingClock(TickingClockModel clock) {
		return imageFromJsonKey(clock.isPowered() ? "clockOn" : "clockOff");
	}
	
	@Override
	public Option<Image> visitXor(XorModel xor) {
		return imageFromJsonKey("xor");
	}
	
	@Override
	public Option<Image> visitItem(CircuitItemModel item) {
		return Option.empty();
	}
}
