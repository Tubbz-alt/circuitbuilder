package fwcd.circuitbuilder.model.logic.circuit;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.Equivalence;
import fwcd.circuitbuilder.model.logic.expression.ExclusiveDisjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;
import fwcd.circuitbuilder.model.utils.BoolUtils;

public class CombinatorialCircuitModelTest {
	private final CombinatorialCircuitModel circuit;
	
	public CombinatorialCircuitModelTest() {
		Map<String, LogicExpression> outputs = new HashMap<>();
		
		// Carry out and sum of a full adder
		outputs.put("Cout", or(and(not(in("Cin")), and(in("A"), in("B"))), and(in("Cin"), or(in("A"), in("B")))));
		outputs.put("S", or(and(not(in("Cin")), xor(in("A"), in("B"))), and(in("Cin"), eqv(in("A"), in("B")))));
		
		circuit = new CombinatorialCircuitModel(outputs);
	}
	
	@Test
	public void testCombinatorialCircuit() {
		assertEquals(0, addBits(0, 0, 0));
		assertEquals(1, addBits(0, 0, 1));
		assertEquals(1, addBits(0, 1, 0));
		assertEquals(2, addBits(0, 1, 1));
		assertEquals(1, addBits(1, 0, 0));
		assertEquals(2, addBits(1, 0, 1));
		assertEquals(2, addBits(1, 1, 0));
		assertEquals(3, addBits(1, 1, 1));
	}
	
	private int addBits(int a, int b, int c) {
		Map<String, Boolean> output = circuit.evaluation().with("A", a).with("B", b).with("Cin", c).run();
		return BoolUtils.toBit(output.get("S")) + (2 * BoolUtils.toBit(output.get("Cout")));
	}
	
	private LogicExpression and(LogicExpression a, LogicExpression b) { return new Conjunction(a, b); }
	
	private LogicExpression or(LogicExpression a, LogicExpression b) { return new Disjunction(a, b); }
	
	private LogicExpression xor(LogicExpression a, LogicExpression b) { return new ExclusiveDisjunction(a, b); }
	
	private LogicExpression eqv(LogicExpression a, LogicExpression b) { return new Equivalence(a, b); }
	
	private LogicExpression not(LogicExpression x) { return new Negation(x); }
	
	private LogicExpression in(String variableName) { return new LogicVariable(variableName); }
}
