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

  final static String PRODUCT = "[caEHR-ESD|http://caehrorg.jira.com/svn/ESD/trunk]";
  final static String CERTIFICATION_STATUS = '(/)'
  final static String SINGLE_COMMAND_BUILD = '[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successfulSCB]'
  final static String SINGLE_COMMAND_DEPLOYMENT = '[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successful]'
  final static String REMOTE_UPGRADE = '[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successfulREMOTE]'
  final static String DATABASE_INTEGRATION = "[(+)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature is OptionalDATABASE]";
  final static String TEMPLATE_VALIDATION = "[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successfulTEMPLATE]";
  final static String PRIVATE_PROPERTIES = "[(+)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature is OptionalPRIVATE_PROPERTIES]";
  final static String CI_BUILD = "[(/)|http://ci.caehr.net:48080/hudson/job/ESD_Full_Deploy/241/|Build #241(Jun 30, 2010 5:59:20 PM)Jun 30, 2010 8:56:11 PM</span><a href=\"http://hudson-ci.org/\">Hudson ver. 1.362</a></td></tr></table></body></html>]";
  final static String DEPLOYMENT_SHAKEOUT = "[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successfulDEPLOYMENT_SHAKEOUT]" ;
  final static String BDA_ENABLED = "[(/)|http://gforge.nci.nih.gov/svnroot/commonlibrary/trunk/ivy-repo/ncicb/bda-utils/1.6.3|1.6.3]";
  final static String COMMANDLINE_INSTALLER = "[(/)|http://cbvapp-c1006.nci.nih.gov:48080/hudson/job/certify-caEHR-ESD/lastBuild/console|Feature implementation is successfulINSTALLER]";

  void testCtor() {
    BuildStatusUpdater target = new BuildStatusUpdater();
    assertNotNull (target);
  }

  void testGetWikiMarkupForRowStartsWithRowMarker() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it starts with a row marker
    assertStartsWith("|| ", actual);

  }

  void testGetWikiMarkupForRowEndsWithRowMarker() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it ends with a row marker
    assertEndsWith(" ||", actual);

  }

  void testGetWikiMarkupForRowContainsProduct() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains the Product
    assertContains(PRODUCT + " | ", actual);


  }

  void testGetWikiMarkupForRowContainsCertificationStatus() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains the Certification status
    assertContains(CERTIFICATION_STATUS + " | ", actual);
  }

  void testGetWikiMarkupForRowContainsBDAEnabled() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains the BDA Enabled status
      assertContains(BDA_ENABLED + " | ", actual);
    }

  void testGetWikiMarkupForRowContainsSingleCommandBuild() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(SINGLE_COMMAND_BUILD + " | ", actual);
    }

  void testGetWikiMarkupForRowContainsSingleCommandDeployment() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(SINGLE_COMMAND_DEPLOYMENT + " | ", actual);
    }

  void testGetWikiMarkupForRowContainsRemoteUpgrade() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(REMOTE_UPGRADE + " | ", actual);
    }

  void testGetWikiMarkupForRowContainsDatabaseIntegration() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(DATABASE_INTEGRATION + " | ", actual);
    }

  void testGetWikiMarkupForRowContainsPrivateProperties() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(PRIVATE_PROPERTIES + " | ", actual);
    }
}
