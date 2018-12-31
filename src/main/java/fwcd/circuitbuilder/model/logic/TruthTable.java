package fwcd.circuitbuilder.model.logic;

import java.util.List;
import java.util.stream.Collectors;

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
	private final List<String> inputNames;
	
	public TruthTable(LogicExpression expression) {
		inputNames = expression.accept(new LogicVariableFinder())
			.map(LogicVariable::getName)
			.collect(Collectors.toList());
		int inputsCount = inputNames.size();
		int max = 1 << inputsCount;
		outputs = new boolean[max];
		
		for (int i = 0; i < max; i++) {
			outputs[i] = expression.evaluate(BoolUtils.toMap(inputNames, BoolUtils.binaryToBooleans(i, inputsCount)));
		}
	}
	
	public List<String> getInputNames() {
		return inputNames;
	}
	
	public boolean[] getOutputs() {
		return outputs;
	}
}
