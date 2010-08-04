package gov.nih.nci.bda.certification.domain;

import gov.nih.nci.bda.certification.BuildCertificationConstants;
import gov.nih.nci.bda.certification.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 3, 2010
 * Time: 5:31:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectCertificationStatusHelper {

    private static Session session ;

    static {
        try {
            session = HibernateUtil.getSession();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static ProjectCertificationStatus getProject(
            String projectName
            , String projectUrl)
    {

        return getProject(projectName, projectUrl, true) ;

    }

    public static ProjectCertificationStatus getProject(
            String projectName
            , String projectUrl
            , boolean allowCreate)
    {
        ProjectCertificationStatus returnValue = new ProjectCertificationStatus();
        Query query = session.createQuery(BuildCertificationConstants.CERTIFICATION_QUERY);
        query.setString(0, "%"+projectName+"|%");
        returnValue = (ProjectCertificationStatus) query.uniqueResult();

        if (allowCreate && returnValue == null)
        {
            ProjectCertificationStatus newStatus = new ProjectCertificationStatus();
            newStatus.setProduct(projectName,projectUrl);
            Transaction t = session.beginTransaction();
            session.save(newStatus);
            t.commit();
            returnValue = (ProjectCertificationStatus) query.uniqueResult();

        }

        return returnValue;

    }

}
