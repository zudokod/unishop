package com.unishop.marketplace.rewards.rules;

public class RuleParameter<T> {

    private T value;

    public RuleParameter(T value){
        this.value = value;
    }

    public T value(){
        return value;
    }


}
