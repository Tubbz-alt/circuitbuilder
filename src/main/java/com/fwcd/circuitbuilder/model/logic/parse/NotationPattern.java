package com.fwcd.circuitbuilder.model.logic.parse;

/**
 * A pattern used by a parser.
 */
public class NotationPattern {
	private final String value;
	private final PatternPosition position;
	private final int precedence;
	private final Associativity associativity;
	
	public NotationPattern(String value, PatternPosition position, int precedence, Associativity associativity) {
		this.value = value;
		this.position = position;
		this.precedence = precedence;
		this.associativity = associativity;
	}
	
	public PatternPosition getPosition() { return position; }
	
	public String getValue() { return value; }
	
	public int getPrecedence() { return precedence; }
	
	public Associativity getAssociativity() { return associativity; }
}
