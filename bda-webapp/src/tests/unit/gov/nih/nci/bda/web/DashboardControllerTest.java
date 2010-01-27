package gov.nih.nci.bda.web;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class DashboardControllerTest {

    @Test
    public void shouldAlwaysSendToHelloPage() throws Exception {
        DashboardController controller = new DashboardController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("hello", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        Map model = (Map) modelAndView.getModel().get("model");
        assertNotNull(model.get("now"));

    }
}
