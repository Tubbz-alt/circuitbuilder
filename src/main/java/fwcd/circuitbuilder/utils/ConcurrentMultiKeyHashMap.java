package fwcd.circuitbuilder.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcurrentMultiKeyHashMap<K, V> implements MultiKeyMap<K, V> {
	private final Map<K, V> mainKeyMap = new ConcurrentHashMap<>();
	private final Map<K, Set<K>> keyLinks = new ConcurrentHashMap<>();
	private final Map<K, V> subKeyMap = new ConcurrentHashMap<>();
	
	@Override
	public final void put(K mainKey, V value, Collection<K> subKeys) {
		mainKeyMap.put(mainKey, value);
		
		if (subKeys.size() > 0) {
			keyLinks.putIfAbsent(mainKey, new HashSet<>());
			Set<K> subKeySet = keyLinks.get(mainKey);
			
			for (K subKey : subKeys) {
				subKeySet.add(subKey);
				subKeyMap.put(subKey, value);
			}
		}
	}

	@Override
	public Set<K> mainKeySet() {
		return mainKeyMap.keySet();
	}

	@Override
	public V get(K key) {
		if (mainKeyMap.containsKey(key)) {
			return mainKeyMap.get(key);
		} else if (subKeyMap.containsKey(key)) {
			return subKeyMap.get(key);
		} else {
			return null;
		}
	}

	@Override
	public boolean removeAllMappings(K key) {
		if (mainKeyMap.containsKey(key)) {
			mainKeyMap.remove(key);
			Set<K> subKeys = keyLinks.remove(key);
			
			if (!subKeys.isEmpty()) {
				for (K subKey : subKeys) {
					subKeyMap.remove(subKey);
				}
			}
			
			return true;
		} else if (subKeyMap.containsKey(key)) {
			for (K mainKey : keyLinks.keySet()) {
				if (keyLinks.get(mainKey).contains(key)) {
					return removeAllMappings(mainKey);
				}
			}
		}
		
		return false;
	}

	@Override
	public Collection<V> values() {
		return mainKeyMap.values();
	}

	@Override
	public void forEachMainKey(BiConsumer<? super K, ? super V> consumer) {
		for (K mainKey : mainKeyMap.keySet()) {
			consumer.accept(mainKey, mainKeyMap.get(mainKey));
		}
	}

	@Override
	public void forEachKey(BiConsumer<? super K, ? super V> consumer) {
		for (K mainKey : mainKeyMap.keySet()) {
			consumer.accept(mainKey, mainKeyMap.get(mainKey));
			
			for (K subKey : keyLinks.get(mainKey)) {
				consumer.accept(subKey, subKeyMap.get(subKey));
			}
		}
	}

	@Override
	public void clear() {
		mainKeyMap.clear();
		keyLinks.clear();
		subKeyMap.clear();
	}

	@Override
	public boolean containsKey(K key) {
		return containsMainKey(key) || containsSubKey(key);
	}

	@Override
	public boolean containsValue(V value) {
		return mainKeyMap.containsValue(value);
	}

	@Override
	public boolean containsMainKey(K mainKey) {
		return mainKeyMap.containsKey(mainKey);
	}

	@Override
	public boolean containsSubKey(K subKey) {
		return subKeyMap.containsKey(subKey);
	}

	@Override
	public Set<K> getSubKeys(K key) {
		Set<K> mappings = new HashSet<>();
		
		if (keyLinks.containsKey(key)) {
			mappings.addAll(keyLinks.get(key));
			return mappings;
		} else {
			for (K mainKey : keyLinks.keySet()) {
				if (keyLinks.get(mainKey).contains(key)) {
					return getSubKeys(mainKey);
				}
			}
			
			return Collections.emptySet();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		forEachMainKey((k, v) -> {
			str.append(Stream.concat(Stream.of(k), getSubKeys(k).stream()).collect(Collectors.toSet())).append(": ").append(v).append(", ");
		});
		return str.delete(str.length() - 2, str.length()).append(']').toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		ConcurrentMultiKeyHashMap<?, ?> other = (ConcurrentMultiKeyHashMap<?, ?>) obj;
		return other.mainKeyMap.equals(mainKeyMap)
			&& other.keyLinks.equals(keyLinks)
			&& other.subKeyMap.equals(subKeyMap);
	}
	
	@Override
	public int hashCode() {
		return 7 * mainKeyMap.hashCode() * keyLinks.hashCode() * subKeyMap.hashCode();
	}
}
