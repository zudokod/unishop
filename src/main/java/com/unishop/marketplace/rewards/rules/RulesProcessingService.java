package com.unishop.marketplace.rewards.rules;

import com.unishop.marketplace.service.repository.OrderHistoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RulesProcessingService {

    @Autowired
    private UserRewardService rewardService;;

    @Autowired
    private RuleDefinitionService ruleDefinitions;


    public void applyRuleOnOrderComplete(UserId userId) {
        List<Rule<Integer>> orderRules = ruleDefinitions.getOrderRuleSet().getOrderRules();
        for (Rule<Integer> orderRule : orderRules) {
            switch (orderRule.ruleType()){
                case ORDER_COUNT ->  applyRuleOrderCount(orderRule, userId);
            }
        }
    }
    private void applyRuleOrderCount(Rule<Integer> orderRule, UserId userId){
        int orderCount = OrderHistoryStore.findOrderCount(userId);
        if(orderRule.evaluate(new RuleParameter<Integer>(orderCount))){
            rewardService.addEligibleDiscount(userId, ruleDefinitions.getOrderRuleSet().getDiscount());
        }
    }
}

