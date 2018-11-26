package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.regex.Pattern;

/**
 * Defines a notation for logic expressions.
 * Examples include the mathematical (^, v)
 * and the algebraic notation (+, *).
 */
public interface LogicNotation {
	Pattern getConjunction();
	
	Pattern getDisjunction();
	
	Pattern getNegation();
	
	Pattern getImplication();
	
	Pattern getEquivalence();
}
