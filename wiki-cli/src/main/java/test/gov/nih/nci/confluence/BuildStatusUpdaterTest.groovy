package test.gov.nih.nci.confluence

import gov.nih.nci.confluence.BuildStatusUpdater

import static gov.nih.nci.test.StringAssert.assertStartsWith
import static gov.nih.nci.test.StringAssert.assertEndsWith
import static gov.nih.nci.test.StringAssert.assertContains

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Jun 30, 2010
 * Time: 5:41:02 PM
 * To change this template use File | Settings | File Templates.
 * this is a test
 */
class BuildStatusUpdaterTest extends GroovyTestCase {

  void testCtor() {
    BuildStatusUpdater target = new BuildStatusUpdater();
    assertNotNull (target);
  }

  void testGetWikiMarkupForRow() {
    BuildStatusUpdater target = new BuildStatusUpdater();


    String PRODUCT = "[caEHR-ESD|http://caehrorg.jira.com/svn/ESD/trunk]";

    String CERTIFICATION_STATUS = '(/)'
    String SINGLE_COMMAND_BUILD = '[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successful]'
    String SINGLE_COMMAND_DEPLOYMENT = '[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successful]'
    String REMOTE_UPGRADE = '[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successful]'
    String DATABASE_INTEGRATION = "[(+)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature is Optional]";

    String TEMPLATE_VALIDATION = "[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successful]";

    String PRIVATE_PROPERTIES = "[(+)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature is Optional]";

    String CI_BUILD = "[(/)|http://ci.caehr.net:48080/hudson/job/ESD_Full_Deploy/241/|Build #241(Jun 30, 2010 5:59:20 PM)Jun 30, 2010 8:56:11 PM</span><a href=\"http://hudson-ci.org/\">Hudson ver. 1.362</a></td></tr></table></body></html>]";
    String DEPLOYMENT_SHAKEOUT = "[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successful]" ;

    String BDA_ENABLED = "[(/)|http://gforge.nci.nih.gov/svnroot/commonlibrary/trunk/ivy-repo/ncicb/bda-utils/1.6.3|1.6.3]";

    String COMMANDLINE_INSTALLER = "[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successful]";

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;


//    make sure it starts with a row marker
    assertStartsWith("|| ", actual);

//    make sure it contains the Product
    assertContains(PRODUCT + " | ", actual);

//    make sure it contains the Certification status
    assertContains(CERTIFICATION_STATUS + " | ", actual);

//    make sure it contains the BDA Enabled status
    assertContains(BDA_ENABLED + " | ", actual);

//    make sure it contains Single Command Build
    assertContains(SINGLE_COMMAND_BUILD + " | ", actual);

//    make sure it ends with a row marker
    assertEndsWith(" ||", actual);

  }
}
