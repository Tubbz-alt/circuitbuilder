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
import fwcd.circuitbuilder.model.grid.components.AndGateModel;
import fwcd.circuitbuilder.model.grid.components.EqvGateModel;
import fwcd.circuitbuilder.model.grid.components.InputComponentModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.model.grid.components.LampModel;
import fwcd.circuitbuilder.model.grid.components.LeverModel;
import fwcd.circuitbuilder.model.grid.components.NandGateModel;
import fwcd.circuitbuilder.model.grid.components.NorGateModel;
import fwcd.circuitbuilder.model.grid.components.OrGateModel;
import fwcd.circuitbuilder.model.grid.components.OutputComponentModel;
import fwcd.circuitbuilder.model.grid.components.RsFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.RsLatchModel;
import fwcd.circuitbuilder.model.grid.components.ClockModel;
import fwcd.circuitbuilder.model.grid.components.XorGateModel;
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
	
	private Option<Image> imageFromJsonKey(String key) {
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
		return Option.of(image);
	}
	
	@Override
	public Option<Image> visitCable(CableModel cable) { return imageFromJsonKey("cable"); }
	
	@Override
	public Option<Image> visitInputComponent(InputComponentModel input) { return imageFromJsonKey("input"); }
	
	@Override
	public Option<Image> visitInverter(InverterModel inverter) { return imageFromJsonKey(inverter.isPowered() ? "inverterOn" : "inverterOff"); }
	
	@Override
	public Option<Image> visitLamp(LampModel lamp) { return imageFromJsonKey(lamp.isPowered() ? "lampOn" : "lampOff"); }
	
	@Override
	public Option<Image> visitLever(LeverModel lever) { return imageFromJsonKey(lever.isPowered() ? "leverOn" : "leverOff"); }
	
	@Override
	public Option<Image> visitOutputComponent(OutputComponentModel output) { return imageFromJsonKey("output"); }
	
	@Override
	public Option<Image> visitTickingClock(ClockModel clock) { return imageFromJsonKey(clock.isPowered() ? "clockOn" : "clockOff"); }
	
	@Override
	public Option<Image> visitXor(XorGateModel xor) { return imageFromJsonKey("xor"); }
	
	@Override
	public Option<Image> visitAnd(AndGateModel and) { return imageFromJsonKey("and"); }
	
	@Override
	public Option<Image> visitEqv(EqvGateModel eqv) { return imageFromJsonKey("eqv"); }
	
	@Override
	public Option<Image> visitNand(NandGateModel nand) { return imageFromJsonKey("nand"); }
	
	@Override
	public Option<Image> visitNor(NorGateModel nor) { return imageFromJsonKey("nor"); }
	
	@Override
	public Option<Image> visitOr(OrGateModel or) { return imageFromJsonKey("or"); }
	
	@Override
	public Option<Image> visitRsFlipFlop(RsFlipFlopModel ff) { return imageFromJsonKey("rsFF"); }
	
	@Override
	public Option<Image> visitRsLatch(RsLatchModel ff) { return imageFromJsonKey("rsLatch"); }
	
	@Override
	public Option<Image> visitItem(CircuitItemModel item) {
		return Option.empty();
	}
}
