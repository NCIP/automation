package test.gov.nih.nci.confluence

import gov.nih.nci.confluence.BuildStatusUpdater

import static gov.nih.nci.test.StringAssert.assertStartsWith
import static gov.nih.nci.test.StringAssert.assertEndsWith
import static gov.nih.nci.test.StringAssert.assertContains
import static gov.nih.nci.test.StringAssert.assertDoesNotContain
import gov.nih.nci.confluence.ErrorValidator
import gov.nih.nci.bda.certification.util.HibernateUtil
import org.hibernate.Session
import gov.nih.nci.bda.domain.ProjectActionHelper
import gov.nih.nci.bda.domain.ProjectAction
import gov.nih.nci.bda.domain.Project
import org.hibernate.Transaction

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Jun 30, 2010
 * Time: 5:41:02 PM
 * To change this template use File | Settings | File Templates.
 * this is a test
 */
class ErrorValidatorTest extends GroovyTestCase {

  protected void setUp() {
    super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
    HibernateUtil.setConnectionUrl("jdbc:mysql://localhost/test");
  }




  void testLogMessageToProjectHistory() {
    ErrorValidator target = new ErrorValidator();
    String projectName = UUID.randomUUID().toString();

    Project p = new Project();
    p.setName(projectName);

    Session s = HibernateUtil.getSession();

    Transaction t = s.beginTransaction();

    s.save(p);

    t.commit();

    s.clear();

    ArrayList recipientList = new ArrayList();

    recipientList.add("tim.bassett@stelligent.com");
    recipientList.add("tim.bassett@nih.gov");

    String subject = "this is the subject";

    String messageText = "this is the message text";


    target.logMessageToProjectHistory(projectName,recipientList,subject,messageText);


    ProjectAction pa =  ProjectActionHelper.getLatestProjectAction(projectName);

    assertNotNull(pa);


  }
}
