package com.fwcd.circuitbuilder.utils;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * A map where each mapping has a main key and
 * optionally multiple subkeys.
 * 
 * @author Fredrik
 *
 */
public interface MultiKeyMap<K, V> {
	@SuppressWarnings("unchecked")
	void put(K mainKey, V value, K... subKeys);
	
	/**
	 * Fetches the set of all main keys.
	 * 
	 * @return The set of all main keys
	 */
	Set<K> mainKeySet();
	
	Collection<V> values();
	
	void forEachMainKey(BiConsumer<? super K, ? super V> consumer);
	
	void forEachKey(BiConsumer<? super K, ? super V> consumer);
	
	/**
	 * Fetches a value from the map.
	 * 
	 * @param key - A main or sub key
	 * @return The associated value
	 * 
	 * @throws NoSuchElementException when the map doesn't contain the item
	 */
	V get(K key);
	
	Set<K> getAllMappings(K key);
	
	/**
	 * Removes all main and sub key associations
	 * of this mapping regardless of whether
	 * a main or sub key is provided.
	 * 
	 * @param key - A main or sub key
	 * @return If the mapping was removed successfully
	 */
	boolean removeAllMappings(K key);
	
	void clear();
	
	boolean containsKey(K key);
	
	boolean containsValue(V value);
	
	boolean containsMainKey(K mainKey);
	
	boolean containsSubKey(K subKey);
}
