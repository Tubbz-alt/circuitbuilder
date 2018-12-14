package fwcd.circuitbuilder.model.logic.karnaugh;

import java.util.stream.IntStream;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;
import fwcd.circuitbuilder.model.logic.expression.LogicExpressionTypeProvider;
import fwcd.circuitbuilder.model.utils.BoolUtils;
import fwcd.circuitbuilder.model.utils.Graycode;
import fwcd.fructose.exception.TodoException;

public class KarnaughMapModel {
	private final LogicExpression expression;
	private final int xAxisBitCount;
	private final int yAxisBitCount;
	
	public KarnaughMapModel(LogicExpression expression) {
		this.expression = expression;
		LogicExpressionType type = expression.accept(new LogicExpressionTypeProvider());
		int inputCount = type.getInputCount();
		xAxisBitCount = inputCount / 2;
		yAxisBitCount = inputCount - xAxisBitCount;
	}
	
	public IntStream getXAxisCode() { return Graycode.nBits(xAxisBitCount); }
	
	public IntStream getYAxisCode() { return Graycode.nBits(yAxisBitCount); }
	
	public int getCode(int x, int y) { return BoolUtils.concatBinary(Graycode.ofBinary(x), Graycode.ofBinary(y), xAxisBitCount); }
	
	public int getXAxisBitCount() { return xAxisBitCount; }
	
	public int getYAxisBitCount() { return yAxisBitCount; }
	
	public int getRowCount() { return 1 << yAxisBitCount; }
	
	public int getColCount() { return 1 << xAxisBitCount; }
	
	public boolean getCell(int x, int y) {
		boolean[] inputs = BoolUtils.binaryToBooleans(getCode(x, y));
		// TODO: Evaluate expression with inputs
		throw new TodoException();
	}
}
