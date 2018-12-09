package fwcd.circuitbuilder.utils;

import java.util.Iterator;

/**
 * An iterable that yields the elements of
 * the first collection and then those of the
 * second collection.
 * 
 * @param <T> - The element type
 */
public class ConcatIterable<T> implements Iterable<T> {
	private final Iterable<? extends T> a;
	private final Iterable<? extends T> b;
	
	public ConcatIterable(Iterable<? extends T> a, Iterable<? extends T> b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private final Iterator<? extends T> first = a.iterator();
			private final Iterator<? extends T> second = b.iterator();
			
			@Override
			public boolean hasNext() {
				return first.hasNext() || second.hasNext();
			}
			
			@Override
			public T next() {
				if (first.hasNext()) {
					return first.next();
				} else {
					return second.next();
				}
			}
		};
	}
}
