package fwcd.circuitbuilder.model.grid.components;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.utils.RelativePos;

/**
 * A basic large component implementation that
 * uses a fixed number of inputs/outputs
 * and a boolean function to route a signal.
 */
public abstract class BasicLargeComponent implements CircuitLargeComponentModel {
	private final List<InputComponentModel> inputs;
	private final List<OutputComponentModel> outputs;
	
	private final int outputYOffset;
	private final int rows;
	private final int cols;
	
	public BasicLargeComponent(int inputCount, int outputCount) {
		Collection<RelativePos> ioPositions = Stream.concat(
			IntStream.range(0, inputCount).mapToObj(this::getInputPosition),
			IntStream.range(0, outputCount).mapToObj(this::getOutputPosition)
		).collect(Collectors.toList());
		RelativePos topLeft = ioPositions.stream()
			.reduce(RelativePos::min)
			.orElse(new RelativePos(0, 0));
		RelativePos bottomRight = ioPositions.stream()
			.reduce(RelativePos::max)
			.orElse(new RelativePos(0, 0));
		
		rows = (bottomRight.getY() - topLeft.getY()) + 1;
		cols = (bottomRight.getX() - topLeft.getX()) + 1;
		
		// The vertical offset needed to center the output cells
		outputYOffset = ((2 * Math.max(inputCount, outputCount) - 1) - outputCount) / 2;
		
		inputs = IntStream.range(0, inputCount)
			.mapToObj(i -> new InputComponentModel(getInputPosition(i), Direction.LEFT))
			.collect(Collectors.toList());
		
		outputs = IntStream.range(0, outputCount)
			.mapToObj(i -> new OutputComponentModel(getOutputPosition(i)))
			.collect(Collectors.toList());
	}
	
	/**
	 * Fetches the relative position delta for an
	 * input component index. This method is required
	 * to be idempotent.
	 */
	protected RelativePos getInputPosition(int index) {
		return new RelativePos(0, index * 2);
	}
	
	/**
	 * Fetches the relative position delta for an
	 * output component index. This method is required
	 * to be idempotent.
	 */
	protected RelativePos getOutputPosition(int index) {
		return new RelativePos(1, index * 2 + outputYOffset);
	}
	
	protected abstract boolean[] compute(boolean... inputs);
	
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
