package se.jensenyh.javacourse.saltmerch.backend.model;
import org.junit.jupiter.api.Test;
import se.jensenyh.javacourse.saltmerch.backend.model.Product;

import static org.assertj.core.api.Assertions.assertThat;
class ProductUnitTest {

    static final int productId = 1;
    static final String category = "hats";
    static final String title= "some hat 1";
    static final String description= "this is some hat";
    static final String previewImage = "\"images/hats/01.jpg\"";

    @Test
    public void productEmptyConstructorInitializes()
    {
        Product product = new Product();
        assertThat(product.id).isNotNull();
    }

    @Test
    public void productConstructorTwoInitializeFields()
    {
        Product product = new Product(productId, category, title,description, previewImage);
        assertThat(product.id).isNotNull();
        assertThat(product.category).isEqualTo("hats");
        assertThat(product.title).isEqualTo("some hat 1");
    }

}

