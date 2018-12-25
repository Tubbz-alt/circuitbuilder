package fwcd.circuitbuilder.view.grid.tools;

import java.awt.Image;
import java.util.Objects;
import java.util.function.Supplier;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.fructose.Option;

/**
 * An abstract tool used to create items.
 */
public class CreateItemTool<T extends CircuitItemModel> implements CircuitTool {
	private final Supplier<T> factory;
	private final T sample;
	private final boolean useImage;
	private final Option<Image> image;
	
	public CreateItemTool(Supplier<T> factory, CircuitItemVisitor<Option<Image>> imageProvider, boolean useImage) {
		this.factory = Objects.requireNonNull(factory, "Tried to construct a CreateItemTool with a 'null' factory");
		this.useImage = useImage;
		sample = factory.get();
		image = sample.accept(imageProvider);
	}
	
	@Override
	public String getName() { return sample.getName(); }
	
	@Override
	public Option<Image> getImage() { return image; }
	
	@Override
	public boolean useImageButton() { return useImage; }
	
	protected T createItem() { return factory.get(); }
}
