package fwcd.circuitbuilder.model.logic.parse;

/**
 * An error that occurred while parsing some input.
 */
public class ParseException extends RuntimeException {
	private static final long serialVersionUID = -4111784658374658763L;
	
	public ParseException(String parseMessage) {
		super(parseMessage);
	}
}
