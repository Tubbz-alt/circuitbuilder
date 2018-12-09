package fwcd.circuitbuilder.model.logic.parse;

public class ParseToken {
	public static final ParseToken END = new ParseToken(ParseTokenType.END, "");
	private final String value;
	private final ParseTokenType type;
	
	public ParseToken(ParseTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String getValue() { return value; }
	
	public ParseTokenType getType() { return type; }
	
	@Override
	public String toString() { return (type == ParseTokenType.VALUE) ? ("'" + value + "'") : value; }
}
