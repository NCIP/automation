package gov.nih.nci.bda.certification;


import gov.nih.nci.bda.certification.domain.TargetLookup;
import gov.nih.nci.bda.certification.listener.SingleCommandListener;
import gov.nih.nci.bda.certification.util.HibernateUtil;

import java.io.File;
import java.util.Iterator;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.hibernate.Query;
import org.hibernate.Session;

	public class CertificationManager {
    

    public void executeJADS(String projectName) {
    	
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


    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction(); 		    
    Query query = session.createQuery( " from TargetLookup ");
    
    Iterator targets = query.iterate();
	
    while (targets.hasNext()) 
    {
          TargetLookup targetLookup= (TargetLookup)  targets.next();
          System.out.println("targetName::"+targetLookup.getMapName()+"::MAPNAME::" + targetLookup.getTargetName()+ "::projectName::" +projectName);
          project.setProperty("map.name", targetLookup.getMapName());
          project.setProperty("project.name", projectName);
          try
          {
        	  project.executeTarget(targetLookup.getTargetName());
          } 
          catch(Exception e)
          {
			System.out.println("EXCEPTION::::" + e.getMessage());
			System.err.println(e.getMessage());
          }
     } 
     
   
        System.out.println("FINISH THE EXECUTE METHOD");
    }
    
    public static void main(String args[])
    {	
    	String projectName = null;
    	System.out.println("args.length::" + args.length);
    	if (args.length != 1)
    	{
    		System.out.println("Enter the project name for certification");
    		System.exit(0);
    	}
    	else
    	{
    		projectName = args[0];
    	}

    	CertificationManager cm = new CertificationManager();
    	cm.executeJADS(projectName);
    }
  
}
