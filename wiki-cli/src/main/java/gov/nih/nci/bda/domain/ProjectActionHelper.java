package gov.nih.nci.bda.domain;

import gov.nih.nci.bda.certification.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 17, 2010
 * Time: 9:45:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectActionHelper {

    public static ProjectAction getLatestProjectAction(String projectName) throws FileNotFoundException {

        Session s = HibernateUtil.getSession();

        Query q = s.createQuery("FROM ProjectAction a WHERE project.name = ? AND a.date = (SELECT MAX(md.date) FROM ProjectAction md WHERE md.project.name = ?)");
        q.setString(0,projectName);
        q.setString(1,projectName);

        return (ProjectAction)q.uniqueResult();
    }
}
