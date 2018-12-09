package fwcd.circuitbuilder.model.logic.parse;

import fwcd.fructose.Option;

public class ParseTreeNode {
	private final ParseToken token;
	private final Option<ParseTreeNode> lhs;
	private final Option<ParseTreeNode> rhs;
	
	private ParseTreeNode(ParseToken token, Option<ParseTreeNode> lhs, Option<ParseTreeNode> rhs) {
		this.token = token;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public static ParseTreeNode of(ParseToken token, Option<ParseTreeNode> lhs, Option<ParseTreeNode> rhs) {
		return new ParseTreeNode(token, lhs, rhs);
	}
	
	public static ParseTreeNode of(ParseToken token, ParseTreeNode lhs, ParseTreeNode rhs) {
		return new ParseTreeNode(token, Option.of(lhs), Option.of(rhs));
	}
	
	public static ParseTreeNode ofLeaf(ParseToken token) {
		return new ParseTreeNode(token, Option.empty(), Option.empty());
	}
	
	public Option<ParseTreeNode> getLhs() { return lhs; }
	
	public Option<ParseTreeNode> getRhs() { return rhs; }
	
	public ParseToken getToken() { return token; }
	
	public boolean isLeaf() { return !lhs.isPresent() && !rhs.isPresent(); }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != getClass()) return false;
		ParseTreeNode other = (ParseTreeNode) obj;
		return other.lhs.equals(lhs)
			&& other.rhs.equals(rhs)
			&& other.token.equals(token);
	}
	
	@Override
	public int hashCode() {
		return lhs.hashCode() * rhs.hashCode() * token.hashCode();
	}
	
	@Override
	public String toString() {
		return isLeaf()
			? token.getValue()
			: "("
				+ lhs.map(ParseTreeNode::toString).orElse("?")
				+ " "
				+ token
				+ " "
				+ rhs.map(ParseTreeNode::toString).orElse("?")
				+ ")";
	}
}
