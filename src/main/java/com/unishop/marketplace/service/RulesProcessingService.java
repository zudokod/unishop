package com.unishop.marketplace.service;

import com.unishop.marketplace.models.UserId;
import com.unishop.marketplace.rewards.rules.*;
import com.unishop.marketplace.service.repository.OrderHistoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Processes rule for an order to generate discounts if any
 */

@Service
public class RulesProcessingService {

    @Autowired
    private UserRewardService rewardService;;

    @Autowired
    private RuleDefinitionService ruleDefinitions;


    /***
     * ON complete of order, tor the nth time, a discount is applied after the rule of Order count rule satisfies
     * @param userId
     */
    public void applyRuleOnOrderComplete(UserId userId) {
        List<Rule<Integer>> orderRules = ruleDefinitions.getOrderRuleSet().getOrderRules();
        // this allows evalution of multiple rules
        for (Rule<Integer> orderRule : orderRules) {
            switch (orderRule.ruleType()){
                case ORDER_COUNT ->  applyRuleOrderCount(orderRule, userId);
            }
        }
    }

    /**
     * Action to be taken for valid Nth order completion
     * @param orderRule
     * @param userId
     */
    private void applyRuleOrderCount(Rule<Integer> orderRule, UserId userId){
        int orderCount = OrderHistoryStore.findOrderCount(userId);
        if(orderRule.evaluate(new RuleParameter<Integer>(orderCount))){
            // this assigned discounts will be looked up and used by user while checkout
            rewardService.addEligibleDiscount(userId, ruleDefinitions.getOrderRuleSet().getDiscount());
        }
    }
}

