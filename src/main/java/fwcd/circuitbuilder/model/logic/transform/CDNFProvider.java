package fwcd.circuitbuilder.model.logic.transform;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.logic.TruthTable;
import fwcd.circuitbuilder.model.logic.expression.ExpressionUtils;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;
import fwcd.circuitbuilder.model.utils.BoolUtils;

/**
 * Creates a canonical disjunctive normal form from
 * a given expression.
 */
public class CDNFProvider implements ExpressionTransformer {
	@Override
	public LogicExpression transform(LogicExpression expression) {
		TruthTable table = new TruthTable(expression);
		List<String> inputNames = table.getInputNames();
		int bitCount = inputNames.size();
		boolean[] outputs = table.getOutputs();
		Stream.Builder<LogicExpression> disjunction = Stream.builder();
		
		for (int i = 0; i < outputs.length; i++) {
			if (outputs[i]) {
				boolean[] inputs = BoolUtils.binaryToBooleans(i, bitCount);
				Stream<LogicExpression> minterm = IntStream.range(0, bitCount)
					.mapToObj(j -> inputs[j]
						? new LogicVariable(inputNames.get(j))
						: new Negation(new LogicVariable(inputNames.get(j))));
				disjunction.accept(ExpressionUtils.and(minterm));
			}
		}
		
		return ExpressionUtils.or(disjunction.build());
	}
}
