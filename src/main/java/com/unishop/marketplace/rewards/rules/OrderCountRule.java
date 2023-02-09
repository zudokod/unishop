package com.unishop.marketplace.rewards.rules;

public class OrderCountRule implements Rule<Integer> {

    private ExpressionEvaluator<Integer> ruleEvaluator = new ExpressionEvaluator<>();
    private NumberPredicate expression;
    private RuleType option;
    private ExpressionType expressionType = ExpressionType.NUMBER;

    public OrderCountRule (NumberPredicate expression, RuleType option){
        this.option = option;
       // this.param = param;
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
        int currentOrderCount = param.value();
        //if (currentOrderCount % expression.value() == 0) return true;
        return ruleEvaluator.evaluate(this, param);
    }

}
