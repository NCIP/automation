package gov.nih.nci.bda.repository;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import gov.nih.nci.bda.domain.Product;

import java.util.List;

public class JdbcProductDaoTest extends AbstractTransactionalDataSourceSpringContextTests {

    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:test-context.xml"};
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.deleteFromTables(new String[]{"project_certification_status"});
        super.executeSqlScript("file:src/tests/sql/load_data.sql", true);
    }

    public void testGetProductList() {

        List<Product> products = productDao.getProductList();
        assertEquals("wrong number of products?", 3, products.size());
    }

}
