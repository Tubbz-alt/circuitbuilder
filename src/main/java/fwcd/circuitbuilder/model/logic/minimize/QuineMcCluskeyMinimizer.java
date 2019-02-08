package fwcd.circuitbuilder.model.logic.minimize;

import fwcd.circuitbuilder.model.logic.TruthTable;
import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.transform.ExpressionTransformer;

/**
 * A minimizer that uses the Quine-McCluskey algorithm.
 */
public class QuineMcCluskeyMinimizer implements ExpressionTransformer {
	@Override
	public LogicExpression transform(LogicExpression expression) {
		TruthTable table = new TruthTable(expression);
		McCluskeyColumn mcCluskey = new McCluskeyColumn(table.getInputCount(), table.getBinaryMinterms()).minimize();
		QuineTable quine = new QuineTable(mcCluskey.getPrimeImplicants());
		
		return quine.findMinimalImplicants().stream()
			.map(it -> it.toExpression(table.getInputs()))
			.reduce(Disjunction::new)
			.orElse(LogicBoolean.FALSE);
	}
}
