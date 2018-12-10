package fwcd.circuitbuilder.view.formula;

import java.util.List;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.logic.notation.LogicNotation;
import fwcd.palm.model.editor.mods.completion.CompletionContext;
import fwcd.palm.model.editor.mods.completion.CompletionElement;
import fwcd.palm.model.editor.mods.completion.CompletionProvider;
import fwcd.palm.model.editor.mods.completion.CompletionType;
import fwcd.palm.model.editor.mods.completion.TextCompletion;

public class FormulaCompletionProvider implements CompletionProvider {
	private final LogicNotation notation;
	
	public FormulaCompletionProvider(LogicNotation notation) {
		this.notation = notation;
	}
	
	@Override
	public List<CompletionElement> listCompletions(CompletionContext context) {
		return notation.getPatterns().stream()
			.map(it -> new CompletionElement(CompletionType.METHOD, it.getValue(), it.getExpressionType().toString(), new TextCompletion(context.getNewOffset(), it.getValue())))
			.collect(Collectors.toList());
	}
}
