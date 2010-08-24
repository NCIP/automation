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

    private static Project getNewProject() throws FileNotFoundException {

        Session s = HibernateUtil.getSession();


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

    public static ProjectActionType getNewProjectActionType() throws FileNotFoundException {

        Session s = HibernateUtil.getSession();

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

    public static ProjectAction getTestProjectAction() throws FileNotFoundException {
        ProjectAction pa = new ProjectAction();
        pa.setProject(ProjectActionTest.getNewProject());
        pa.setDate(new Date());
        pa.setNotes(UUID.randomUUID().toString());
        pa.setType(getNewProjectActionType());
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(pa);
        t.commit();
        return pa;
    }

    public void testGetMostRecentByProjectName() throws Exception {
        Calendar c = Calendar.getInstance();
        Date older = c.getTime();
        c.add(Calendar.DATE,5);
        Date newer = c.getTime();

        HibernateUtil.setConnectionUrl("jdbc:mysql://localhost/test");
        Session s = HibernateUtil.getSession();
        s.clear();

        ProjectActionType pat = getNewProjectActionType();
        Project p = getNewProject();

        ProjectAction paOlder = new ProjectAction();
        paOlder.setType(pat);
        paOlder.setDate(older);
        paOlder.setProject(p);

        ProjectAction paNewer = new ProjectAction();
        paNewer.setType(pat);
        paNewer.setDate(newer);
        paNewer.setProject(p);

        Transaction t = s.beginTransaction();

        s.save(paOlder);
        s.save(paNewer);

        t.commit();

        ProjectAction latest = ProjectActionHelper.getLatestProjectAction(p.getName());

        assertNotNull(latest);
        assertEquals(paNewer.getDate(),latest.getDate());
        assertEquals(paNewer.getId(),latest.getId());
        assertEquals(paNewer.getProject(),latest.getProject());
        assertEquals(paNewer.getType(),latest.getType());

        

    }


    public void testDateWithNoTimeThrowException() {

        IllegalArgumentException caught = null;
        ProjectAction target = new ProjectAction();

        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND,0);
        d.setTime(c.getTime().getTime());

        try
        {
            target.setDate(d);
        }
        catch(IllegalArgumentException ex)
        {
            caught = ex;
        }

        assertNotNull(caught);
    }




}
