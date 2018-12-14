package fwcd.circuitbuilder.model.logic.circuit;

import java.util.Map;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;

/**
 * A stateless, boolean circuit.
 */
public class CombinatorialCircuitModel {
	public final Map<String, LogicExpression> outputs;
	
	public CombinatorialCircuitModel(Map<String, LogicExpression> outputs) {
		this.outputs = outputs;
	}
	
	public Map<String, Boolean> evaluate(Map<String, Boolean> inputs) {
		return outputs.entrySet().stream()
			.collect(Collectors.toMap(
				entry -> entry.getKey(),
				entry -> entry.getValue().evaluate(inputs)
			));
	}
	
	public CircuitEvaluationBuilder evaluation() {
		return new CircuitEvaluationBuilder(this::evaluate);
	}
}
