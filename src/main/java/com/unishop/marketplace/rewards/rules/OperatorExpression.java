package com.unishop.marketplace.rewards.rules;

/**
 * This is to support evaluation of expressions based on the type of operands.
 * Say number expressions for INT will support operators of >, <, + or %
 * if we want to introduce strings also we will be able to do by matching operators like contains, equals or regex etc
 * @param <T>
 */
public interface OperatorExpression<T> {
    enum ExpressionValueType {INT, STR}
    T value();
    String operator();
    ExpressionValueType valueType();
}
