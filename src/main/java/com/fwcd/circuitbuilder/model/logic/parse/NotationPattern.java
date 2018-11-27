package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.List;

public interface NotationPattern {
	List<String> match(String tested);
}
