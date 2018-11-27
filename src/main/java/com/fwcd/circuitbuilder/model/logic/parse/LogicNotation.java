package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.regex.Pattern;

/**
 * Defines a notation for logic expressions.
 * Examples include the mathematical (^, v)
 * and the algebraic notation (+, *).
 */
public interface LogicNotation {
	/**
	 * A pattern that captures two groups
	 * that represent the (raw) AND operands.
	 */
	Pattern getConjunction();
	
	/**
	 * A pattern that captures two groups
	 * that represent the (raw) OR operands.
	 */
	Pattern getDisjunction();
	
	/**
	 * A pattern that captures one group
	 * that represents the (raw) NOT operand.
	 */
	Pattern getNegation();
	
	/**
	 * A pattern that captures two groups
	 * that represent the premise and the conclusion.
	 */
	Pattern getImplication();
	
	/**
	 * A pattern that captures
	 */
	Pattern getEquivalence();
}
