package fwcd.circuitbuilder.model.logic.expression;

public class EvaluationException extends RuntimeException {
	private static final long serialVersionUID = 6035189880864109818L;
	
	public EvaluationException(String evaluationMessage) {
		super(evaluationMessage);
	}
}
