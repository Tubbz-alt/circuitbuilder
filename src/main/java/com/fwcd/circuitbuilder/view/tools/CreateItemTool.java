package com.fwcd.circuitbuilder.view.tools;

import java.awt.Image;
import java.util.function.Supplier;

import com.fwcd.circuitbuilder.model.CircuitItemModel;
import com.fwcd.circuitbuilder.view.CircuitItemImageProvider;
import com.fwcd.fructose.Option;

/**
 * An abstract tool used to create items.
 */
public class CreateItemTool<T extends CircuitItemModel> implements CircuitTool {
	private final Supplier<T> factory;
	private Option<Image> image = Option.empty();
	
	public CreateItemTool(Supplier<T> factory) {
		this.factory = factory;
		createItem().accept(new CircuitItemImageProvider(newImg -> image = Option.of(newImg)));
	}
	
	@Override
	public Option<Image> getImage() { return image; }
	
	protected T createItem() { return factory.get(); }
}