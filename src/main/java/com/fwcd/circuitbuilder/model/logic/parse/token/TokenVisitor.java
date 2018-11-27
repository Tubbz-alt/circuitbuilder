package com.fwcd.circuitbuilder.model.logic.parse.token;

public interface TokenVisitor<T> {
	T visitIdentifier(Identifier identifier);
}