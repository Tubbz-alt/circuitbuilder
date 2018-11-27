package fwcd.circuitbuilder.model;

import fwcd.circuitbuilder.model.cable.CableModel;
import fwcd.circuitbuilder.model.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.components.CircuitLargeComponentModel;
import fwcd.circuitbuilder.model.components.InputComponentModel;
import fwcd.circuitbuilder.model.components.InverterModel;
import fwcd.circuitbuilder.model.components.LampModel;
import fwcd.circuitbuilder.model.components.LeverModel;
import fwcd.circuitbuilder.model.components.OutputComponentModel;
import fwcd.circuitbuilder.model.components.TickingClockModel;
import fwcd.circuitbuilder.model.components.XorModel;

public interface CircuitItemVisitor<T> {
	T visitItem(CircuitItemModel item);
	
	default T visitLargeComponent(CircuitLargeComponentModel largeComponent) { return visitItem(largeComponent); }
	
	default T visit1x1Component(Circuit1x1ComponentModel component) { return visitItem(component); }
	
	default T visitInverter(InverterModel inverter) { return visit1x1Component(inverter); }
	
	default T visitLamp(LampModel lamp) { return visit1x1Component(lamp); }
	
	default T visitLever(LeverModel lever) { return visit1x1Component(lever); }
	
	default T visitTickingClock(TickingClockModel clock) { return visit1x1Component(clock); }
	
	default T visitCable(CableModel cable) { return visit1x1Component(cable); }
	
	default T visitInputComponent(InputComponentModel input) { return visit1x1Component(input); }
	
	default T visitOutputComponent(OutputComponentModel output) { return visit1x1Component(output); }
	
	default T visitXor(XorModel xor) { return visitLargeComponent(xor); }
}
