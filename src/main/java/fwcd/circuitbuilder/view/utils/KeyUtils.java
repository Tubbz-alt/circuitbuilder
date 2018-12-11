package fwcd.circuitbuilder.view.utils;

import java.awt.event.KeyEvent;

public class KeyUtils {
	public static final int CTRL_OR_META_DOWN_MASK = isMacOS() ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK;
	
	private KeyUtils() {}
	
	private static boolean isMacOS() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}
