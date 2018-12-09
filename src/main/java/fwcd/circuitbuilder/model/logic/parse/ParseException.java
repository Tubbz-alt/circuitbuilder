package fwcd.circuitbuilder.model.logic.parse;

/**
 * An error that occurred while parsing some input.
 * Stores a parse message that is intended to be
 * presented to the user (e.g. through a GUI).
 */
public class ParseException extends RuntimeException {
	private static final long serialVersionUID = -4111784658374658763L;
	private final String parseMessage;
	
	public ParseException(String parseMessage) {
		super(parseMessage);
		this.parseMessage = parseMessage;
	}
	
	public String getParseMessage() { return parseMessage; }
}
