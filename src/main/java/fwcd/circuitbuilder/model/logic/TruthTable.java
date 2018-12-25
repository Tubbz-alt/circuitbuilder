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
	private final int inputCount;
	
	public TruthTable(LogicExpression expression) {
		List<String> vars = expression.accept(new LogicVariableFinder())
			.map(LogicVariable::getName)
			.collect(Collectors.toList());
		inputCount = vars.size();
		int max = 1 << inputCount;
		outputs = new boolean[max];
		
		for (int i = 0; i < max; i++) {
			outputs[i] = expression.evaluate(BoolUtils.toMap(vars, BoolUtils.binaryToBooleans(i, inputCount)));
		}
	}
	
	public int getInputCount() {
		return inputCount;
	}
	
	public boolean[] getOutputs() {
		return outputs;
	}
	
	public IntStream getBinaryMinterms() {
		return IntStream.range(0, outputs.length)
			.filter(i -> outputs[i]);
	}
}
