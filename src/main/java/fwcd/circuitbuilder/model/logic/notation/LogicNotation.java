package fwcd.circuitbuilder.model.logic.notation;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Defines a notation for logic expressions.
 * Examples include the mathematical (^, v)
 * and the algebraic notation (+, *).
 */
public interface LogicNotation {
	Collection<? extends OperatorPattern> getPatterns();
	
	default Stream<? extends OperatorPattern> streamBinaryPatterns() {
		return getPatterns().stream().filter(OperatorPattern::isBinary);
	}
	
	default Stream<? extends OperatorPattern> streamUnaryPatterns() {
		return getPatterns().stream().filter(OperatorPattern::isUnary);
	}
}
