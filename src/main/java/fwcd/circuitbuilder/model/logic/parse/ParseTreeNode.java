package fwcd.circuitbuilder.model.logic.parse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.fructose.Option;
import fwcd.fructose.structs.TreeNode;

public class ParseTreeNode implements TreeNode {
	private final ParseToken token;
	private final Option<ParseTreeNode> lhs;
	private final Option<ParseTreeNode> rhs;
	
	private ParseTreeNode(ParseToken token, Option<ParseTreeNode> lhs, Option<ParseTreeNode> rhs) {
		this.token = token;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	public static ParseTreeNode ofUnary(ParseToken token, ParseTreeNode operand) {
		return new ParseTreeNode(token, Option.of(operand), Option.empty());
	}
	
	public static ParseTreeNode ofBinary(ParseToken token, ParseTreeNode lhs, ParseTreeNode rhs) {
		return new ParseTreeNode(token, Option.of(lhs), Option.of(rhs));
	}
	
	public static ParseTreeNode ofLeaf(ParseToken token) {
		return new ParseTreeNode(token, Option.empty(), Option.empty());
	}
	
	public Option<ParseTreeNode> getOperand() { return lhs; }
	
	public Option<ParseTreeNode> getLhs() { return lhs; }
	
	public Option<ParseTreeNode> getRhs() { return rhs; }
	
	public ParseToken getToken() { return token; }
	
	public boolean isLeaf() { return !lhs.isPresent() && !rhs.isPresent(); }
	
	public boolean isUnary() { return lhs.isPresent() && !rhs.isPresent(); }
	
	public boolean isBinary() { return lhs.isPresent() && rhs.isPresent(); }
	
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
		if (isLeaf()) {
			return token.getValue();
		} else if (isUnary()) {
			return token.toString() + lhs.unwrap().toString();
		} else if (isBinary()) {
			return "(" + lhs.unwrap() + " " + token.getValue() + " " + rhs.unwrap() + ")";
		} else {
			return "?";
		}
	}

	@Override
	public List<? extends TreeNode> getChildren() {
		return Stream.of(lhs, rhs).filter(Option::isPresent).map(Option::unwrap).collect(Collectors.toList());
	}
}
