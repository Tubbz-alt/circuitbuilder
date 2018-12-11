package fwcd.circuitbuilder.view.utils;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@FunctionalInterface
public interface DocumentChangeListener extends DocumentListener {
	void anyUpdate(DocumentEvent e);
	
	@Override
	default void changedUpdate(DocumentEvent e) { anyUpdate(e); }
	
	@Override
	default void insertUpdate(DocumentEvent e) { anyUpdate(e); }
	
	@Override
	default void removeUpdate(DocumentEvent e) { anyUpdate(e); }
}
