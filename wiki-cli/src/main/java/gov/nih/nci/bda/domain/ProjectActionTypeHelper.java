package gov.nih.nci.bda.domain;

import gov.nih.nci.bda.certification.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 6, 2010
 * Time: 12:41:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectActionTypeHelper {

    public static ProjectActionType getById(int id) throws FileNotFoundException {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from ProjectActionType where id = " + id);
        ProjectActionType pat = (ProjectActionType)q.uniqueResult();
        return pat;
    }

    public static ProjectActionType getByDescription(String description) throws FileNotFoundException {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from ProjectActionType where description = ?");
        q.setString(0,description) ;
        ProjectActionType pat = (ProjectActionType)q.uniqueResult();
        return pat;
    }
}
