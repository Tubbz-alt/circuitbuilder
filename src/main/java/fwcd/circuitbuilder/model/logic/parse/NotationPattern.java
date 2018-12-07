package fwcd.circuitbuilder.model.logic.parse;

import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;

/**
 * A pattern used by a parser.
 */
public class NotationPattern {
	private final LogicExpressionType type;
	private final String value;
	private final PatternPosition position;
	private final int precedence;
	private final Associativity associativity;
	
	public NotationPattern(
		LogicExpressionType type,
		String value,
		PatternPosition position,
		int precedence,
		Associativity associativity
	) {
		this.type = type;
		this.value = value;
		this.position = position;
		this.precedence = precedence;
		this.associativity = associativity;
	}
	
	public PatternPosition getPosition() { return position; }
	
	public String getValue() { return value; }
	
	public Associativity getAssociativity() { return associativity; }
	
	public int getPrecedence() { return precedence; }
	
	public LogicExpressionType getType() { return type; }
}
