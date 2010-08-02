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
  final static String CERTIFICATION_STATUS = "'(/)'"
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

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it starts with a row marker
    assertStartsWith(BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW, actual);

  }

  void testGetWikiMarkupForRowEndsWithRowMarker() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it ends with a row marker
    assertEndsWith( BuildStatusUpdater.WIKI_TABLE_END_ROW, actual);

  }

  void testGetWikiMarkupForRowContainsProduct() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains the Product
    assertContains(PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);


  }

  void testGetWikiMarkupForRowContainsCertificationStatus() {
    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains the Certification status
    assertContains(CERTIFICATION_STATUS.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
  }

  void testGetWikiMarkupForRowContainsBDAEnabled() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains the BDA Enabled status
      assertContains(BDA_ENABLED.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsSingleCommandBuild() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(SINGLE_COMMAND_BUILD.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsSingleCommandDeployment() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(SINGLE_COMMAND_DEPLOYMENT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsRemoteUpgrade() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(REMOTE_UPGRADE.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsDatabaseIntegration() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(DATABASE_INTEGRATION.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsPrivateProperties() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(PRIVATE_PROPERTIES.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsCIBuild() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(CI_BUILD.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsDeploymentShakeout() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(DEPLOYMENT_SHAKEOUT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsCommandLineInstaller() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(COMMANDLINE_INSTALLER.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowContainsTemplateValidation() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

//    make sure it contains Single Command Build
      assertContains(TEMPLATE_VALIDATION.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR, actual);
    }

  void testGetWikiMarkupForRowHasNoDinks() {
      BuildStatusUpdater target = new BuildStatusUpdater();

      String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

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

  void testGetWikiMarkupForRowContainsGreenCertification() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(PRODUCT, BDA_ENABLED, CERTIFICATION_STATUS, SINGLE_COMMAND_BUILD, SINGLE_COMMAND_DEPLOYMENT, DATABASE_INTEGRATION, REMOTE_UPGRADE, TEMPLATE_VALIDATION, PRIVATE_PROPERTIES, CI_BUILD, DEPLOYMENT_SHAKEOUT, COMMANDLINE_INSTALLER, null) ;

    String productNoDinks = PRODUCT.replace("'","");

    assertStartsWith( BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW
                      + productNoDinks
                      + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR
                      + BuildStatusUpdater.WIKI_CERTIFICATION_GREEN
                      , actual );
    }

  void testGetWikiMarkupForRowContainsRedCertificationForBDAEnabled() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForSingleCommandDeployment() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForSingleCommandBuild() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForDatabaseIntegration() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForRemoteUpgrade() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForTemplateValidation() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForPrivateProperties() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForDeploymentShakeout() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForCIBuild() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED
            , DEPLOYMENT_SHAKEOUT
            , COMMANDLINE_INSTALLER, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  void testGetWikiMarkupForRowContainsRedCertificationForCommandLineInstaller() {

    BuildStatusUpdater target = new BuildStatusUpdater();

    String actual = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , DEPLOYMENT_SHAKEOUT
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED, null) ;

    String startsWithExpected = BuildStatusUpdater.WIKI_TABLE_BEGIN_ROW + PRODUCT.replace("'","") + BuildStatusUpdater.WIKI_TABLE_CELL_TERMINATOR + BuildStatusUpdater.WIKI_CERTIFICATION_RED ;

    assertStartsWith( startsWithExpected, actual );

    }

  // proves that curlies are escaped
  void testCurliesAreEscaped() {

    BuildStatusUpdater target = new BuildStatusUpdater();


    String wiki = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , "This has a { curly in it }."
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED, new Date()) ;

    int curlyAt = wiki.indexOf("{");

    String actual = wiki.substring(curlyAt-1,curlyAt) ;

    assertEquals( "\\", actual );
    
    curlyAt = wiki.indexOf("}");

    actual = wiki.substring(curlyAt-1,curlyAt) ;

    assertEquals( "\\", actual );

  }

  // proves that brackets are escaped
  void testBracketAreEscaped() {

    BuildStatusUpdater target = new BuildStatusUpdater();


    String wiki = target.getWikiMarkupForRow(
            PRODUCT
            , BDA_ENABLED
            , CERTIFICATION_STATUS
            , SINGLE_COMMAND_BUILD
            , SINGLE_COMMAND_DEPLOYMENT
            , DATABASE_INTEGRATION
            , REMOTE_UPGRADE
            , TEMPLATE_VALIDATION
            , PRIVATE_PROPERTIES
            , CI_BUILD
            , "This has a [ bracket in it ]."
            , BuildStatusUpdater.WIKI_CERTIFICATION_RED, new Date()) ;

    int curlyAt = wiki.indexOf("[");

    String actual = wiki.substring(curlyAt-1,curlyAt) ;

    assertEquals( "\\", actual );

    curlyAt = wiki.indexOf("]");

    actual = wiki.substring(curlyAt-1,curlyAt) ;

    assertEquals( "\\", actual );

  }
}
