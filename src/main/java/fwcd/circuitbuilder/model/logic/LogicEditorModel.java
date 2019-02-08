package fwcd.circuitbuilder.model.logic;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.notation.LogicNotation;
import fwcd.circuitbuilder.model.logic.notation.MathematicalNotation;
import fwcd.circuitbuilder.model.logic.parse.ParseException;
import fwcd.fructose.Observable;
import fwcd.fructose.Option;
import fwcd.fructose.ReadOnlyObservable;

public class LogicEditorModel {
	private final LogicNotation notation = new MathematicalNotation();
	private final LogicExpressionParser parser = new LogicExpressionParser(notation);
	private final Observable<String> rawFormula = new Observable<>("");
	private final Observable<Option<String>> errorMessage = new Observable<>(Option.empty());
	private final Observable<Option<LogicExpression>> expression = new Observable<>(Option.empty());
	
	public LogicEditorModel() {
		rawFormula.listen(raw -> {
			if (raw.isEmpty()) {
				expression.set(Option.empty());
				errorMessage.set(Option.empty());
			} else {
				try {
					expression.set(Option.of(parser.parse(raw)));
					errorMessage.set(Option.empty());
				} catch (ParseException e) {
					errorMessage.set(Option.of(e.getMessage()));
				}
			}
		});
	}
	
	public LogicNotation getNotation() { return notation; }
	
	public Observable<String> getRawFormula() { return rawFormula; }
	
	public ReadOnlyObservable<Option<String>> getErrorMessage() { return errorMessage; }
	
	public ReadOnlyObservable<Option<LogicExpression>> getExpression() { return expression; }
}
