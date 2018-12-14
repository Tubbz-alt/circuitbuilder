package fwcd.circuitbuilder.model.logic.circuit;

import java.util.Map;
import java.util.function.UnaryOperator;

import fwcd.circuitbuilder.model.utils.BoolUtils;
import fwcd.circuitbuilder.model.utils.MapBuilder;

public class CircuitEvaluationBuilder {
	private final UnaryOperator<Map<String, Boolean>> evaluator;
	private final MapBuilder<String, Boolean> input = new MapBuilder<>();
	private boolean evaluated = false;
	
	public CircuitEvaluationBuilder(UnaryOperator<Map<String, Boolean>> evaluator) {
		this.evaluator = evaluator;
	}
	
	private void assertNotEvaluated() {
		if (evaluated) {
			throw new IllegalStateException("Can't call method on CircuitEvaluationBuilder after evaluation");
		}
	}
	
	public CircuitEvaluationBuilder with(String inputName, boolean value) {
		assertNotEvaluated();
		input.with(inputName, value);
		return this;
	}
	
	public CircuitEvaluationBuilder with(String inputName, int value) {
		assertNotEvaluated();
		input.with(inputName, BoolUtils.toBoolean(value));
		return this;
	}
	
	public Map<String, Boolean> run() {
		assertNotEvaluated();
		evaluated = true;
		return evaluator.apply(input.build());
	}
}
