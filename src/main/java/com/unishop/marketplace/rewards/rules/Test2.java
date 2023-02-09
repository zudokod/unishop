package com.unishop.marketplace.rewards.rules;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test2 {

    private static final OrderRuleSet ORDER_RULE_ACTION = new OrderRuleSet();
    //private static final Map<String, > orderRuleSet = new OrderRuleSet();

    public static void main(String[] args) {

        Map<Rule.RuleType, RuleParameter> ruleParamsMap = new HashMap<>();
        RuleParameter<Integer> parameter = new RuleParameter<>(12);
        //NumberExpression operatorExpression = new NumberExpression(11, ">");
        //OrderCountRule countRule = new OrderCountRule(operatorExpression, OrderRule.RuleType.valueOf("ORDER_COUNT"));
        //System.out.println(rule.evaluate(countRule, parameter));

        Rule.RuleConfig ruleConfig = new Rule.RuleConfig(Rule.RuleType.ORDER_COUNT, "10", ">");
        List<Rule.RuleConfig> configs = new ArrayList<>();
        configs.add(ruleConfig);
        Rule.Discount discount = new Rule.Discount("dsc_1", Rule.DiscountType.ORDER_AMOUNT, 10);
        Rule.RuleRequest request = new Rule.RuleRequest(configs, discount);
        ruleBuilder(request);

        List<Rule<Integer>> orderRules = ORDER_RULE_ACTION.getOrderRules();
        for (Rule<Integer> orderRule : orderRules) {

           // OrderRuleParameter orderRuleParameter = ruleParamsMap.get(OrderRule.RuleType.ORDER_COUNT);
            System.out.println("zz " + orderRule.evaluate(parameter));
        }

        testRuleProcessor();

    }

    public static void testRuleProcessor(){
        UserRewardService rewardService = new UserRewardService();
        RulesProcessingService processingService = new RulesProcessingService();
        UserId userId = new UserId("1");
        processingService.applyRuleOnOrderComplete(userId);
        processingService.applyRuleOnOrderComplete(userId);
        System.out.println(rewardService.getEligibleDiscounts(userId));

    }

    public static void ruleBuilder(Rule.RuleRequest request){

        Rule.RuleConfig ruleConfig =  request.orderRules().get(0);
        switch (ruleConfig.ruleType()) {
            case ORDER_COUNT -> ORDER_RULE_ACTION.addOrderRule(createOrderCountRule(request));
        }
        ORDER_RULE_ACTION.setDiscount(request.discount());

    }

    private static OrderCountRule createOrderCountRule(Rule.RuleRequest request) {
        Rule.RuleConfig ruleConfig =  request.orderRules().get(0);
        Integer expressionValue = Integer.parseInt(ruleConfig.expressionValue());
        Rule.RuleType ruleType = ruleConfig.ruleType();
        //OrderRuleParameter<Integer> parameter = new OrderRuleParameter<>(ruleValue);
        NumberPredicate operatorExpression = new NumberPredicate(expressionValue, ruleConfig.operator());
        return new OrderCountRule(operatorExpression,  ruleConfig.ruleType());
    }
}
