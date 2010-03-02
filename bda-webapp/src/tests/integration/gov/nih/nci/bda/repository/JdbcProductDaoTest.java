package gov.nih.nci.bda.repository;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import gov.nih.nci.bda.domain.Product;
import gov.nih.nci.bda.domain.PracticeStatus;

import java.util.List;
import java.net.URL;
import java.net.MalformedURLException;

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
        super.executeSqlScript("file:src/tests/sql/load_real_data.sql", true);
    }

    public void testGetProductList() {
        List<Product> products = productDao.getProductList();
        assertEquals("wrong number of products?", 46, products.size());        
    }

    public void testShouldHaveUrlAndAltTextSetWhenAppropriate() throws MalformedURLException {
        URL testUrl =
                new URL("http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-cadsr-cdecurate/lastBuild/console");
        String testAltText = "The following error occurred while executing this line:: " +
                "Exception while geting the version of bdauitls::" +
                "/usr/local/hudsonuser/buildcertification/build/working/bda_certification" +
                "/software/build/project.properties (No such file or directory)";

        List<Product> products = productDao.getProductList();
        Product product = products.get(10);
        assertEquals(PracticeStatus.NOT_SUCCESSFUL, product.getBdaEnabled().getStatus());
        assertEquals(testUrl, product.getBdaEnabled().getUrl());
        assertEquals(testAltText, product.getBdaEnabled().getAlternateText());
    }
}
