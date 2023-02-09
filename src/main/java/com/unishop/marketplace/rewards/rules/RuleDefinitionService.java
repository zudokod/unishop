package com.unishop.marketplace.rewards.rules;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RuleDefinitionService {

    private List<Rule<Integer>> orderRules;
    private OrderRuleSet orderRuleSet;

    public RuleDefinitionService(){
        orderRules = new LinkedList<>();
        orderRuleSet = new OrderRuleSet();
        init();
    }

    private void init() {
        ruleBuilder(createRuleRequest());
    }

    private Rule.RuleRequest createRuleRequest() {
        Rule.RuleConfig ruleConfig = new Rule.RuleConfig(Rule.RuleType.ORDER_COUNT, "2", "%");
        List<Rule.RuleConfig> configs = new ArrayList<>();
        configs.add(ruleConfig);
        Rule.Discount discount = new Rule.Discount("dsc_1", Rule.DiscountType.ORDER_AMOUNT, 10);
        return new Rule.RuleRequest(configs, discount);
    }


    private void ruleBuilder(Rule.RuleRequest request){

        Rule.RuleConfig ruleConfig =  request.orderRules().get(0);
        switch (ruleConfig.ruleType()) {
            case ORDER_COUNT -> orderRuleSet.addOrderRule(createOrderCountRule(request));
        }
        orderRuleSet.setDiscount(request.discount());

    }

    private OrderCountRule createOrderCountRule(Rule.RuleRequest request) {
        Rule.RuleConfig ruleConfig =  request.orderRules().get(0);
        Integer expressionValue = Integer.parseInt(ruleConfig.expressionValue());
        Rule.RuleType ruleType = ruleConfig.ruleType();
        //OrderRuleParameter<Integer> parameter = new OrderRuleParameter<>(ruleValue);
        NumberPredicate operatorExpression = new NumberPredicate(expressionValue, ruleConfig.operator());
        return new OrderCountRule(operatorExpression,  ruleConfig.ruleType());
    }

    public List<Rule<Integer>> getOrderRules() {
        return orderRules;
    }

    public OrderRuleSet getOrderRuleSet() {
        return orderRuleSet;
    }
}
