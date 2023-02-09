package com.unishop.marketplace;


import com.unishop.marketplace.models.*;
import com.unishop.marketplace.rewards.discount.OrderDiscountService;
import com.unishop.marketplace.rewards.discount.PercentageReducer;
import com.unishop.marketplace.service.*;
import com.unishop.marketplace.service.repository.ProductCatalogStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CheckoutOperationsTest {

    @InjectMocks
    private CheckoutService checkoutService;

    @Spy
    @InjectMocks
    private OrderService orderService;

    @Spy
    @InjectMocks
    private RulesProcessingService rulesProcessingService;

    @Spy
    @InjectMocks
    private OrderAdjustmentService adjustmentService;

    @Spy
    private UserRewardService rewardService;;

    @Spy
    private RuleDefinitionService ruleDefinitions;

    @Spy
    private OrderDiscountService orderDiscountService;

    @Spy
    private PercentageReducer percentageReducer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_checkout_cart_1_item_without_discount() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        UserCart cart = new UserCart(new UserId("1"), itemMap, new ArrayList<>());
        Order order = checkoutService.checkout(cart);
        assertThat(order).isNotNull();
    }

    @Test
    void should_checkout_cart_2_items_without_discount() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        product = ProductCatalogStore.findProductById("2");
        CartItem item2 = new CartItem(product, 10);
        itemMap.put("2", item2);
        UserCart cart = new UserCart(new UserId("1"), itemMap, new ArrayList<>());
        Order order = checkoutService.checkout(cart);
        assertThat(order).isNotNull();
        assertThat(order.orderItems().size()).isEqualTo(2);
    }

    @Test
    void should_payable_amount_is_sum_of_price_all_items_in_cart_without_discount() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        UserCart cart = new UserCart(new UserId("1"), itemMap, new ArrayList<>());
        Order order = checkoutService.checkout(cart);
        assertThat(order.payableAmount()).isEqualTo(product.unitPrice() * item.quantity());
    }

    @Test
    void should_total_amount_is_sum_of_price_all_items_in_cart() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        double totalProductPrice = product.unitPrice() * item.quantity();
        product = ProductCatalogStore.findProductById("2");
        CartItem item2 = new CartItem(product, 10);
        itemMap.put("2", item2);
        totalProductPrice += product.unitPrice() * item2.quantity();
        UserCart cart = new UserCart(new UserId("1"), itemMap, new ArrayList<>());
        Order order = checkoutService.checkout(cart);
        assertThat(order.totalAmount()).isEqualTo(totalProductPrice);
    }

    @Test
    void should_payable_amount_is_not_discounted_with_invalid_rule() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        List<String> rewardIds = new ArrayList<>();
        rewardIds.add("DISCOUNT10");
        UserCart cart = new UserCart(new UserId("1"), itemMap, rewardIds);
        Order order = checkoutService.checkout(cart);
        assertThat(order.payableAmount()).isEqualTo(product.unitPrice() * item.quantity());
    }


    @Test
    void should_payable_amount_is_not_discounted_with_invalid_code() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        List<String> rewardIds = new ArrayList<>();
        rewardIds.add("DISCOUNT5");
        UserCart cart = new UserCart(new UserId("1"), itemMap, rewardIds);
        Order order = checkoutService.checkout(cart);
        assertThat(order.payableAmount()).isEqualTo(product.unitPrice() * item.quantity());
    }

    @Test
    void should_payable_amount_is_discounted_in_nth_order() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        List<String> rewardIds = new ArrayList<>();
        rewardIds.add("DISCOUNT10");
        UserCart cart = new UserCart(new UserId("1"), itemMap, rewardIds);
        Order order = checkoutService.checkout(cart);
        for(int n = 0; n < 10; n++){
            order = checkoutService.checkout(cart);
        }
        assertThat(order.payableAmount()).isNotEqualTo(product.unitPrice() * item.quantity());
    }

    @Test
    void should_payable_amount_is_not_discounted_order_after_discount_used() {
        Product product = ProductCatalogStore.findProductById("1");
        CartItem item = new CartItem(product, 10);
        HashMap<String, CartItem> itemMap = new HashMap<>();
        itemMap.put("1", item);
        List<String> rewardIds = new ArrayList<>();
        rewardIds.add("DISCOUNT10");
        UserCart cart = new UserCart(new UserId("1"), itemMap, rewardIds);
        Order order = checkoutService.checkout(cart);
        for(int n = 0; n < 11; n++){
            order = checkoutService.checkout(cart);
        }
        assertThat(order.payableAmount()).isEqualTo(product.unitPrice() * item.quantity());
    }
}
