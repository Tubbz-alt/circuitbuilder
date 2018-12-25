package fwcd.circuitbuilder.view.grid.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.NoSuchElementException;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.view.grid.theme.CircuitGridTheme;
import fwcd.fructose.Option;
import fwcd.fructose.Unit;

/**
 * Renders a circuit item to a graphics context.
 */
public class CircuitItemRenderer implements CircuitItemVisitor<Unit> {
	private final Graphics2D g2d;
	private final AbsolutePos pos;
	private final int unitSize;
	private final CableDrawOptions options;
	private final CircuitItemVisitor<Option<Image>> imageProvider;
	
	public CircuitItemRenderer(
		Graphics2D g2d,
		AbsolutePos pos,
		int unitSize,
		CircuitGridTheme theme
	) {
		this.g2d = g2d;
		this.pos = pos;
		this.unitSize = unitSize;
		options = new CableDrawOptions(theme.getCableThickness(), theme.drawCableDots());
		imageProvider = theme.getItemImageProvider();
	}
	
	@Override
	public Unit visitCable(CableModel cable) {
		new CableView(cable, unitSize, options).render(g2d, pos);
		return Unit.UNIT;
	}
	
	@Override
	public Unit visitInverter(InverterModel inverter) {
		g2d.drawImage(imageOf(inverter), getTransform(pos, inverter.getImageFacing()), null);
		return Unit.UNIT;
	}
	
	@Override
	public Unit visitItem(CircuitItemModel item) {
		g2d.drawImage(imageOf(item), pos.getX(), pos.getY(), null);
		return Unit.UNIT;
	}
	
	private AffineTransform getTransform(AbsolutePos pos, Direction direction) {
		AffineTransform transform = new AffineTransform();
		transform.translate(pos.getX(), pos.getY());
		
		int halfSize = unitSize / 2;
		
		switch (direction) {
			case LEFT: transform.rotate(Math.toRadians(-90), halfSize, halfSize); break;
			case UP: break;
			case RIGHT: transform.rotate(Math.toRadians(90), halfSize, halfSize); break;
			case DOWN: transform.rotate(Math.toRadians(180), halfSize, halfSize); break;
			default: throw new RuntimeException("Invalid direction.");
		}
		
		return transform;
	}
	
	private Image imageOf(CircuitItemModel item) {
		Option<Image> image = item.accept(imageProvider);
		if (image.isPresent()) {
			return image.unwrap();
		} else {
			throw new NoSuchElementException("No image registered for " + item.getClass().getSimpleName() + " in CircuitItemImageProvider");
		}
	}
}
