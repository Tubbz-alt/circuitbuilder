package com.fwcd.circuitbuilder.model;

import com.fwcd.circuitbuilder.model.cable.CableModel;
import com.fwcd.circuitbuilder.model.components.InputComponentModel;
import com.fwcd.circuitbuilder.model.components.InverterModel;
import com.fwcd.circuitbuilder.model.components.LampModel;
import com.fwcd.circuitbuilder.model.components.LeverModel;
import com.fwcd.circuitbuilder.model.components.OutputComponentModel;
import com.fwcd.circuitbuilder.model.components.TickingClockModel;
import com.fwcd.circuitbuilder.model.components.XorModel;

public interface CircuitItemVisitor {
	default void visitInverter(InverterModel inverter) {}
	
	default void visitLamp(LampModel lamp) {}
	
	default void visitLever(LeverModel lever) {}
	
	default void visitTickingClock(TickingClockModel clock) {}
	
	default void visitCable(CableModel cable) {}
	
	default void visitInputComponent(InputComponentModel input) {}
	
	default void visitOutputComponent(OutputComponentModel output) {}
	
	default void visitXor(XorModel xor) {}
}
