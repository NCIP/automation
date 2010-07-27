package gov.nih.nci.wiki;

import gov.nih.nci.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Jul 16, 2010
 * Time: 8:29:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class History {


    public static final String WIKI_CERTIFICATION_GREEN = "(/)" ;


    public ProjectCertificationStatus getMostRecentSuccess(String projectName) {
        ProjectCertificationStatus returnValue = null ;

        Session s = HibernateUtil.getSession();

//        org.hibernate.Query q = s.createQuery("from ProjectCertificationStatus pcs where pcs.certificationDate = (select max(certificationDate) from ProjectCertificationStatus where product = '" + projectName +"' and certificationStatus = '(X)')");

        DetachedCriteria d = DetachedCriteria
                .forClass(ProjectCertificationStatus.class,"pcs2")
                .setProjection(Property.forName("pcs2.certificationDate").max())
                .add(Property.forName("pcs2.product").eqProperty("pcs.product"))
                .add(Property.forName("pcs2.certificationStatus").eq(History.WIKI_CERTIFICATION_GREEN));

        Criteria c = s.createCriteria(ProjectCertificationStatus.class,"pcs")
                .add(Property.forName("product").eq(projectName))
                .add(Property.forName("certificationDate").eq(d));

        returnValue = (ProjectCertificationStatus) c.uniqueResult();

        return returnValue;
    }
}
