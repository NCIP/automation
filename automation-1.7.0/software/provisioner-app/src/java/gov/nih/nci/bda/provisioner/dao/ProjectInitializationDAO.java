package gov.nih.nci.bda.provisioner.dao;

import java.util.Iterator;
import java.util.List;

import gov.nih.nci.bda.provisioner.User;
import gov.nih.nci.bda.provisioner.util.HibernateUtil;
import gov.nih.nci.bda.provisioner.domain.ProjectInitialization;

import org.hibernate.Query;
import org.hibernate.Session;


public class ProjectInitializationDAO {

	public void saveProjectInitialization(ProjectInitialization projectInitialization)
	{
		//Session session = HibernateUtil.getSession();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    session.save(projectInitialization);

	    session.getTransaction().commit();
	    //HibernateUtil.closeSession();
	}
	
	public void updateRunCommandStatus(String projectName,String commandName,String runCommandStatus )
	{
		//Session session = HibernateUtil.getSession();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
		Query query = session.createQuery("update ProjectInitialization set runCommand = :runCommand where projectName = :projectName and commandName = :commandName" );
		query.setString("runCommand",runCommandStatus);
		query.setString("projectName",projectName);
		query.setString("commandName",commandName);
	    int rowCount = query.executeUpdate();
	    System.out.println("Rows affected: " + rowCount);
	    session.getTransaction().commit();
	    //HibernateUtil.closeSession();
	}	
	
}
