package com.fwcd.circuitbuilder.utils;

public enum Direction {
	UP(new RelativePos(0, -1)),
	RIGHT(new RelativePos(1, 0)),
	DOWN(new RelativePos(0, 1)),
	LEFT(new RelativePos(-1, 0));
	
	private RelativePos vector;
	
	private Direction(RelativePos vector) {
		this.vector = vector;
	}
	
	public Direction invert() {
		switch (this) {
			case LEFT: return RIGHT;
			case UP: return DOWN;
			case RIGHT: return LEFT;
			case DOWN: return UP;
			default: throw new RuntimeException("Invalid direction.");
		}
	}
	
	public Direction cycle() {
		return values()[(ordinal() + 1) % values().length];
	}
	
	public RelativePos getVector() {
		return vector;
	}
}
