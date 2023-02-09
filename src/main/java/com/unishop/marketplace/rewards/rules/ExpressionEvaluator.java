package com.unishop.marketplace.rewards.rules;

public class ExpressionEvaluator<T> {

    public boolean evaluate(Rule<T> rule, RuleParameter<T> orderParameter) {

        switch (rule.getExpressionType()) {
            case NUMBER -> {
               return evaluateNumber(orderParameter.value(), rule.expression().value(), rule.expression().operator());

            }
        }

        return false;

    }

    private boolean evaluateNumber(T parameterValue, T ruleValue, String operator){
        switch (operator){
            case ">" : return (Integer) parameterValue > (Integer) ruleValue;
            case "=" : return ((Integer) parameterValue).equals(ruleValue);
            case "%" : return ((Integer) parameterValue % (Integer) ruleValue == 0);
        }

        return false;
    }
}
