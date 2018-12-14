package fwcd.circuitbuilder.model.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
	private final Map<K, V> mappings = new HashMap<>();
	private boolean mutable = true;
	private boolean built = false;
	
	public void assertNotBuilt() {
		if (built) {
			throw new IllegalStateException("Can't invoke method on MapBuilder after construction");
		}
	}
	
	public MapBuilder<K, V> mutable(boolean mutable) {
		assertNotBuilt();
		this.mutable = mutable;
		return this;
	}
	
	public MapBuilder<K,V> with(K key, V value) {
		assertNotBuilt();
		mappings.put(key, value);
		return this;
	}
	
	public Map<K, V> build() {
		assertNotBuilt();
		built = true;
		if (mutable) {
			return mappings;
		} else {
			return Collections.unmodifiableMap(mappings);
		}
	}
}
