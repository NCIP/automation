package gov.nih.nci.bda.service;

import gov.nih.nci.bda.domain.Product;
import gov.nih.nci.bda.repository.ProductDao;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class SimpleProductManagerTest {

    @Test
    public void shouldAlwaysGiveEmptyListWhenNoProductsFound() {
        SimpleProductManager manager = new SimpleProductManager();
        manager.setProductDao(new ProductDao() {
            public List<Product> getProductList() {
                return new ArrayList<Product>();
            }

			public Product getProduct(String productName) {

				return new Product(productName);
			}
        });
        List<Product> products = manager.getProducts();
        assertNotNull(products);
        assertEmpty(products);
    }
    
    private void assertEmpty(List<?> list) {
        assertEquals("Expected an empty list.", 0, list.size());
    }
}
