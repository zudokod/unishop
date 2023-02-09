package com.unishop.marketplace.service;

import com.unishop.marketplace.rewards.rules.NumberPredicate;
import com.unishop.marketplace.rewards.rules.OrderCountRule;
import com.unishop.marketplace.rewards.rules.OrderRuleSet;
import com.unishop.marketplace.rewards.rules.Rule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/***
 * This is predefined rule definition. Ideally this should be possible to be defined from UI
 */

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

    /**
     * Instead of creating rule request, one should be able to define from API. For now, keeping like this
     * @return
     */
    private Rule.RuleRequest createRuleRequest() {
        Rule.RuleConfig ruleConfig = new Rule.RuleConfig(Rule.RuleType.ORDER_COUNT, "10", "%");
        List<Rule.RuleConfig> configs = new ArrayList<>();
        configs.add(ruleConfig);
        Rule.Discount discount = new Rule.Discount("DISCOUNT10", Rule.DiscountType.ORDER_AMOUNT_PERCENT, 10);
        return new Rule.RuleRequest(configs, discount);
    }


    private void ruleBuilder(Rule.RuleRequest request){

        Rule.RuleConfig ruleConfig =  request.orderRules().get(0);
        switch (ruleConfig.ruleType()) {
            case ORDER_COUNT -> orderRuleSet.addOrderRule(createOrderCountRule(request));
        }
        orderRuleSet.setDiscount(request.discount());

    }

    /***
     * Right now only one rule is defined
     * @param request
     * @return
     */
    private OrderCountRule createOrderCountRule(Rule.RuleRequest request) {
        Rule.RuleConfig ruleConfig =  request.orderRules().get(0);
        Integer expressionValue = Integer.parseInt(ruleConfig.expressionValue());
        Rule.RuleType ruleType = ruleConfig.ruleType();
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
