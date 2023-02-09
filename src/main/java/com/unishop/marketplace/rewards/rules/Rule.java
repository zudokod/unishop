package com.unishop.marketplace.rewards.rules;

import java.util.List;

public interface Rule<T>{

    public enum RuleType {ORDER_COUNT}
    public enum DiscountType {ORDER_AMOUNT}
    //public enum LogicType {AND, OR, NOT}
    public enum ExpressionType {NUMBER, STRING}
    public static record RuleRequest(List<RuleConfig> orderRules, Discount discount) {}
    public static record RuleConfig(RuleType ruleType, String expressionValue, String operator) {}
    public static record Discount(String discountCode, DiscountType discountType, int percentage) {}

    public RuleType ruleType();
    public OperatorExpression<T> expression();
    public ExpressionType getExpressionType();
    //public OrderRuleParameter<T> param();
    public boolean evaluate(RuleParameter<T> param);

}
