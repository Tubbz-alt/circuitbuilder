package fwcd.circuitbuilder.view.logic.formula;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fwcd.circuitbuilder.model.logic.notation.LogicNotation;
import fwcd.circuitbuilder.model.logic.notation.OperatorPattern;
import fwcd.palm.model.TextRange;
import fwcd.palm.model.editor.mods.highlighting.SyntaxHighlighter;
import fwcd.palm.model.editor.mods.highlighting.TextStyler;
import fwcd.palm.view.theme.SyntaxElement;

public class FormulaSyntaxHighlighter implements SyntaxHighlighter {
	private final Pattern regex;
	
	public FormulaSyntaxHighlighter(LogicNotation notation) {
		regex = constructRegex(notation);
	}
	
	private Pattern constructRegex(LogicNotation notation) {
		return Pattern.compile(notation.getPatterns().stream()
			.map(OperatorPattern::getValue)
			.reduce((a, b) -> a + '|' + b)
			.orElse(""));
	}
	
	@Override
	public void performHighlighting(String text, TextStyler styler) {
		Matcher matcher = regex.matcher(text);
		while (matcher.find()) {
			int start = matcher.start();
			int length = matcher.end() - start;
			styler.colorize(new TextRange(start, length), SyntaxElement.KEYWORD);
		}
	}
}
