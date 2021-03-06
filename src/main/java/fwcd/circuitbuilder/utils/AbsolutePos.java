package fwcd.circuitbuilder.utils;

import fwcd.fructose.GridPos;

public class AbsolutePos extends GridPos {
	public AbsolutePos(GridPos pos) {
		super(pos.getX(), pos.getY());
	}
	
	public AbsolutePos(int x, int y) {
		super(x, y);
	}

	public RelativePos toRelative(CoordinateMapper grid) {
		return grid.toRelativePos(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((AbsolutePos) obj).getX() == getX()
				&& ((AbsolutePos) obj).getY() == getY();
	}
	
	@Override
	public int hashCode() {
		return 37 * getX() * getY();
	}
}
