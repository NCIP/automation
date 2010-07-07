package test.gov.nih.nci.confluence

import gov.nih.nci.confluence.BuildStatusUpdater

import static gov.nih.nci.test.StringAssert.assertStartsWith
import static gov.nih.nci.test.StringAssert.assertEndsWith
import static gov.nih.nci.test.StringAssert.assertContains
import static gov.nih.nci.test.StringAssert.assertDoesNotContain

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Jun 30, 2010
 * Time: 5:41:02 PM
 * To change this template use File | Settings | File Templates.
 * this is a test
 */
class BuildStatusUpdaterTest extends GroovyTestCase {

  final static String PRODUCT = "'PR'";
  final static String CERTIFICATION_STATUS = "'CS'"
  final static String SINGLE_COMMAND_BUILD = "'SCB'"
  final static String SINGLE_COMMAND_DEPLOYMENT = "'SCD'"
  final static String REMOTE_UPGRADE = "'RU'"
  final static String DATABASE_INTEGRATION = "'DI'";
  final static String TEMPLATE_VALIDATION = "'TV'";
  final static String PRIVATE_PROPERTIES = "'PP'";
  final static String CI_BUILD = "'CI'";
  final static String DEPLOYMENT_SHAKEOUT = "'DS'" ;
  final static String BDA_ENABLED = "'BDA'";
  final static String COMMANDLINE_INSTALLER = "'CLI'";

  void testCtor() {
    BuildStatusUpdater target = new BuildStatusUpdater();
    assertNotNull (target);
  }

  void testGetWikiMarkupForRowStartsWithRowMarker() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it starts with a row marker
    assertStartsWith(BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW, actual);

  }

  void testGetWikiMarkupForRowEndsWithRowMarker() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it ends with a row marker
    assertEndsWith( BuildStatusUpdater.WIKI_TABLE_END_ROW, actual);

  }

  void testGetWikiMarkupForRowContainsProduct() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains the Product
    assertContains(PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);


  }

  void testGetWikiMarkupForRowContainsCertificationStatus() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains the Certification status
    assertContains(CERTIFICATION_STATUS.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
  }

  void testGetWikiMarkupForRowContainsBDAEnabled() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains the BDA Enabled status
      assertContains(BDA_ENABLED.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsSingleCommandBuild() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(SINGLE_COMMAND_BUILD.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsSingleCommandDeployment() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(SINGLE_COMMAND_DEPLOYMENT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsRemoteUpgrade() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(REMOTE_UPGRADE.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsDatabaseIntegration() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(DATABASE_INTEGRATION.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsPrivateProperties() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(PRIVATE_PROPERTIES.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsCIBuild() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(CI_BUILD.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsDeploymentShakeout() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(DEPLOYMENT_SHAKEOUT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsCommandLineInstaller() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(COMMANDLINE_INSTALLER.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsTemplateValidation() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
      assertContains(TEMPLATE_VALIDATION.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowHasNoDinks() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER) ;

//    make sure it contains Single Command Build
       assertDoesNotContain("'", actual);

      println actual;
    }

  void testRemoveEndDinks() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.removeEndDinks("'asdkjkasjldfhshadkjf'");

      actual = target.removeEndDinks("'xxxx'");

      assertEquals("xxxx",actual);

    }



}
