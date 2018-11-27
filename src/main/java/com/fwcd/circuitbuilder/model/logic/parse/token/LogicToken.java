package com.fwcd.circuitbuilder.model.logic.parse.token;

/**
 * A segment of a raw logic expression.
 */
public class LogicToken {
	private final LogicTokenType type;
	private final String value;
	
	public LogicToken(LogicTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public LogicTokenType getType() { return type; }
	
	public String getValue() { return value; }
}