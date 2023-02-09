package com.unishop.marketplace.rewards.rules;

/**
 * This is the value which will be satisfied by the rule
 * @param <T>
 */
public class RuleParameter<T> {

    private T value;

    public RuleParameter(T value){
        this.value = value;
    }

    public T value(){
        return value;
    }


}
