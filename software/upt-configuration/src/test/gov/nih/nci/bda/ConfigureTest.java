package gov.nih.nci.bda;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
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

	    final HtmlPage page = (HtmlPage) webClient.getPage("http://localhost:46210/upt");
	    final HtmlForm form = page.getFormByName("LoginForm");
	    // Enter login and passwd
	    form.getInputByName("loginId").setValueAttribute("superadmin");
	    form.getInputByName("password").setValueAttribute("changeme");
	    form.getInputByName("applicationContextName").setValueAttribute("csmupt");
	    final HtmlPage page2 = (HtmlPage) form.getInputByValue("Login").click();
	    
	    HtmlPage page3 = null;
		HtmlPage page4 = null;   
	    List<HtmlAnchor> anchors = (List<HtmlAnchor>)  page2.getAnchors();		
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: set('User')"))
	    	{
	    		page3 = (HtmlPage) anchor.click();
	    		break;
	    	}	    	
	    }
	    
	    anchors = (List<HtmlAnchor>)  page3.getAnchors();
		
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: setAndSubmit('loadAdd')"))
	    	{
	    		System.out.println("Creating new admin user..." );
	    		page4 = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	    
	    final HtmlForm form1 = page4.getFormByName("UserForm");
	    form1.getInputByName("userLoginName").setValueAttribute("caarrayAdmin");
	    form1.getInputByName("userFirstName").setValueAttribute("Admin_First");
	    form1.getInputByName("userLastName").setValueAttribute("Admin_Last");
	    form1.getInputByName("userPassword").setValueAttribute("password");
	    form1.getInputByName("userPasswordConfirm").setValueAttribute("password");
 
	    final HtmlPage page5 = (HtmlPage) form1.getInputByValue("Add").click();

    
	    HtmlPage page6 = null;
		HtmlPage page7 = null;
		final HtmlPage page8;
		HtmlPage page9 = null;
		HtmlPage page10 = null;
		final HtmlPage page11;
	    anchors = (List<HtmlAnchor>)  page5.getAnchors();		
	    for (HtmlAnchor anchor: anchors) 
	    {
//	    	System.out.println("anchor name :" + anchor.getNodeName());	    
//	    	System.out.println("anchor attribute :" + anchor.getHrefAttribute());
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: set('Application')"))
	    	{
	    		page6 = (HtmlPage) anchor.click();
	    		break;
	    	}	    	
	    }
	    System.out.println("page name :" + page6.getTitleText()); 
	    anchors = (List<HtmlAnchor>)  page6.getAnchors();
		
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: setAndSubmit('loadAdd')"))
	    	{
	    		System.out.println("ADDING NEW APPLICATION..." );
	    		page7 = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	    System.out.println("page name :" + page7.toString());
	    
	    
	    final HtmlForm form2 = page7.getFormByName("ApplicationForm");
	    form2.getInputByName("applicationName").setValueAttribute("caarray");
	    form2.getTextAreaByName("applicationDescription").setText("caarray desc");
	    form2.getInputByName("applicationDatabaseURL").setValueAttribute("jdbc:mysql://localhost:3306/carrdb");
	    form2.getInputByName("applicationDatabaseUserName").setValueAttribute("carruser");
	    form2.getInputByName("applicationDatabasePassword").setValueAttribute("password");
	    form2.getInputByName("applicationDatabaseConfirmPassword").setValueAttribute("password");
	    form2.getInputByName("applicationDatabaseDialect").setValueAttribute("org.hibernate.dialect.MySQLDialect");
	    form2.getInputByName("applicationDatabaseDriver").setValueAttribute("com.mysql.jdbc.Driver");
 
	    page8 = (HtmlPage) form2.getInputByValue("Add").click();
	   
	    
	    anchors = (List<HtmlAnchor>)  page5.getAnchors();
		
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: set('Application')"))
	    	{
	    		page9 = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	   
	     
	    anchors = (List<HtmlAnchor>)  page9.getAnchors();
	    
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: setAndSubmit('loadSearch')"))
	    	{
	    		System.out.println("ADDING NEW APPLICATION..." );
	    		page10 = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	    
	    System.out.println("page name :" + page10.toString());

	    HtmlForm form3 = page10.getFormByName("ApplicationForm");
	    
	    form3.getInputByName("applicationName").setValueAttribute("caarray");
	    page11 = (HtmlPage) form3.getInputByValue("Search").click();
	    HtmlForm form4 = page11.getFormByName("ApplicationForm");
	    List<HtmlRadioButtonInput> buttons = (List<HtmlRadioButtonInput>) form4.getRadioButtonsByName("applicationId");
	    //List<HtmlButton> buttons = (List<HtmlButton>) form3.getButtonsByName("Search");
	
	    for (HtmlRadioButtonInput button: buttons) 
	    {
	    	System.out.println("Button name..." + button.getNameAttribute());
	    	System.out.println("Button name..." + button.getAttributeValue("applicationId"));
	    	button.click();
	    }
	    HtmlPage page12 = (HtmlPage) form4.getInputByValue("View Details").click();
	    HtmlForm form5 = page12.getFormByName("ApplicationForm");
	    
	    HtmlPage page13 = (HtmlPage) form5.getInputByValue("Associated Admins").click();
	    System.out.println("page name :" + page13.toString());
	    HtmlForm form6 = page13.getFormByName("ApplicationForm");
	    HtmlPage page14 = (HtmlPage) form6.getInputByValue("Assign Admin").click();
	    System.out.println("page name :" + page14.toString());
	    
	}
}

