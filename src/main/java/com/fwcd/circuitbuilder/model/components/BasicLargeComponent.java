package com.fwcd.circuitbuilder.model.components;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fwcd.circuitbuilder.utils.RelativePos;

/**
 * A basic large component implementation that
 * uses a fixed number of inputs/outputs
 * and a boolean function to route a signal.
 */
public abstract class BasicLargeComponent implements CircuitLargeComponentModel {
	private final List<InputComponentModel> inputs;
	private final List<OutputComponentModel> outputs;
	
	private final int rows;
	private final int cols = 2;
	
	public BasicLargeComponent(int inputCount, int outputCount) {
		// The total amount of rows needed
		rows = 2 * Math.max(inputCount, outputCount) - 1;
		// The vertical offset needed to center the output cells
		int outputYOffset = (rows / 2) - (outputCount / 2);
		
		inputs = IntStream.range(0, inputCount)
			.mapToObj(i -> new InputComponentModel(new RelativePos(0, i * 2)))
			.collect(Collectors.toList());
		
		outputs = IntStream.range(0, outputCount)
			.mapToObj(i -> new OutputComponentModel(new RelativePos(1, i * 2 + outputYOffset)))
			.collect(Collectors.toList());
	}
	
	protected abstract boolean[] compute(boolean[] inputs);
	
	@Override
	public List<RelativePos> getOccupiedPositions(RelativePos topLeft) {
		return IntStream.range(0, rows)
			.boxed()
			.flatMap(y -> IntStream.range(0, cols)
				.boxed()
				.map(x -> new RelativePos(topLeft.add(x, y))))
			.collect(Collectors.toList());
	}
	
	@Override
	public void tick() {
		int inputsCount = inputs.size();
		int outputsCount = outputs.size();
		boolean[] boolInputs = new boolean[inputsCount];
		
		for (int i = 0; i < inputsCount; i++) {
			boolInputs[i] = inputs.get(i).isPowered();
		}
		
		boolean[] boolOutputs = compute(boolInputs);
		
		if (boolOutputs.length != outputsCount) {
			throw new RuntimeException("compute() can't return more output booleans than there are outputs in this nested circuit!");
		}
		
		for (int i = 0; i < outputsCount; i++) {
			outputs.get(i).setPowered(boolOutputs[i]);
		}
	}
	
	@Override
	public List<InputComponentModel> getInputs() { return inputs; }
	
	@Override
	public List<OutputComponentModel> getOutputs() { return outputs; }
	
	public int getRows() { return rows; }
	
	public int getCols() { return cols; }
}
