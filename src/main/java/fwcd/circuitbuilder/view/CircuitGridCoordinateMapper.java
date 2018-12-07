package fwcd.circuitbuilder.view;

import fwcd.circuitbuilder.utils.AbsolutePos;
import fwcd.circuitbuilder.utils.CoordinateMapper;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * A basic implementation of CoordinateMapper that
 * uses a single scale factor to transform coordinates.
 */
public class CircuitGridCoordinateMapper implements CoordinateMapper {
	private final int unitSize;
	
	public CircuitGridCoordinateMapper(int unitSize) {
		this.unitSize = unitSize;
	}
	
	@Override
	public AbsolutePos toAbsolutePos(RelativePos relativePos) {
		return new AbsolutePos(relativePos.getX() * unitSize, relativePos.getY() * unitSize);
	}
	
	@Override
	public RelativePos toRelativePos(AbsolutePos absolutePos) {
		return new RelativePos(absolutePos.getX() / unitSize, absolutePos.getY() / unitSize);
	}
}