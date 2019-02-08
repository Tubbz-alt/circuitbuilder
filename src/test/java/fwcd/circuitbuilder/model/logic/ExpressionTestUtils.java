package fwcd.circuitbuilder.model.logic;

import java.util.Arrays;

import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.Equivalence;
import fwcd.circuitbuilder.model.logic.expression.ExclusiveDisjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;

public class ExpressionTestUtils {
	public static LogicExpression and(LogicExpression a, LogicExpression b) { return new Conjunction(a, b); }
	
	public static LogicExpression or(LogicExpression a, LogicExpression b) { return new Disjunction(a, b); }
	
	public static LogicExpression xor(LogicExpression a, LogicExpression b) { return new ExclusiveDisjunction(a, b); }
	
	public static LogicExpression eqv(LogicExpression a, LogicExpression b) { return new Equivalence(a, b); }
	
	public static LogicExpression not(LogicExpression x) { return new Negation(x); }
	
	public static LogicExpression in(String variableName) { return new LogicVariable(variableName); }
	
	public static LogicExpression fromDNF(String... productTerms) {
		return Arrays.stream(productTerms)
			.<LogicExpression>map(conj -> Arrays.stream(conj.split(" "))
				.<LogicExpression>map(v -> {
					if (v.equals("1")) {
						return LogicBoolean.TRUE;
					} else if (v.equals("0")) {
						return LogicBoolean.FALSE;
					} else if (v.startsWith("n")) {
						return new Negation(new LogicVariable(v.substring(1)));
					} else {
						return new LogicVariable(v);
					}
				}).reduce(Conjunction::new)
				.orElse(LogicBoolean.FALSE))
			.reduce((LogicExpression a, LogicExpression b) -> new Disjunction(a, b))
			.orElse(LogicBoolean.TRUE);
	}
}
