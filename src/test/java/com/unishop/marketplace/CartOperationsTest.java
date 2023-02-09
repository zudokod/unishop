package com.unishop.marketplace;

import com.unishop.marketplace.models.AvailableStock;
import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.models.UserId;
import com.unishop.marketplace.service.*;
import com.unishop.marketplace.service.repository.ProductCatalogStore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CartOperationsTest {


    @InjectMocks
    private UserCartService cartService;

    @Spy
    private ProductCatalogService catalogService;


    static {
        ProductCatalogStore.init();
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_save_empty_cart() {
        final var actual = cartService.fetchCart("1");
        UserCart cart = new UserCart(new UserId("1"), new HashMap<>(), new ArrayList<>());
        assertThat(actual).usingRecursiveComparison().isEqualTo(cart);
    }

    @Test
    void should_add_item_cart() {

        UserCart cart1 = cartService.addItemToCart("1", "1", 10);
        var actual = cartService.fetchCart("1");
        assertThat(actual).usingRecursiveComparison().isEqualTo(cart1);

        cartService.addItemToCart("1", "2", 11);
         var actual2 = cartService.fetchCart("1");
        assertThat(actual2.items().size()).isEqualTo(2);
    }


    @Test
    void should_check_product_exists() {
        Product product = catalogService.findProductById("1");
        Product toTest = new Product("1", "Surgical Mask", 10.00,  new AvailableStock(128));
        assertThat(product).usingRecursiveComparison().isEqualTo(toTest);
    }


}
