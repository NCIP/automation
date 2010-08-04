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


    public static ProjectCertificationStatus getProject(
            String projectName
            , String projectUrl) throws FileNotFoundException {

        return getProject(projectName, projectUrl, true) ;

    }

    public static ProjectCertificationStatus getProject(
            String projectName
            , String projectUrl
            , boolean allowCreate) throws FileNotFoundException {
        ProjectCertificationStatus returnValue = new ProjectCertificationStatus();
        Session session = HibernateUtil.getSession();
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
