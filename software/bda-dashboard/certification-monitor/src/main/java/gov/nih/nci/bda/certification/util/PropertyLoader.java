package gov.nih.nci.bda.certification.util;

import gov.nih.nci.bda.certification.BuildCertificationConstants;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.domain.ProjectProperties;

import org.hibernate.Query;
import org.hibernate.Session;

public class PropertyLoader {

	private static ProjectProperties pp = null;
	
	public static void loadProperties(String projectName) {
		Session session = HibernateUtil.getSession();
	    session.beginTransaction(); 
	    
	    Query query = session.createQuery( " from project_properties where project_name like ?");
	    
	    query.setString(0, projectName);
		pp = (ProjectProperties) query.uniqueResult();
		if(pp != null )
		{	
			//
	    }
	    else
	    {
	    	//throw new Exception("Cannot the properties for the project " + projectName);
	    }
		session.clear();
	}

}
