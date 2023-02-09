package com.unishop.marketplace.rewards.rules;

public interface OperatorExpression<T> {
    public enum ExpressionValueType {INT, STR}
    public T value();
    public String operator();
    public ExpressionValueType valueType();
}
