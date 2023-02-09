package com.unishop.marketplace.rewards.rules;

/**
 * A rule setting for order counts to act on Nth orders. This is backed by modulo "%" expression
 */
public class OrderCountRule implements Rule<Integer> {

    private ExpressionEvaluator<Integer> ruleEvaluator = new ExpressionEvaluator<>();
    private NumberPredicate expression;
    private RuleType option;
    private ExpressionType expressionType = ExpressionType.NUMBER;

    public OrderCountRule (NumberPredicate expression, RuleType option){
        this.option = option;
        this.expression = expression;
    }

    @Override
    public RuleType ruleType() {
        return this.option;
    }

    public NumberPredicate expression() {
        return expression;
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    @Override
    public boolean evaluate(RuleParameter<Integer> param) {
        return ruleEvaluator.evaluate(this, param);
    }

}
