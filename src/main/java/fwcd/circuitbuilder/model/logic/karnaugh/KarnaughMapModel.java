package fwcd.circuitbuilder.model.logic.karnaugh;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.LogicVariableFinder;
import fwcd.circuitbuilder.model.utils.BoolUtils;
import fwcd.circuitbuilder.model.utils.Graycode;

public class KarnaughMapModel {
	private final boolean[][] cells;
	private final List<String> inputVariables;
	private final LogicExpression expression;
	private final int xAxisBitCount;
	private final int yAxisBitCount;
	
	public KarnaughMapModel(LogicExpression expression) {
		this(expression, expression.accept(new LogicVariableFinder())
			.map(LogicVariable::getName)
			.distinct()
			.collect(Collectors.toList()));
	}
	
	public KarnaughMapModel(LogicExpression expression, List<String> inputVariables) {
		this.inputVariables = inputVariables;
		this.expression = expression;
		
		int inputCount = inputVariables.size();
		yAxisBitCount = inputCount / 2;
		xAxisBitCount = inputCount - yAxisBitCount;
		
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
	
	public Stream<String> getXAxisVariables() {
		return IntStream.range(0, xAxisBitCount).mapToObj(inputVariables::get);
	}
	
	public Stream<String> getYAxisVariables() {
		return IntStream.range(0, yAxisBitCount).mapToObj(i -> inputVariables.get(i + xAxisBitCount));
	}
	
	public int getCode(int x, int y) {
		return BoolUtils.concatBinary(Graycode.ofBinary(x), Graycode.ofBinary(y), yAxisBitCount);
	}
	
	public int getXAxisBitCount() { return xAxisBitCount; }
	
	public int getYAxisBitCount() { return yAxisBitCount; }
	
	public int getRowCount() { return 1 << yAxisBitCount; }
	
	public int getColCount() { return 1 << xAxisBitCount; }
	
	private boolean computeCell(int x, int y) {
		boolean[] inputs = BoolUtils.binaryToBooleans(getCode(x, y), inputVariables.size());
		return expression.evaluate(BoolUtils.toMap(inputVariables, inputs));
	}
	
	public boolean getCell(int x, int y) {
		if (y < 0 || y >= cells.length) {
			throw new IndexOutOfBoundsException("Cell y index " + y + " not in bounds [0, " + cells.length + ")");
		} else if (x < 0 || x >= cells[0].length) {
			throw new IndexOutOfBoundsException("Cell x index " + x + " not in bounds [0, " + cells[0].length + ")");
		}
		return cells[y][x];
	}
	
	public boolean[][] getCells() {
		return cells;
	}
}
