package fwcd.circuitbuilder.model.logic.parse;

import java.util.regex.Pattern;

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
}