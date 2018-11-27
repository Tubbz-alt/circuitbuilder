package com.fwcd.circuitbuilder.model.logic.parse.token;

public class Identifier implements Token {
	@Override
	public <T> T accept(TokenVisitor<T> visitor) {
		return visitor.visitIdentifier(this);
	}
}