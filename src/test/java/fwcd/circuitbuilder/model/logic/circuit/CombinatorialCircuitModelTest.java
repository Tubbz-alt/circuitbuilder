package fwcd.circuitbuilder.model.logic.circuit;

import static fwcd.circuitbuilder.model.logic.ExpressionTestUtils.and;
import static fwcd.circuitbuilder.model.logic.ExpressionTestUtils.eqv;
import static fwcd.circuitbuilder.model.logic.ExpressionTestUtils.in;
import static fwcd.circuitbuilder.model.logic.ExpressionTestUtils.not;
import static fwcd.circuitbuilder.model.logic.ExpressionTestUtils.or;
import static fwcd.circuitbuilder.model.logic.ExpressionTestUtils.xor;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
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
}
