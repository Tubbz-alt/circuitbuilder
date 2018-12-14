package fwcd.circuitbuilder.model.logic.parse;

public class ParseToken {
	private final String value;
	private final ParseTokenType type;
	
	public ParseToken(ParseTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String getValue() { return value; }
	
	public ParseTokenType getType() { return type; }
	
	public boolean isOperator() {
		return type == ParseTokenType.UNARY_OPERATOR
			|| type == ParseTokenType.BINARY_OPERATOR;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != getClass()) return false;
		ParseToken other = (ParseToken) obj;
		return other.value.equals(value) && other.type.equals(type);
	}
	
	@Override
	public String toString() { return (type == ParseTokenType.CONSTANT) ? ("'" + value + "'") : value; }
}
