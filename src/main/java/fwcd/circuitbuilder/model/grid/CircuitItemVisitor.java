package fwcd.circuitbuilder.model.grid;

import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.AndModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.grid.components.CircuitLargeComponentModel;
import fwcd.circuitbuilder.model.grid.components.EqvModel;
import fwcd.circuitbuilder.model.grid.components.InputComponentModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.model.grid.components.LampModel;
import fwcd.circuitbuilder.model.grid.components.LeverModel;
import fwcd.circuitbuilder.model.grid.components.NandModel;
import fwcd.circuitbuilder.model.grid.components.NorModel;
import fwcd.circuitbuilder.model.grid.components.OrModel;
import fwcd.circuitbuilder.model.grid.components.OutputComponentModel;
import fwcd.circuitbuilder.model.grid.components.TickingClockModel;
import fwcd.circuitbuilder.model.grid.components.XorModel;

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
	
	default T visitEqv(EqvModel eqv) { return visitLargeComponent(eqv); }
	
	default T visitAnd(AndModel and) { return visitLargeComponent(and); }
	
	default T visitOr(OrModel or) { return visitLargeComponent(or); }
	
	default T visitNand(NandModel nand) { return visitLargeComponent(nand); }
	
	default T visitNor(NorModel nor) { return visitLargeComponent(nor); }
}
