package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Image;
import java.util.Objects;
import java.util.function.Supplier;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.view.grid.CircuitItemImageProvider;
import fwcd.fructose.Option;

/**
 * An abstract tool used to create items.
 */
public class CreateItemTool<T extends CircuitItemModel> implements CircuitTool {
	private final Supplier<T> factory;
	private final T sample;
	private final Option<Image> image;
	
	public CreateItemTool(Supplier<T> factory) {
		this.factory = Objects.requireNonNull(factory, "Tried to construct a CreateItemTool with a 'null' factory");
		sample = factory.get();
		image = sample.accept(new CircuitItemImageProvider(true /* alwaysUsePoweredImage */));
	}
	
	@Override
	public String getName() { return sample.getName(); }
	
	@Override
	public Option<Image> getImage() { return image; }
	
	protected T createItem() { return factory.get(); }
}
