package gov.nih.nci.bda.service;

import gov.nih.nci.bda.domain.Product;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;

import java.util.List;

public class SimpleProductManagerTest {

    @Test
    public void shouldAlwaysGiveEmptyListWhenNoProductsFound() {
        ProductManager manager = new SimpleProductManager();
        List<Product> products = manager.getProducts();
        assertNotNull(products);
        assertEmpty(products);
    }

    private void assertEmpty(List<?> list) {
        assertEquals("Expected an empty list.", 0, list.size());
    }
}
