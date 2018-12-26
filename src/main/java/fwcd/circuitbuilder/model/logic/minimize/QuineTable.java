package fwcd.circuitbuilder.model.logic.minimize;

import java.util.Set;

import fwcd.fructose.structs.MapTable;
import fwcd.fructose.structs.Table;

/**
 * A prime implication chart that may be used
 * to elimite redundant prime implicants.
 */
public class QuineTable {
	private final Table<Set<Integer>, Set<Integer>, Boolean> data = new MapTable<>();
	
	public QuineTable() {
		
	}
}
