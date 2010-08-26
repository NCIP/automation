package gov.nih.nci.bda.domain;

import gov.nih.nci.bda.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: bassettj
 * Date: Aug 6, 2010
 * Time: 12:41:54 PM
 */
public class ProjectActionTypeHelper {

    public static ProjectActionType getById(int id) throws FileNotFoundException {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from ProjectActionType where id = " + id);
        return (ProjectActionType)q.uniqueResult();
    }

    public static ProjectActionType getByDescription(String description) throws FileNotFoundException {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from ProjectActionType where description = ?");
        q.setString(0,description) ;
        return (ProjectActionType)q.uniqueResult();
    }
}