package fwcd.circuitbuilder.model.grid;

import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.AndGateModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.model.grid.components.CircuitComponentModel;
import fwcd.circuitbuilder.model.grid.components.CircuitLargeComponentModel;
import fwcd.circuitbuilder.model.grid.components.ClockModel;
import fwcd.circuitbuilder.model.grid.components.DLatchModel;
import fwcd.circuitbuilder.model.grid.components.DMasterSlaveModel;
import fwcd.circuitbuilder.model.grid.components.DemultiplexerModel;
import fwcd.circuitbuilder.model.grid.components.EqvGateModel;
import fwcd.circuitbuilder.model.grid.components.InputComponentModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.model.grid.components.JkFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.LampModel;
import fwcd.circuitbuilder.model.grid.components.LeverModel;
import fwcd.circuitbuilder.model.grid.components.MultiplexerModel;
import fwcd.circuitbuilder.model.grid.components.NandGateModel;
import fwcd.circuitbuilder.model.grid.components.NorGateModel;
import fwcd.circuitbuilder.model.grid.components.OrGateModel;
import fwcd.circuitbuilder.model.grid.components.OutputComponentModel;
import fwcd.circuitbuilder.model.grid.components.RsFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.RsLatchModel;
import fwcd.circuitbuilder.model.grid.components.RsMasterSlaveModel;
import fwcd.circuitbuilder.model.grid.components.TFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.XorGateModel;

public interface CircuitItemVisitor<T> {
	T visitItem(CircuitItemModel item);
	
	default T visitComponent(CircuitComponentModel component) { return visitItem(component); }
	
	default T visitLargeComponent(CircuitLargeComponentModel largeComponent) { return visitComponent(largeComponent); }
	
	default T visit1x1Component(Circuit1x1ComponentModel component) { return visitComponent(component); }
	
	default T visitInverter(InverterModel inverter) { return visit1x1Component(inverter); }
	
	default T visitLamp(LampModel lamp) { return visit1x1Component(lamp); }
	
	default T visitLever(LeverModel lever) { return visit1x1Component(lever); }
	
	default T visitTickingClock(ClockModel clock) { return visit1x1Component(clock); }
	
	default T visitCable(CableModel cable) { return visit1x1Component(cable); }
	
	default T visitInputComponent(InputComponentModel input) { return visit1x1Component(input); }
	
	default T visitOutputComponent(OutputComponentModel output) { return visit1x1Component(output); }
	
	default T visitXor(XorGateModel xor) { return visitLargeComponent(xor); }
	
	default T visitEqv(EqvGateModel eqv) { return visitLargeComponent(eqv); }
	
	default T visitAnd(AndGateModel and) { return visitLargeComponent(and); }
	
	default T visitOr(OrGateModel or) { return visitLargeComponent(or); }
	
	default T visitNand(NandGateModel nand) { return visitLargeComponent(nand); }
	
	default T visitNor(NorGateModel nor) { return visitLargeComponent(nor); }
	
	default T visitRsFlipFlop(RsFlipFlopModel ff) { return visitLargeComponent(ff); }
	
	default T visitRsLatch(RsLatchModel ff) { return visitLargeComponent(ff); }
	
	default T visitRsMasterSlave(RsMasterSlaveModel ff) { return visitLargeComponent(ff); }
	
	default T visitDLatch(DLatchModel ff) { return visitLargeComponent(ff); }
	
	default T visitDMasterSlave(DMasterSlaveModel ff) { return visitLargeComponent(ff); }
	
	default T visitTFlipFlop(TFlipFlopModel ff) { return visitLargeComponent(ff); }
	
	default T visitJkFlipFlop(JkFlipFlopModel ff) { return visitLargeComponent(ff); }
	
	default T visitMultiplexer(MultiplexerModel mux) { return visitLargeComponent(mux); }
	
	default T visitDemultiplexer(DemultiplexerModel mux) { return visitLargeComponent(mux); }
}
