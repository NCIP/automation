package gov.nih.nci.bda.service;

import gov.nih.nci.bda.domain.Product;

import java.util.List;
import java.util.Collections;

public class SimpleProductManager implements ProductManager {
    public List<Product> getProducts() {
        return Collections.EMPTY_LIST;
    }
}
