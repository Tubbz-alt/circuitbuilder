package com.fredrikw.circuitbuilder.utils;

public interface Grid {
	RelativePos toRelativePos(AbsolutePos absolutePos);
	
	AbsolutePos toAbsolutePos(RelativePos relativePos);
}
