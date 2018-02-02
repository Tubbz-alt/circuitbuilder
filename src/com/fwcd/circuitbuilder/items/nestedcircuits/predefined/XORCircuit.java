package com.fwcd.circuitbuilder.items.nestedcircuits.predefined;

import java.awt.Image;

import javax.swing.Icon;

import com.fwcd.circuitbuilder.items.nestedcircuits.NestedCircuit;
import com.fwcd.fructose.swing.ResourceImage;

public class XORCircuit extends PredefinedNestedCircuit {
	private static final Icon ICON = new ResourceImage("/resources/xorIcon.png").getAsIcon();
	private static final Image IMAGE = new ResourceImage("/resources/xorImage.png").get();
	
	public XORCircuit() {
		super(2, 1);
	}

	@Override
	public NestedCircuit copy() {
		return new XORCircuit();
	}

	@Override
	public Icon getIcon() {
		return ICON;
	}

	@Override
	public boolean[] compute(boolean[] inputs) {
		return new boolean[] {inputs[0] ^ inputs[1]};
	}

	@Override
	protected Image fetchMultiCellImage(int correctWidth, int correctHeight) {
		return IMAGE;
	}
}
