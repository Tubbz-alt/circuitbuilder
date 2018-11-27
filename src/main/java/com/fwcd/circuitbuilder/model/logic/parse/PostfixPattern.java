package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.Collections;
import java.util.List;

/**
 * A parse pattern that matches a postfix
 * and returns the beginning of the string.
 */
public class PostfixPattern implements NotationPattern {
	private final String postfix;
	
	public PostfixPattern(String postfix) {
		this.postfix = postfix;
	}
	
	@Override
	public List<String> match(String tested) {
		if (tested.endsWith(postfix)) {
			return Collections.singletonList(tested.substring(0, tested.length() - postfix.length()));
		} else {
			return Collections.emptyList();
		}
	}
}
