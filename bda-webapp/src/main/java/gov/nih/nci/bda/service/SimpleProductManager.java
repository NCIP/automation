package gov.nih.nci.bda.service;

import gov.nih.nci.bda.domain.Product;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class SimpleProductManager implements ProductManager {
    public List<Product> getProducts() {
        return Collections.EMPTY_LIST;
    }

    public static List<Product> getDefaultProducts() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Test Product 1"));
        products.add(new Product("Test Product 2"));
        products.add(new Product("Test Product 3"));
        products.add(new Product("Test Product 4"));
        return products;
    }
}
