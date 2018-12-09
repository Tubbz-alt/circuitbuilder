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
