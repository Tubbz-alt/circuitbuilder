package fwcd.circuitbuilder.utils;

import fwcd.fructose.GridPos;

public class RelativePos extends GridPos {
	public RelativePos(GridPos pos) {
		super(pos.getX(), pos.getY());
	}
	
	public RelativePos(int x, int y) {
		super(x, y);
	}

	public AbsolutePos toAbsolute(CoordinateMapper grid) {
		return grid.toAbsolutePos(this);
	}
	
	public RelativePos follow(Direction direction) {
		return new RelativePos(add(direction.getVector()));
	}
	
	public RelativePos min(RelativePos other) {
		return new RelativePos(Math.min(getX(), other.getX()), Math.min(getY(), other.getY()));
	}
	
	public RelativePos max(RelativePos other) {
		return new RelativePos(Math.max(getX(), other.getX()), Math.max(getY(), other.getY()));
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((RelativePos) obj).getX() == getX()
				&& ((RelativePos) obj).getY() == getY();
	}
	
	@Override
	public int hashCode() {
		return 31 * getX() * getY();
	}
}
