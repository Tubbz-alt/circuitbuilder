package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.Collections;
import java.util.List;

/**
 * A parse pattern that matches a prefix
 * and returns the rest of the string.
 */
public class PrefixPattern implements NotationPattern {
	private final String prefix;
	
	public PrefixPattern(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public List<String> match(String tested) {
		if (tested.startsWith(prefix)) {
			return Collections.singletonList(tested.substring(prefix.length()));
		} else {
			return Collections.emptyList();
		}
	}
}
