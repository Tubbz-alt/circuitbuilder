package com.fredrikw.circuitbuilder.items.nestedcircuits.predefined;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.fredrikw.circuitbuilder.items.nestedcircuits.NestedCircuit;
import com.fredrikw.circuitbuilder.items.nestedcircuits.NestedCircuitInput;
import com.fredrikw.circuitbuilder.items.nestedcircuits.NestedCircuitOutput;
import com.fredrikw.circuitbuilder.utils.AbsolutePos;
import com.fredrikw.circuitbuilder.utils.RelativePos;

public abstract class PredefinedNestedCircuit implements NestedCircuit {
	private final NestedCircuitInput[] inputs;
	private final NestedCircuitOutput[] outputs;
	
	private final int relWidth = 2;
	private final int relHeight;
	
	public PredefinedNestedCircuit(int inputs, int outputs) {
		this.inputs = new NestedCircuitInput[inputs];
		this.outputs = new NestedCircuitOutput[outputs];
		
		relHeight = 2 * Math.max(inputs, outputs) - 1;
		
		for (int i=0; i<inputs; i++) {
			this.inputs[i] = new NestedCircuitInput(new RelativePos(0, i * 2));
		}
		
		for (int i=0; i<outputs; i++) {
			this.outputs[i] = new NestedCircuitOutput(new RelativePos(1, i * 2));
		}
	}
	
	@Override
	public void render(Graphics2D g2d, AbsolutePos pos) {
		g2d.drawImage(getMultiCellImage(), pos.getX(), pos.getY(), null);
	}

	@Override
	public NestedCircuitInput[] getInputs() {
		return inputs;
	}

	@Override
	public NestedCircuitOutput[] getOutputs() {
		return outputs;
	}
	
	@Override
	public RelativePos[] getOccupiedPositions(RelativePos basePos) {
		List<RelativePos> positions = new ArrayList<>();
		
		for (int y=0; y<relHeight; y++) {
			for (int x=0; x<relWidth; x++) {
				positions.add(new RelativePos(basePos.add(x, y)));
			}
		}
		
		return positions.toArray(new RelativePos[0]);
	}

	@Override
	public void tick() {
		boolean[] boolInputs = new boolean[inputs.length];
		
		for (int i=0; i<inputs.length; i++) {
			boolInputs[i] = inputs[i].isPowered();
		}
		
		boolean[] boolOutputs = compute(boolInputs);
		
		if (boolOutputs.length != outputs.length) {
			throw new RuntimeException("compute() can't return more output booleans than there are outputs in this nested circuit!");
		}
		
		for (int i=0; i<outputs.length; i++) {
			outputs[i].setEnabled(boolOutputs[i]);
		}
	}
	
	public abstract boolean[] compute(boolean[] inputs);

	@Override
	public Image getMultiCellImage() {
		int correctWidth = relWidth * UNIT_SIZE;
		int correctHeight = relHeight * UNIT_SIZE;
		
		Image image = fetchMultiCellImage(correctWidth, correctHeight);
		
		if (image.getWidth(null) != correctWidth) {
			throw new RuntimeException("This multi cell image must have the width " + Integer.toString(correctWidth));
		} else if (image.getHeight(null) != correctHeight) {
			throw new RuntimeException("This multi cell image must have the height " + Integer.toString(correctHeight));
		}
		
		return image;
	}
	
	/**
	 * Fetches the multi-cell image for this nested circuit. Image size
	 * MUST (!!) be as follows:<br>
	 * 
	 * <li>width = 2 * UNIT_SIZE</li>
	 * <li>height = (2 * Math.max(inputs, outputs) - 1) * UNIT_SIZE</li>
	 * 
	 * @return The grid image for this nested circuit
	 */
	protected abstract Image fetchMultiCellImage(int correctWidth, int correctHeight);
}
