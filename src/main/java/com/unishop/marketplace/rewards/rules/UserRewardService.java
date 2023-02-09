package com.unishop.marketplace.rewards.rules;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserRewardService {

    private static final Map<UserId, List<Rule.Discount>> eligibleDiscounts = new HashMap<>();

    public List<Rule.Discount> getEligibleDiscounts(UserId userId) {
        return eligibleDiscounts.get(userId);
    }

    public void addEligibleDiscount(UserId userId, Rule.Discount discount){
        eligibleDiscounts.computeIfAbsent(userId, k -> new ArrayList<Rule.Discount>());
        eligibleDiscounts.get(userId).add(discount);
    }

    public void removeDiscount(UserId userId, String discountCode){
        Rule.Discount discount = getDiscountByCode(userId, discountCode);
        if(eligibleDiscounts.get(userId) != null) eligibleDiscounts.get(userId).remove(discount);
    }

    public Rule.Discount getDiscountByCode(UserId userId, String discountCode){
        List<Rule.Discount> eligibleDiscounts = getEligibleDiscounts(userId);
        Optional<Rule.Discount> discount = eligibleDiscounts.stream().filter(it -> it.discountCode().equals(discountCode)).findFirst();
        return discount.orElseThrow();
    }
}
