package com.fredrikw.circuitbuilder.utils;

import com.fwcd.fructose.GridPos;

public class RelativePos extends GridPos {
	public RelativePos(GridPos pos) {
		super(pos.getX(), pos.getY());
	}
	
	public RelativePos(int x, int y) {
		super(x, y);
	}

	public AbsolutePos toAbsolute(Grid grid) {
		return grid.toAbsolutePos(this);
	}
	
	public RelativePos follow(Direction direction) {
		return new RelativePos(add(direction.getVector()));
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
