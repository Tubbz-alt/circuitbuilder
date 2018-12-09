package fwcd.circuitbuilder.model.logic.parse;

import java.util.List;
import java.util.regex.Pattern;

import fwcd.fructose.Option;

public class ParseUtils {
	private ParseUtils() {}
	
	public static String[] splitWithDelimiter(String delimiter, String str) {
		if (delimiter.length() == 0) {
			return new String[] {str};
		} else {
			String quoted = Pattern.quote(delimiter);
			return str.split("((?<=" + quoted + ")|(?=" + quoted + "))");
		}
	}
	
	/**
	 * Safely gets the nth element from a list.
	 * 
	 * @param list - The list to be processed
	 * @param n - The index of the element to be fetched
	 * @return The element in the list if the index is valid, otherwise {@code Option.empty}
	 */
	public static <T> Option<T> nth(List<? extends T> list, int n) {
		if (n >= 0 && n < list.size()) {
			return Option.of(list.get(n));
		} else {
			return Option.empty();
		}
	}
}
