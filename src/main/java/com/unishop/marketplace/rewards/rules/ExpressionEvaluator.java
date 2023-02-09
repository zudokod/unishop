package com.unishop.marketplace.rewards.rules;

/**
 * A generic way to expression evaluation. For now only supporting numerical expressions
 * @param <T>
 */
public class ExpressionEvaluator<T> {

    /**
     * Evaluate for order parameter conditions
     * @param rule
     * @param orderParameter
     * @return
     */
    public boolean evaluate(Rule<T> rule, RuleParameter<T> orderParameter) {

        switch (rule.getExpressionType()) {
            case NUMBER -> {
               return evaluateNumber(orderParameter.value(), rule.expression().value(), rule.expression().operator());

            }
        }

        return false;

    }

    /**
     * This token evaluation is a naive implementation for pre-known operators supporting numbers
     * @return
     */
    private boolean evaluateNumber(T parameterValue, T ruleValue, String operator){
        switch (operator){
            case ">" : return (Integer) parameterValue > (Integer) ruleValue;
            case "=" : return ((Integer) parameterValue).equals(ruleValue);
            case "%" : return ((Integer) parameterValue % (Integer) ruleValue == 0);
        }

        return false;
    }
}
