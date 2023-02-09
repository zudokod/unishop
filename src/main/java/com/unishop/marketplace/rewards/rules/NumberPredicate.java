package com.unishop.marketplace.rewards.rules;

public class NumberPredicate implements OperatorExpression<Integer> {

    private Integer value;
    private String operator;

    public NumberPredicate(Integer value, String operator) {
        this.value = value;
        this.operator = operator;
    }
    public Integer value() {
        return value;
    }
    public ExpressionValueType valueType() {
        return ExpressionValueType.INT;
    }

    public String operator(){
        return this.operator;
    }

}
