package gov.nih.nci.bda.certification;


import gov.nih.nci.bda.certification.listener.SingleCommandListener;

import java.io.File;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

	public class CertificationManager {
    

    public void executeJADS() {
    	
     System.out.println("ENTER THE EXECUTE METHOD");

     Project project = new Project();
     project.init();
         
        DefaultLogger logger = new DefaultLogger();
        logger.setMessageOutputLevel(Project.MSG_INFO);
        logger.setErrorPrintStream(System.err);
        logger.setOutputPrintStream(System.out);
        project.addBuildListener(logger);
        
        
        SingleCommandListener scListener = new SingleCommandListener();
        project.addBuildListener(scListener);
      
        File buildFile = new File("C:\\dev\\automat\\trunk\\software\\bda-dashboard\\certification-monitor\\build\\build.xml");
        ProjectHelper.configureProject(project, buildFile);
        //project.setProperty("ant.file", buildFile.getAbsolutePath());
   
        try {
           project.executeTarget("build:project");
        } 
        catch(Exception e) {
        	System.out.println("EXCEPTION::::" + e.getMessage());
        	System.err.println(e.getMessage());
        }
   
        System.out.println("FINISH THE EXECUTE METHOD");
    }
    
    public static void main(String args[])
    {
    	CertificationManager cm = new CertificationManager();
    	cm.executeJADS();
    }
  
}
