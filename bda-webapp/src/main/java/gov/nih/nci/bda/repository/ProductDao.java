package gov.nih.nci.bda.repository;

import gov.nih.nci.bda.domain.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProductList();    
}
