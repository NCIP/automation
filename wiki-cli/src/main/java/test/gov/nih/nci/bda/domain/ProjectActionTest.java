package test.gov.nih.nci.bda.domain;

import gov.nih.nci.bda.certification.util.HibernateUtil;
import gov.nih.nci.bda.domain.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * ProjectAction Tester.
 *
 * @author <Authors name>
 * @since <pre>08/06/2010</pre>
 * @version 1.0
 */
public class ProjectActionTest extends TestCase {
    public ProjectActionTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
        HibernateUtil.setConnectionUrl("jdbc:mysql://localhost/test");
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() throws FileNotFoundException {

        return new TestSuite(ProjectActionTest.class);
    }

    public void testGetMostRecentByProjectName() throws Exception {
        Calendar c = Calendar.getInstance();
        Date older = c.getTime();
        c.add(Calendar.DATE,5);
        Date newer = c.getTime();

        HibernateUtil.setConnectionUrl("jdbc:mysql://localhost/test");
        Session s = HibernateUtil.getSession();
        s.clear();

        ProjectActionType pat = getNewProjectActionType(s);
        Project p = getNewProject(s);

        ProjectAction pa = new ProjectAction();
        pa.setType(pat.getId());
        pa.setDate(older);
        pa.setProject(p);

        Transaction t = s.beginTransaction();

        s.save(pa);

        t.commit();

        

    }

    private static Project getNewProject(Session s) throws FileNotFoundException {


        Project p = new Project();

        s.clear();
        String projectName = UUID.randomUUID().toString();
        p.setName(projectName);

        Transaction t = s.beginTransaction();

        s.save(p);

        t.commit();

        p = ProjectHelper.getByName(projectName);

        return p;

        


    }

    private static ProjectActionType getNewProjectActionType(Session s) throws FileNotFoundException {
        ProjectActionType pat = new ProjectActionType();

        s.clear();
        String patDescription = UUID.randomUUID().toString();
        pat.setDescription(patDescription);

        Transaction t = s.beginTransaction();

        s.save(pat);

        t.commit();


        pat = ProjectActionTypeHelper.getByDescription(patDescription);

        return pat;
    }


}
