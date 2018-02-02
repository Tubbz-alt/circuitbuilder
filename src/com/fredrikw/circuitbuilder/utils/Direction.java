package com.fredrikw.circuitbuilder.utils;

import java.awt.geom.AffineTransform;

import com.fredrikw.circuitbuilder.items.CircuitItem;

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
		
		case LEFT:
			return RIGHT;
		case UP:
			return DOWN;
		case RIGHT:
			return LEFT;
		case DOWN:
			return UP;
		default:
			throw new RuntimeException("Invalid direction.");
		
		}
	}
	
	public AffineTransform getTransform(AbsolutePos pos) {
		AffineTransform transform = new AffineTransform();
		transform.translate(pos.getX(), pos.getY());
		
		int halfSize = CircuitItem.UNIT_SIZE / 2;
		
		switch (this) {

		case LEFT:
			transform.rotate(Math.toRadians(-90), halfSize, halfSize);
			break;
		case UP:
			break;
		case RIGHT:
			transform.rotate(Math.toRadians(90), halfSize, halfSize);
			break;
		case DOWN:
			transform.rotate(Math.toRadians(180), halfSize, halfSize);
			break;
		default:
			throw new RuntimeException("Invalid direction.");
		
		}
		
		return transform;
	}
	
	public Direction cycle() {
		return values()[(ordinal() + 1) % values().length];
	}
	
	public RelativePos getVector() {
		return vector;
	}
}
