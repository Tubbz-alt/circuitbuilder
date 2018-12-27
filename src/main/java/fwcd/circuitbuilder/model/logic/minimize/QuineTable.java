package fwcd.circuitbuilder.model.logic.minimize;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * A prime implication chart that may be used to elimite redundant prime
 * implicants.
 */
public class QuineTable {
	private final Set<Implicant> primeImplicants;
	private final Map<Minterm, Set<Implicant>> table;
	
	public QuineTable(Set<Implicant> primeImplicants) {
		this.primeImplicants = primeImplicants;
		table = new HashMap<>();
		
		for (Implicant implicant : primeImplicants) {
			for (Minterm minterm : implicant.getMinterms()) {
				if (!table.containsKey(minterm)) {
					table.put(minterm, new HashSet<>());
				}
				table.get(minterm).add(implicant);
			}
		}
	}
	
	Set<Minterm> getMinterms() {
		return table.keySet();
	}
	
	public Set<Implicant> findMinimalImplicants() {
		Set<Minterm> remainingMinterms = new HashSet<>(table.keySet());
		Set<Implicant> remainingImplicants = new HashSet<>(primeImplicants);
		Set<Implicant> minimalImplicants = new HashSet<>();
		
		for (Map.Entry<Minterm, Set<Implicant>> entry : table.entrySet()) {
			if (entry.getValue().size() == 1) {
				// Found dominant minterm and essential prime implicant
				Implicant essentialImplicant = entry.getValue().iterator().next();
				
				minimalImplicants.add(essentialImplicant);
				remainingMinterms.remove(entry.getKey());
				
				// Remove implicants that are covered by the essential implicant
				for (Minterm covered : essentialImplicant.getMinterms()) {
					remainingMinterms.remove(covered);
				}
			}
		}
		
		while (remainingMinterms.size() > 0) {
			// Find best optional prime implicant
			Optional<Implicant> implicant = remainingImplicants.stream()
				.max(compareBy(imp -> intersect(imp.getMinterms(), remainingMinterms)));
			
			implicant.ifPresent(imp -> {
				minimalImplicants.add(imp);
				for (Minterm minterm : imp.getMinterms()) {
					remainingMinterms.remove(minterm);
				}
			});
		}
		
		return minimalImplicants;
	}
	
	private <T, R extends Comparable<R>> Comparator<T> compareBy(Function<T, R> mapper) {
		return (a, b) -> mapper.apply(a).compareTo(mapper.apply(b));
	}
	
	private <T> long intersect(Set<? extends T> a, Set<? extends T> b) {
		return a.stream()
			.filter(b::contains)
			.count();
	}
}
