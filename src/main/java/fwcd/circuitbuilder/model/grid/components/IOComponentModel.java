package fwcd.circuitbuilder.model.grid.components;

import fwcd.circuitbuilder.utils.RelativePos;

public interface IOComponentModel extends Circuit1x1ComponentModel {
	RelativePos getDeltaPos();
}
