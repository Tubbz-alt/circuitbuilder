package fwcd.circuitbuilder.model.logic.minimize;

import static fwcd.circuitbuilder.model.logic.minimize.MinimizeTestUtils.implicantsOf;
import static fwcd.circuitbuilder.model.logic.minimize.MinimizeTestUtils.mintermsOf;
import static fwcd.circuitbuilder.model.logic.minimize.MinimizeTestUtils.assertEitherEquals;
import static fwcd.circuitbuilder.model.logic.minimize.MinimizeTestUtils.assertSetEquals;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

public class QuineTableTest {
	@Test
	public void testQuineTable() {
		Set<Implicant> primeImplicants = implicantsOf(4, new int[][] {
			{9, 13},
			{13, 15},
			{15, 7},
			{7, 6}
		});
		QuineTable table = new QuineTable(primeImplicants);
		assertSetEquals(mintermsOf(4, 9, 13, 15, 7, 6), table.getMinterms());
		
		Set<Implicant> minimalImplicants = table.findMinimalImplicants();
		assertEitherEquals(
			implicantsOf(4, new int[][] {
				{9, 13},
				{15, 7},
				{7, 6}
			}),
			implicantsOf(4, new int[][] {
				{9, 13},
				{13, 15},
				{7, 6}
			}),
			minimalImplicants
		);
	}
}
