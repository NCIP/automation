package gov.nih.nci.bda.web;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import gov.nih.nci.bda.service.SimpleProductManager;
import gov.nih.nci.bda.repository.ProductDao;
import gov.nih.nci.bda.domain.Product;

public class DashboardControllerTest {
    private DashboardController controller;

    @Before
    public void setUp() {
        controller = new DashboardController();
        SimpleProductManager manager = new SimpleProductManager();
        manager.setProductDao(new ProductDao() {

            public List<Product> getProductList() {
                return new ArrayList<Product>();
            }
        });
        controller.setProductManager(manager);
    }

    @Test
    public void shouldAlwaysSendToHelloPage() throws Exception {
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("hello", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        Map model = (Map) modelAndView.getModel().get("model");
        assertNotNull(model.get("now"));
    }
}
