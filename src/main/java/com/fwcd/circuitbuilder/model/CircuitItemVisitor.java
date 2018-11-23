package com.fwcd.circuitbuilder.model;

import com.fwcd.circuitbuilder.model.cable.CableModel;
import com.fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import com.fwcd.circuitbuilder.model.components.CircuitLargeComponentModel;
import com.fwcd.circuitbuilder.model.components.InputComponentModel;
import com.fwcd.circuitbuilder.model.components.InverterModel;
import com.fwcd.circuitbuilder.model.components.LampModel;
import com.fwcd.circuitbuilder.model.components.LeverModel;
import com.fwcd.circuitbuilder.model.components.OutputComponentModel;
import com.fwcd.circuitbuilder.model.components.TickingClockModel;
import com.fwcd.circuitbuilder.model.components.XorModel;

public interface CircuitItemVisitor {
	default void visitInverter(InverterModel inverter) { visit1x1Component(inverter); }
	
	default void visitLamp(LampModel lamp) { visit1x1Component(lamp); }
	
	default void visitLever(LeverModel lever) { visit1x1Component(lever); }
	
	default void visitTickingClock(TickingClockModel clock) { visit1x1Component(clock); }
	
	default void visitCable(CableModel cable) { visit1x1Component(cable); }
	
	default void visitInputComponent(InputComponentModel input) { visit1x1Component(input); }
	
	default void visitOutputComponent(OutputComponentModel output) { visit1x1Component(output); }
	
	default void visitXor(XorModel xor) { visitLargeComponent(xor); }
	
	default void visitLargeComponent(CircuitLargeComponentModel largeComponent) { visitItem(largeComponent); }
	
	default void visit1x1Component(Circuit1x1ComponentModel component) { visitItem(component); }
	
	default void visitItem(CircuitItemModel item) {}
}
