package fwcd.circuitbuilder.utils;

import java.util.Iterator;

import fwcd.fructose.Option;

public class CollectionUtils {
	public static <T> Option<T> anyFrom(Iterable<? extends T> iterable) {
		Iterator<? extends T> iterator = iterable.iterator();
		if (iterator.hasNext()) {
			return Option.of(iterator.next());
		} else {
			return Option.empty();
		}
	}
}
