package fwcd.circuitbuilder.model.logic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.LogicVariableFinder;
import fwcd.circuitbuilder.model.utils.BoolUtils;

/**
 * A list of all possible outputs for a given
 * logic (boolean) expression.
 */
public class TruthTable {
	private final boolean[] outputs;
	private final List<String> inputs;
	
	public TruthTable(LogicExpression expression) {
		inputs = expression.accept(new LogicVariableFinder())
			.map(LogicVariable::getName)
			.distinct()
			.collect(Collectors.toList());
		int max = 1 << inputs.size();
		outputs = new boolean[max];
		
		for (int i = 0; i < max; i++) {
			outputs[i] = expression.evaluate(BoolUtils.toMap(inputs, BoolUtils.binaryToBooleans(i, inputs.size())));
		}
	}
	
	public List<String> getInputs() {
		return inputs;
	}
	
	public int getInputCount() {
		return inputs.size();
	}
	
	public boolean[] getOutputs() {
		return outputs;
	}
	
	public IntStream getBinaryMinterms() {
		return IntStream.range(0, outputs.length)
			.filter(i -> outputs[i]);
	}
}
