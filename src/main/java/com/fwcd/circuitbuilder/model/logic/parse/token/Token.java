package com.fwcd.circuitbuilder.model.logic.parse.token;

public interface Token {
	<T> T accept(TokenVisitor<T> visitor);
}