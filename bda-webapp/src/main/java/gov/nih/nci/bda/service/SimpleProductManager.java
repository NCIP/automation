package gov.nih.nci.bda.service;

import gov.nih.nci.bda.domain.Product;
import gov.nih.nci.bda.repository.ProductDao;

import java.util.List;

public class SimpleProductManager implements ProductManager {
    private ProductDao productDao;
    
    public List<Product> getProducts() {
        return productDao.getProductList();
    }

    public void setProductDao(ProductDao dao) {
        this.productDao = dao;
    }
}
