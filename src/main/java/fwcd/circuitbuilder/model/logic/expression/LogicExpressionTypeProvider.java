package fwcd.circuitbuilder.model.logic.expression;

public class LogicExpressionTypeProvider implements LogicExpressionVisitor<LogicExpressionType> {
	@Override
	public LogicExpressionType visitExpression(LogicExpression expression) {
		throw new IllegalArgumentException("Could not find expression type for " + expression);
	}
	
	@Override
	public LogicExpressionType visitBoolean(LogicBoolean bool) {
		return bool.getBoolValue() ? LogicExpressionType.TRUE : LogicExpressionType.FALSE;
	}
	
	@Override
	public LogicExpressionType visitConjunction(Conjunction conjunction) {
		return LogicExpressionType.CONJUNCTION;
	}
	
	@Override
	public LogicExpressionType visitDisjunction(Disjunction disjunction) {
		return LogicExpressionType.DISJUNCTION;
	}
	
	@Override
	public LogicExpressionType visitImplication(Implication implication) {
		return LogicExpressionType.IMPLICATION;
	}
	
	@Override
	public LogicExpressionType visitEquivalence(Equivalence equivalence) {
		return LogicExpressionType.EQUIVALENCE;
	}
	
	@Override
	public LogicExpressionType visitNegation(Negation negation) {
		return LogicExpressionType.NEGATION;
	}
}
