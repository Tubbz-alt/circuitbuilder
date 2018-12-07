package fwcd.circuitbuilder.utils;

/**
 * A pair of functions that map absolute
 * positions to relative positions and vice-versa.
 */
public interface CoordinateMapper {
	RelativePos toRelativePos(AbsolutePos absolutePos);
	
	AbsolutePos toAbsolutePos(RelativePos relativePos);
}
