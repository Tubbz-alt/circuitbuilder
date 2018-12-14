package fwcd.circuitbuilder.model.logic.karnaugh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.utils.BoolUtils;
import fwcd.circuitbuilder.model.utils.Graycode;

public class KarnaughMapModel {
	private final boolean[][] cells;
	private final List<String> inputVariables;
	private final LogicExpression expression;
	private final int xAxisBitCount;
	private final int yAxisBitCount;
	
	public KarnaughMapModel(LogicExpression expression, List<String> inputVariables) {
		this.inputVariables = inputVariables;
		this.expression = expression;
		
		int inputCount = inputVariables.size();
		xAxisBitCount = inputCount / 2;
		yAxisBitCount = inputCount - xAxisBitCount;
		
		int rowCount = getRowCount();
		int colCount = getColCount();
		cells = new boolean[rowCount][colCount];
		
		for (int y = 0; y < rowCount; y++) {
			for (int x = 0; x < colCount; x++) {
				cells[y][x] = computeCell(x, y);
			}
		}
	}
	
	public IntStream getXAxisCode() { return Graycode.nBits(xAxisBitCount); }
	
	public IntStream getYAxisCode() { return Graycode.nBits(yAxisBitCount); }
	
	public int getCode(int x, int y) { return BoolUtils.concatBinary(Graycode.ofBinary(x), Graycode.ofBinary(y), xAxisBitCount); }
	
	public int getXAxisBitCount() { return xAxisBitCount; }
	
	public int getYAxisBitCount() { return yAxisBitCount; }
	
	public int getRowCount() { return 1 << yAxisBitCount; }
	
	public int getColCount() { return 1 << xAxisBitCount; }
	
	private boolean computeCell(int x, int y) {
		boolean[] inputs = BoolUtils.binaryToBooleans(getCode(x, y), xAxisBitCount + yAxisBitCount);
		Map<String, Boolean> inputMap = new HashMap<>();
		for (int i = 0; i < inputs.length; i++) {
			inputMap.put(inputVariables.get(i), inputs[i]);
		}
		return expression.evaluate(inputMap);
	}
	
	public boolean getCell(int x, int y) {
		return cells[y][x];
	}
	
	public boolean[][] getCells() {
		return cells;
	}
}
