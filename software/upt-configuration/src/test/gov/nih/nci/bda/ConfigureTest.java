package gov.nih.nci.bda;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class ConfigureTest {

	public static void main(String[] args) throws Exception {

		// Create and initialize WebClient object
	    WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
	    webClient.setThrowExceptionOnScriptError(false);
	    webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("handleRefresh");
			}

	    });

	    HtmlPage page = (HtmlPage) webClient.getPage("http://localhost:46210/upt");
	    HtmlForm form = page.getFormByName("LoginForm");
	    // Enter login and passwd
	    form.getInputByName("loginId").setValueAttribute("superadmin");
	    form.getInputByName("password").setValueAttribute("changeme");
	    form.getInputByName("applicationContextName").setValueAttribute("csmupt");
	    page = (HtmlPage) form.getInputByValue("Login").click();
	    
	    List<HtmlAnchor> anchors = (List<HtmlAnchor>)  page.getAnchors();		
	    for (HtmlAnchor anchor: anchors) 
	    {
//	    	System.out.println("anchor name :" + anchor.getNodeName());	    
//	    	System.out.println("anchor attribute :" + anchor.getHrefAttribute());
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: set('Application')"))
	    	{
	    		page = (HtmlPage) anchor.click();
	    		break;
	    	}	    	
	    }
	    System.out.println("page name :" + page.getTitleText()); 
	    anchors = (List<HtmlAnchor>)  page.getAnchors();
		
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: setAndSubmit('loadAdd')"))
	    	{
	    		System.out.println("ADDING NEW APPLICATION..." );
	    		page = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	    System.out.println("page name :" + page.toString());
	    
	    form = page.getFormByName("ApplicationForm");
	    form.getInputByName("applicationName").setValueAttribute("app1");
	    form.getTextAreaByName("applicationDescription").setText("app1 desc");
	    
	    page = (HtmlPage) form.getInputByValue("Add").click();
	    
	    System.out.println("page name :" + page.toString());	    
	}
}

