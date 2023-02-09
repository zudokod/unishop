package com.unishop.marketplace.rewards.rules;

import java.util.ArrayList;
import java.util.List;

public class OrderRuleSet {

    public OrderRuleSet(){

    }

    private List<Rule<Integer>> orderRules = new ArrayList<>();
    private Rule.Discount discount;

    public void addOrderRule(Rule<Integer> orderRule){
        this.orderRules.add(orderRule);
    }

    public void setDiscount(Rule.Discount discount){
        this.discount = discount;
    }

    public List<Rule<Integer>> getOrderRules() {
        return orderRules;
    }

    public Rule.Discount getDiscount() {
        return discount;
    }
}
