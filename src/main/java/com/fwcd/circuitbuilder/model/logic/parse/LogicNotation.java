package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.Collection;

/**
 * Defines a notation for logic expressions.
 * Examples include the mathematical (^, v)
 * and the algebraic notation (+, *).
 */
public interface LogicNotation {
	Collection<? extends NotationPattern> getPatterns();
}
