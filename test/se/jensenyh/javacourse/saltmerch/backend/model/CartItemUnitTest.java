package se.jensenyh.javacourse.saltmerch.backend.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import se.jensenyh.javacourse.saltmerch.backend.model.CartItem;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemUnitTest {

    static final int productId = 1;
    static final String title = "Some Jacket 1";
    static final String color= "black";
    static final String size= "M";
    static final int quantity = 8;
    static final String previewImage = "\"images/jackets/01.jpg\"";

    @Test
    public void cartItemEmptyConstructorInitializes()
    {
        CartItem cartItem = new CartItem();
        assertThat(cartItem.productId).isNotNull();
    }

    @Test
    public void cartItemConstructorTwoInitializeFields()
    {
        CartItem cartItem = new CartItem(productId, title, color, size,  previewImage, quantity);
        assertThat(cartItem.productId).isNotNull();
        assertThat(cartItem.title).isEqualTo("Some Jacket 1");
        assertThat(cartItem.color).isEqualTo("black");
        assertThat(cartItem.size).isEqualTo("M");
        assertThat(cartItem.quantity).isEqualTo(8);
    }

}
