package com.fwcd.circuitbuilder.utils;

public interface Grid {
	RelativePos toRelativePos(AbsolutePos absolutePos);
	
	AbsolutePos toAbsolutePos(RelativePos relativePos);
}
