package gov.nih.nci.bda;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.PromptHandler;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.WebWindowEvent;
import com.gargoylesoftware.htmlunit.WebWindowListener;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

public class ConfigureTest {

	private WebClient webClient = null;
	private HtmlPage page = null;
	private HtmlForm form = null;
	private List<HtmlAnchor> anchors = null; 
	
	private String uptUrl;
	private String uptAppUser;
	private String uptAppPassword;
	private String appContextName;

	private String userLogonID;
	private String userFirstName;
	private String userLastName;
	private String userPassword;
	
	private String applicationName;
	private String applicationDesc;
	private String applicationConnectionUrl;
	private String applicationDBUserName;
	private String applicationDBPassword;
	private String applicationDBDialect;
	private String applicationDBDriver;
	
	
	
	public String getUptUrl() {
		return uptUrl;
	}

	public void setUptUrl(String uptUrl) {
		this.uptUrl = uptUrl;
	}

	public String getUptAppUser() {
		return uptAppUser;
	}

	public void setUptAppUser(String uptAppUser) {
		this.uptAppUser = uptAppUser;
	}

	public String getUptAppPassword() {
		return uptAppPassword;
	}

	public void setUptAppPassword(String uptAppPassword) {
		this.uptAppPassword = uptAppPassword;
	}

	public String getAppContextName() {
		return appContextName;
	}

	public void setAppContextName(String appContextName) {
		this.appContextName = appContextName;
	}

	public String getUserLogonID() {
		return userLogonID;
	}

	public void setUserLogonID(String userLogonID) {
		this.userLogonID = userLogonID;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public String getApplicationConnectionUrl() {
		return applicationConnectionUrl;
	}

	public void setApplicationConnectionUrl(String applicationConnectionUrl) {
		this.applicationConnectionUrl = applicationConnectionUrl;
	}

	public String getApplicationDBUserName() {
		return applicationDBUserName;
	}

	public void setApplicationDBUserName(String applicationDBUserName) {
		this.applicationDBUserName = applicationDBUserName;
	}

	public String getApplicationDBPassword() {
		return applicationDBPassword;
	}

	public void setApplicationDBPassword(String applicationDBPassword) {
		this.applicationDBPassword = applicationDBPassword;
	}

	public String getApplicationDBDialect() {
		return applicationDBDialect;
	}

	public void setApplicationDBDialect(String applicationDBDialect) {
		this.applicationDBDialect = applicationDBDialect;
	}

	public String getApplicationDBDriver() {
		return applicationDBDriver;
	}

	public void setApplicationDBDriver(String applicationDBDriver) {
		this.applicationDBDriver = applicationDBDriver;
	}

	public static void main(String[] args) {
		ConfigureTest ct = new ConfigureTest();
		try {
			ct.setUptAppUser("superadmin");
			ct.setUptUrl("http://localhost:46210/upt");
			ct.setUptAppPassword("changeme");
			ct.setAppContextName("csmupt");
			
		    ct.setUserLogonID("caarrayAdmin");
		    ct.setUserFirstName("Admin_First");
		    ct.setUserLastName("Admin_Last");
		    ct.setUserPassword("password");
			
		    ct.setApplicationName("caarray");
		    ct.setApplicationDesc("caarray desc");
		    ct.setApplicationConnectionUrl("jdbc:mysql://localhost:3306/carrdb");
		    ct.setApplicationDBUserName("carruser");
		    ct.setApplicationDBPassword("password");
		    ct.setApplicationDBDialect("org.hibernate.dialect.MySQLDialect");
		    ct.setApplicationDBDriver("com.mysql.jdbc.Driver");
		  		    
			ct.configureTest();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void configureTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
	    	    
		initialize();
		
	    login();

	    createUser();

	    createApplication();
	 
	    associateApplicationWithUser();    
	}

	private void associateApplicationWithUser() throws IOException {
	    anchors = (List<HtmlAnchor>)  page.getAnchors();
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: set('Application')"))
	    	{
	    		page = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	   
	     
	    anchors = (List<HtmlAnchor>)  page.getAnchors();
	    
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: setAndSubmit('loadSearch')"))
	    	{
	    		System.out.println("ADDING NEW APPLICATION..." );
	    		page = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	    
	    System.out.println("page name :" + page.toString());

	    form = page.getFormByName("ApplicationForm");
	    
	    form.getInputByName("applicationName").setValueAttribute(getApplicationName());
	    page = (HtmlPage) form.getInputByValue("Search").click();
	    form = page.getFormByName("ApplicationForm");
	    List<HtmlRadioButtonInput> buttons = (List<HtmlRadioButtonInput>) form.getRadioButtonsByName("applicationId");
	
	    for (HtmlRadioButtonInput button: buttons) 
	    {
	    	button.click();
	    }
	    page = (HtmlPage) form.getInputByValue("View Details").click();
	    form = page.getFormByName("ApplicationForm");
	    
	    page = (HtmlPage) form.getInputByValue("Associated Admins").click();

  
	    WebRequestSettings requestSettings = new WebRequestSettings(new URL(getUptUrl()+"/SearchUserDBOperation.do"), HttpMethod.POST);
	    requestSettings.setRequestParameters(new ArrayList());
	    requestSettings.getRequestParameters().add(new NameValuePair("operation", "loadSearch"));
	    page = webClient.getPage(requestSettings);
	    form = page.getFormByName("SearchUserForm");
	    
	    form.getInputByName("userLoginName").setValueAttribute(getUserLogonID());
	    page = (HtmlPage) form.submit(null);
	    form = page.getFormByName("SearchUserForm");
	    
	    Iterator ite =  form.getAllHtmlChildElements().iterator();
	    String defaultValue = null;
		while (ite.hasNext()) {
			HtmlElement child = (HtmlElement) ite.next();
			if (child instanceof HtmlCheckBoxInput)
			{
				System.out.println("CHECK BOX INSTANCE");
				HtmlCheckBoxInput chkBox = (HtmlCheckBoxInput) child;
				System.out.println("ATTRIBUTE NAME::"+chkBox.getDefaultValue());
				defaultValue = chkBox.getDefaultValue();

			}
		}

	    WebRequestSettings associationRequestSettings = new WebRequestSettings(new URL(getUptUrl()+"/ApplicationDBOperation.do"), HttpMethod.POST);
    	associationRequestSettings.setRequestParameters(new ArrayList());
    	associationRequestSettings.getRequestParameters().add(new NameValuePair("operation", "setAssociation"));
    	associationRequestSettings.getRequestParameters().add(new NameValuePair("associatedIds", defaultValue));
	    page = webClient.getPage(associationRequestSettings);		
	}

	private void createApplication() throws IOException {
	    anchors = (List<HtmlAnchor>)  page.getAnchors();		
	    for (HtmlAnchor anchor: anchors) 
	    {
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
	    
	    form = page.getFormByName("ApplicationForm");
	    form.getInputByName("applicationName").setValueAttribute(getApplicationName());
	    form.getTextAreaByName("applicationDescription").setText(getApplicationDesc());
	    form.getInputByName("applicationDatabaseURL").setValueAttribute(getApplicationConnectionUrl());
	    form.getInputByName("applicationDatabaseUserName").setValueAttribute(getApplicationDBUserName());
	    form.getInputByName("applicationDatabasePassword").setValueAttribute(getApplicationDBPassword());
	    form.getInputByName("applicationDatabaseConfirmPassword").setValueAttribute(getApplicationDBPassword());
	    form.getInputByName("applicationDatabaseDialect").setValueAttribute(getApplicationDBDialect());
	    form.getInputByName("applicationDatabaseDriver").setValueAttribute(getApplicationDBDriver());
 
	    page = (HtmlPage) form.getInputByValue("Add").click();
	}

	private void createUser() throws IOException {
	    anchors = (List<HtmlAnchor>)  page.getAnchors();		
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: set('User')"))
	    	{
	    		page = (HtmlPage) anchor.click();
	    		break;
	    	}	    	
	    }
	    
	    anchors = (List<HtmlAnchor>)  page.getAnchors();		
	    for (HtmlAnchor anchor: anchors) 
	    {
	    	if(anchor.getHrefAttribute().equalsIgnoreCase("javascript: setAndSubmit('loadAdd')"))
	    	{
	    		System.out.println("Creating new admin user..." );
	    		page = (HtmlPage) anchor.click();
	    		break;
	    	}	    		    	
	    }
	    
	    form = page.getFormByName("UserForm");
	    form.getInputByName("userLoginName").setValueAttribute(getUserLogonID());
	    form.getInputByName("userFirstName").setValueAttribute(getUserFirstName());
	    form.getInputByName("userLastName").setValueAttribute(getUserLastName());
	    form.getInputByName("userPassword").setValueAttribute(getUserPassword());
	    form.getInputByName("userPasswordConfirm").setValueAttribute(getUserPassword());
 
	    page = (HtmlPage) form.getInputByValue("Add").click();
	}

	private void login() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
	    page = (HtmlPage) webClient.getPage(getUptUrl());
	    form = page.getFormByName("LoginForm");
	    // Enter login and passwd
	    form.getInputByName("loginId").setValueAttribute(getUptAppUser());
	    form.getInputByName("password").setValueAttribute(getUptAppPassword());
	    form.getInputByName("applicationContextName").setValueAttribute(getAppContextName());
	    page = (HtmlPage) form.getInputByValue("Login").click();		
	}

	private void initialize() {
		webClient = new WebClient(BrowserVersion.FIREFOX_3);
	    webClient.setThrowExceptionOnScriptError(true);
	    webClient.setJavaScriptEnabled(true);
	    webClient.setRedirectEnabled(true);
/*
	    webClient.addWebWindowListener(new WebWindowListener() {
                     public void webWindowClosed(WebWindowEvent event) 
                     {
                         if (win==null || event.getOldPage().equals(win.getEnclosedPage())) 
                         {
                             win = webClient.getCurrentWindow();        
                         }
                         String win = event.getWebWindow().getName();
                         Page oldPage = event.getOldPage();
                         String oldPageTitle = "no_html";
                         if (oldPage instanceof HtmlPage) 
                         {
                             oldPageTitle = ((HtmlPage) oldPage).getTitleText();
                         }                         
                     }
         
                     public void webWindowContentChanged(WebWindowEvent event) 
                     {                         
                         String winName = event.getWebWindow().getName();
                         Page oldPage = event.getOldPage();
                         Page newPage = event.getNewPage();
                         
                         String oldPageTitle = "no_html";
                         if (oldPage instanceof HtmlPage)
                             oldPageTitle = ((HtmlPage) oldPage).getTitleText();
                         String newPageTitle = "no_html";
                         if (newPage instanceof HtmlPage){
                             newPageTitle = ((HtmlPage) newPage).getTitleText();
                             List<HtmlForm> forms = (List<HtmlForm>) ((HtmlPage) newPage).getForms();
                             for (HtmlForm form : forms)
                             {
                            	 if(winName != null && winName.equalsIgnoreCase("UserSearchWin")){                 
                            		webClient.setJavaScriptEnabled(false);									
                            	 }
                             }
                         }
                     }
      
                     public void webWindowOpened(WebWindowEvent event) 
                     {
                         String win = event.getWebWindow().getName();
                         Page newPage = event.getNewPage();
                     }          
         });
 */          
	    webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("handleRefresh");
			}

	    });
	    webClient.setPromptHandler(new PromptHandler() 
	    {
			 public String handlePrompt(Page page, String msg) {
					System.out.println("handlePrompt");
					return "handle prompt";
			 }
	    });		
	}
}

