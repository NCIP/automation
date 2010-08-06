package gov.nih.nci.bda.domain;

import gov.nih.nci.bda.certification.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 6, 2010
 * Time: 10:12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectHelper {

    public static Project getByName(String projectName) throws FileNotFoundException {

        Session s = HibernateUtil.getSession();

        Query q = s.createQuery("from Project where name like ?");
        q.setString(0,projectName);
        Project returnValue  = (Project)q.uniqueResult();

        return returnValue;
    }

}
