import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.String;

import java.util.HashMap;
import java.util.Properties;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jaxen.XPath;
import org.jaxen.jdom.JDOMXPath;


 public class GuiInstallerConfigApp {                                       

 JButton button = null;
 JTextField nameField = null;
 JTextField descriptionField = null;
 JTextField sizeField = null;
 JTextField defaultValueField = null;
 JTextField panelField = null;
 JComboBox fieldList = null;
 JComboBox validatorList = null;
 String[] fieldStr = null;
 String[] validatorStr = null;
 HashMap validatorMessages = null;
 
 Properties installProperties = new Properties();
 Properties upgradeProperties = new Properties();
 JRadioButton installButton,upgradeButton = null;
 
 private String propertyName=null;
 private String propertyDescription =null;
 private String propertySize =null;
 private String propertyDefault =null;
 private String panelNumber =null;
 private String fieldType =null;
 private String validatorType =null;

 
 private String installTemplateFileName=null;
 private String upgradeTemplateFileName=null;
 private String inputUserSpec=null;
 private String xPathStr=null;
 
 
 public GuiInstallerConfigApp(String installTemplateFileName,String upgradeTemplateFileName,String inputUserSpec,String xPathStr) throws FileNotFoundException, IOException
 {
	 setInstallTemplateFileName(installTemplateFileName);
	 setUpgradeTemplateFileName(upgradeTemplateFileName);
	 setInputUserSpec(inputUserSpec);
	 setXPathStr(xPathStr);
	 	 
	 installProperties.load(new FileInputStream(installTemplateFileName));
	 upgradeProperties.load(new FileInputStream(upgradeTemplateFileName));
	 
	 fieldStr = new String[]{ "text", "password"};
	 validatorStr = new String[]{"","com.izforge.izpack.util.HostNameValidator", "com.izforge.izpack.util.PortValidator", "com.izforge.izpack.util.NotEmptyValidator", "com.izforge.izpack.util.DBConnectionValidator" };
	 validatorMessages = new HashMap();
	 validatorMessages.put("com.izforge.izpack.util.HostNameValidator", "Unable to reach a host based on the name entered. Please reenter a valid hostname.");
	 validatorMessages.put("com.izforge.izpack.util.PortValidator", "The http port number is already in use. Choose a different port number.");
	 validatorMessages.put("com.izforge.izpack.util.NotEmptyValidator", "The Field Cannot be empty");
	 validatorMessages.put("com.izforge.izpack.util.DBConnectionValidator", "Unable to connect to this database with the Database Application User and Password you have entered.");
 }
 public Component createComponents() 
 {
     button = new JButton("Add Property");          
/*    
     JLabel logoLabel = new JLabel();
     ImageIcon icon = new ImageIcon("C:/work/IzConf/logotype.gif");
     logoLabel.setIcon(icon);
 */  
     
     nameField = new JTextField(35) ;
     JLabel nameLabel = new JLabel ("Enter New Property ");
     
     descriptionField = new JTextField(50) ;
     JLabel descriptionLabel = new JLabel ("Enter the Description to Display on the Installer ");

     sizeField = new JTextField(2) ;
     JLabel sizeLabel = new JLabel ("Enter the Size the field ");     

     defaultValueField = new JTextField(50) ;
     JLabel defaultValueLabel = new JLabel ("Enter the Default Value for the property ");   
     
     panelField = new JTextField(2) ;
     JLabel panelLabel = new JLabel ("Enter the Panel Number for the Property to display ");     
    
     JLabel fieldLabel = new JLabel ("Chose the type of field ");     
     fieldList = new JComboBox(fieldStr);
     
     JLabel validatorLabel = new JLabel ("Chose the type of Validator for the property ");
     validatorList = new JComboBox(validatorStr);
     
     installButton = new JRadioButton("Install");
     installButton.setSelected(true);

     upgradeButton = new JRadioButton("Upgrade");

     //Group the radio buttons.
     ButtonGroup group = new ButtonGroup();
     group.add(installButton);
     group.add(upgradeButton);

     
     JPanel pane = new JPanel();                                  // Create a panel
     pane.setBorder(BorderFactory.createEmptyBorder(              // Grey background to border
                                        30, //top
                                        30, //left
                                        10, //bottom
                                        30) //right
                                        );
    pane.setLayout(new GridLayout(0, 1));

    
  //  pane.add(logoLabel);
    pane.add(nameLabel);
    pane.add(nameField);
    pane.add(descriptionLabel);
    pane.add(descriptionField);
    pane.add(sizeLabel);
    pane.add(sizeField);
    pane.add(defaultValueLabel);
    pane.add(defaultValueField);
    pane.add(panelLabel);
    pane.add(panelField);
    pane.add(fieldLabel);
    pane.add(fieldList);
    pane.add(validatorLabel);
    pane.add(validatorList);
    pane.add(installButton);
    pane.add(upgradeButton);
    pane.add(button);    
    return pane;
 }

    public static void main(String[] args) {
    	GuiInstallerConfigApp app;
        if(args.length<=3)
        {
        	System.out.println("Usage: java GuiInstallerConfigApp <install.template.file> <upgrade.template.file> <userspec.file>");
        	System.exit(1);
        }
		try 
		{
			app = new GuiInstallerConfigApp(args[0],args[1],args[2],"//userInput");
			app.addProperty();
		}
		catch (FileNotFoundException e)
		{		
			e.printStackTrace();
		} catch (IOException e) 
		{		
			e.printStackTrace();
		}
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
        
    } 

    public void addProperty() throws Exception
        {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        JFrame frame = new JFrame("Installer application");
        Component contents = createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        
        button.addActionListener(new ActionListener() {               
            public void actionPerformed(ActionEvent e) {             
                  try {
                	setPropertyName(nameField.getText().trim());
                	setPropertyDescription(descriptionField.getText().trim());
                	setPropertySize(sizeField.getText().trim());
                	setPropertyDefault(defaultValueField.getText().trim());
                	setPanelNumber(panelField.getText().trim());
                	setFieldType(((String) fieldList.getSelectedItem()).trim());
                	setValidatorType(((String) validatorList.getSelectedItem()).trim());
                	
                	System.out.println("Adding property:: "+getPropertyName() + "with description:: " + getPropertyDescription());
               	   	if(installButton.isSelected())
               	   	{              	
	                	if(installProperties.getProperty(propertyName)== null || installProperties.getProperty(propertyName)== "")
	                	{
	                		updateInstallProperties();
	                		updateUserSpec();
	                	}
	                	else
	                	{
	                		System.out.println("Property exists in the Install Template File:: "+propertyName);
	                	}
               	   	}
               	   	if(upgradeButton.isSelected())
               	   	{
                    	if(upgradeProperties.getProperty(propertyName)== null || installProperties.getProperty(propertyName)== "")
                    	{
                    		updateUpgradeProperties();
                    		updateUserSpec();
                    	}
                    	else
                    	{
                    		System.out.println("Property exists in the Upgrade Template File:: "+propertyName);
                    	}
               	   	}
                  }catch(Exception exp){exp.printStackTrace();}                         
            }});  

       // Finish setting up the frame, and show it.
       frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
   
    public void updateInstallProperties() throws Exception 
    {    	
		installProperties.setProperty(getPropertyName(), "${"+getPropertyName()+".value}");
   		installProperties.store(new FileOutputStream(installTemplateFileName), "add a new property");   
    }
    
    public void updateUpgradeProperties() throws Exception 
    {    	
   		upgradeProperties.setProperty(getPropertyName(), "${"+getPropertyName()+".value}");
   		upgradeProperties.store(new FileOutputStream(getUpgradeTemplateFileName()), "add a new property");
    } 
    
    public void updateUserSpec() throws Exception 
    {    	
    	SAXBuilder builder = new SAXBuilder();
    	Document document = builder.build(getInputUserSpec());
		FileOutputStream outStream = null;
		XMLOutputter outputter = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setIndent("    ");
		outputter.setFormat(format);
		
		StringBuffer propertyXpathStr=new StringBuffer(getXPathStr()).append("/panel[@order="+getPanelNumber()+ "]").append("/field[@variable='"+getPropertyName()+"']");
		StringBuffer panelXpathStr=new StringBuffer(getXPathStr()).append("/panel[@order="+getPanelNumber()+ "]");
		
		XPath propertyXpath = new JDOMXPath(propertyXpathStr.toString());		
		XPath panelXpath = new JDOMXPath(panelXpathStr.toString());
	
		boolean elementFg =  propertyXpath.booleanValueOf(document);
		
		if(!elementFg)
		{
			java.util.List xpathRes = (java.util.List) panelXpath.evaluate(document);
			Iterator iterator = xpathRes.iterator();		
			if (iterator.hasNext())
			{
				Element elem = (Element) iterator.next();
				Element fieldElement = new Element("field");
				fieldElement.setAttribute(new Attribute("type", getFieldType()));
				fieldElement.setAttribute(new Attribute("variable", getPropertyName()));
				Element specElement = new Element("spec");
				specElement.setAttribute(new Attribute("txt", getPropertyDescription()));
				specElement.setAttribute(new Attribute("size", getPropertySize()));
				specElement.setAttribute(new Attribute("set", getPropertyDefault()));
				fieldElement.addContent(specElement);
				if(getValidatorType() != "")
				{
					Element validatorElement = new Element("validator");
					validatorElement.setAttribute(new Attribute("class", getValidatorType()));
					validatorElement.setAttribute(new Attribute("size", getValidatorMessage(getValidatorType())));
					fieldElement.addContent(validatorElement);
				}				
				elem.addContent(fieldElement);								
			}
			outStream = new FileOutputStream(getInputUserSpec());
			outputter.output(document, outStream);
		}	
    }
	private String getValidatorMessage(String validatorType) {		
		return (String) validatorMessages.get(validatorType);
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getInstallTemplateFileName() {
		return installTemplateFileName;
	}
	public void setInstallTemplateFileName(String installTemplateFileName) {
		this.installTemplateFileName = installTemplateFileName;
	}
	public String getUpgradeTemplateFileName() {
		return upgradeTemplateFileName;
	}
	public void setUpgradeTemplateFileName(String upgradeTemplateFileName) {
		this.upgradeTemplateFileName = upgradeTemplateFileName;
	}
	public String getInputUserSpec() {
		return inputUserSpec;
	}
	public void setInputUserSpec(String inputUserSpec) {
		this.inputUserSpec = inputUserSpec;
	}
	public String getXPathStr() {
		return xPathStr;
	}
	public void setXPathStr(String pathStr) {
		xPathStr = pathStr;
	}
	public String getPropertyDescription() {
		return propertyDescription;
	}
	public void setPropertyDescription(String propertyDescription) {
		this.propertyDescription = propertyDescription;
	}
	public String getPropertySize() {
		return propertySize;
	}
	public void setPropertySize(String propertySize) {
		this.propertySize = propertySize;
	}
	public String getPropertyDefault() {
		return propertyDefault;
	}
	public void setPropertyDefault(String propertyDefault) {
		this.propertyDefault = propertyDefault;
	}
	public String getPanelNumber() {
		return panelNumber;
	}
	public void setPanelNumber(String panelNumber) {
		this.panelNumber = panelNumber;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getValidatorType() {
		return validatorType;
	}
	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}    
 }

