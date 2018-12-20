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
	
	public TruthTable(LogicExpression expression) {
		List<String> vars = expression.accept(new LogicVariableFinder())
			.map(LogicVariable::getName)
			.collect(Collectors.toList());
		int varCount = vars.size();
		int max = 1 << varCount;
		outputs = new boolean[max];
		
		for (int i = 0; i < max; i++) {
			outputs[i] = expression.evaluate(BoolUtils.toMap(vars, BoolUtils.binaryToBooleans(i, varCount)));
		}
	}
	
	public boolean[] getOutputs() {
		return outputs;
	}
}
