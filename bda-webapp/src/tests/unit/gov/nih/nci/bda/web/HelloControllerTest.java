package gov.nih.nci.bda.web;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class HelloControllerTest {

    @Test
    public void testFoo() {
        System.out.println("true = " + true);
        assert (true);
    }

    @Test
    public void shouldAlwaysSendToHelloPage() throws Exception {
        HelloController controller = new HelloController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("hello.jsp", modelAndView.getViewName());
    }
}
