package fwcd.circuitbuilder.model.grid;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import fwcd.circuitbuilder.model.grid.cable.CableEvent;
import fwcd.circuitbuilder.model.utils.GsonFactory;
import fwcd.fructose.EventListenerList;
import fwcd.fructose.ListenerList;

public class CircuitListenableGridModel {
	private static final Gson GSON = GsonFactory.newGson();
    private CircuitGridModel inner = new CircuitGridModel();

	private final ListenerList changeListeners = new ListenerList();
	private final ListenerList clearListeners = new ListenerList();
	private final EventListenerList<CableEvent> addCableListeners = new EventListenerList<>();
    private final EventListenerList<CableEvent> removeCableListeners = new EventListenerList<>();
    
    {
        injectListeners();
    }
    
    public CircuitGridModel getInner() {
        return inner;
    }
    
    public void saveGridTo(Path path) throws IOException {
		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(inner, writer);
		}
	}
	
	public void loadGridFrom(Path path) throws IOException {
		try (Reader reader = Files.newBufferedReader(path)) {
            inner = GSON.fromJson(reader, CircuitGridModel.class);
            injectListeners();
		}
    }
    
    private void injectListeners() {
        inner.setChangeListeners(changeListeners);
        inner.setClearListeners(clearListeners);
        inner.setAddCableListeners(addCableListeners);
        inner.setRemoveCableListeners(removeCableListeners);
    }
    
    public ListenerList getChangeListeners() { return changeListeners; }
    
    public ListenerList getClearListeners() { return clearListeners; }
    
    public EventListenerList<CableEvent> getAddCableListeners() { return addCableListeners; }
    
    public EventListenerList<CableEvent> getRemoveCableListeners() { return removeCableListeners; }
}
