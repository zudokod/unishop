package com.unishop.marketplace.rewards.rules;

import java.util.List;

public interface Rule<T>{

    /***
     * Note that I sued types to route on switch conditions as well. The UI will provide the matching type code as well
     * as the rule definitions are predefined
     */
    enum RuleType {ORDER_COUNT}
    enum DiscountType {ORDER_AMOUNT_PERCENT}
    enum ExpressionType {NUMBER, STRING}
    record RuleRequest(List<RuleConfig> orderRules, Discount discount) {}
    record RuleConfig(RuleType ruleType, String expressionValue, String operator) {}
    record Discount(String discountCode, DiscountType discountType, int percentage) {}
    RuleType ruleType();
    OperatorExpression<T> expression();
    ExpressionType getExpressionType();
    boolean evaluate(RuleParameter<T> param);

}
