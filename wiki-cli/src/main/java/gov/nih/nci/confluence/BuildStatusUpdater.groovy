package gov.nih.nci.confluence

import groovy.sql.Sql

class BuildStatusUpdater {
  def properties = null
  Sql connection = null
  String confluence = null
  public static final String WIKI_TABLE_BEGIN_ROW = "||";
  public static final String WIKI_TABLE_END_ROW = "|| \n";
  public static final String WIKI_TABLE_CELL_TERMINATOR = "|";
  public static final String WIKI_CERTIFICATION_GREEN = "(/)" ;
  public static final String WIKI_CERTIFICATION_RED = "(x)" ;


  static void main(String[] args) {
    BuildStatusUpdater buildStatus = new BuildStatusUpdater();
    buildStatus.loadProperties();
    buildStatus.setDBConnection();
    buildStatus.setDefaultConfluenceString();
    //buildStatus.updateBuildStatus();
    buildStatus.updateCertificationStatusForBDAProjects2();
    buildStatus.updateCertificationStatusForNonBDAProjects2();
    buildStatus.closeDBConnection();
  }

  public void updateBuildStatus() {
    String deploymentStatusTemplateFile = properties.getProperty("deployment-status.template.file");//"Deployment_Status_Template"
    String deploymentStatusTemplateSpace = properties.getProperty("deployment-status.template.space");//"test"
    String deploymentStatusPageFile = properties.getProperty("deployment-status.page.file");//"page1"
    String deploymentStatusPageSpace = properties.getProperty("deployment-status.page.space");//"confluence-cli-1.3.0.jar"

    // get most recent tempates
    doCmd("${confluence} -a getPageSource --space \"" + deploymentStatusTemplateSpace + "\" --title \"" + deploymentStatusTemplateFile + "\" --file _temp.txt")
    String statement = "select PRODUCT,DEV,QA,STAGE,PROD from PROJECT_BUILD_STATUS "


    int count = 0
    connection.eachRow(statement) { row ->
      count++

      String productName = row.PRODUCT;
      String devStatus = row.DEV;
      String qaStatus = row.QA;
      String stageStatus = row.STAGE;
      String prodStatus = row.PROD;

      String findReplace = "--findReplace \"Product${count}:${productName},dev-status${count}:${devStatus},qa-status${count}:${qaStatus},stage-status${count}:${stageStatus},prod-status${count}:${prodStatus}\""

//      println findReplace
      // update page
      doCmd("${confluence} -a storePage --space \"" + deploymentStatusPageSpace + "\" --title \"" + deploymentStatusPageFile + "\"   --file _temp.txt ${findReplace}")
      doCmd("${confluence} -a getPageSource --space \"" + deploymentStatusPageSpace + "\" --title \"" + deploymentStatusPageFile + "\"    --file _temp.txt")
    }
  }

  public void updateCertificationStatusForBDAProjects2() {

    String certificationTemplateFile = properties.getProperty("certification.template2.file");//"Deployment_Status_Template"
    String certificationTemplateSpace = properties.getProperty("certification.template.space");//"test"
    String certificationPageFile = properties.getProperty("certification.page2.file");//"page1"
    String certificationPageSpace = properties.getProperty("certification.page.space");//"confluence-cli-1.3.0.jar"


    String dashboardVersion = properties.getProperty("dashboard.release.version");//"1.0.0"
    String dashboardRevision = properties.getProperty("dashboard.revision.number");//"100"

    String dashboardRelease = "[" + dashboardVersion + "|#anchor|" + dashboardRevision + "]"

    String dashboardTemplateFile = certificationTemplateFile + "_temp.txt";

    // get most recent tempates
    doCmd("${confluence} -a getPageSource --space \""
            + certificationTemplateSpace
            + "\" --title \""
            + certificationTemplateFile
            + "\" --file "
            + dashboardTemplateFile)

    String replacementText = dashboardTableText("select PRODUCT,CERTIFICATION_STATUS,SINGLE_COMMAND_BUILD,SINGLE_COMMAND_DEPLOYMENT,REMOTE_UPGRADE,DATABASE_INTEGRATION,TEMPLATE_VALIDATION,PRIVATE_PROPERTIES,CI_BUILD,BDA_ENABLED,DEPLOYMENT_SHAKEOUT,COMMANDLINE_INSTALLER from PROJECT_CERTIFICATION_STATUS WHERE SUBSTR(BDA_ENABLED,LOCATE('[\',BDA_ENABLED)+1,LOCATE('|',BDA_ENABLED)-3) = '(/)' ORDER BY CERTIFICATION_STATUS");

    FileWriter writer = new FileWriter("x." + dashboardTemplateFile);
    File file = new File(dashboardTemplateFile);
    BufferedReader reader = new BufferedReader(new FileReader(file));


    try {
      String oldline = "";
      String newline = "";

      while ((oldline = reader.readLine()) != null) {
//        println "oldline=" + oldline;
        newline = oldline.replace("|| XXX ||", replacementText) + "\r\n";
//        println "newline=" + newline;

        writer.write(newline);
      }

    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    finally
    {
      reader.close();
      reader.finalize();
      writer.close();
      writer.finalize();
    }

    println "Updating page..." ;

    // update bdafied page
    println "${confluence} -a storePage --space \"" + certificationPageSpace + "\" --title \"" + certificationPageFile + "\"   --file " + "x." + dashboardTemplateFile ;

    doCmd("${confluence} -a storePage --space \"" + certificationPageSpace + "\" --title \"" + certificationPageFile + "\"   --file " + "x." + dashboardTemplateFile) ;

    println "Page updated..." ;
  }

  public void updateCertificationStatusForNonBDAProjects2() {

    String certificationTemplateFile = properties.getProperty("non-bda.template2.file");//"Deployment_Status_Template"
    String certificationTemplateSpace = properties.getProperty("certification.template.space");//"test"
    String certificationPageFile = properties.getProperty("non-bda.page2.file");//"page1"
    String certificationPageSpace = properties.getProperty("certification.page.space");//"confluence-cli-1.3.0.jar"


    String dashboardVersion = properties.getProperty("dashboard.release.version");//"1.0.0"
    String dashboardRevision = properties.getProperty("dashboard.revision.number");//"100"

    String dashboardRelease = "[" + dashboardVersion + "|#anchor|" + dashboardRevision + "]"

    String dashboardTemplateFile = certificationTemplateFile + "_temp.txt";

    // get most recent tempates
    doCmd("${confluence} -a getPageSource --space \""
            + certificationTemplateSpace
            + "\" --title \""
            + certificationTemplateFile
            + "\" --file "
            + dashboardTemplateFile)

    String replacementText = dashboardTableText("select PRODUCT,CERTIFICATION_STATUS,SINGLE_COMMAND_BUILD,SINGLE_COMMAND_DEPLOYMENT,REMOTE_UPGRADE,DATABASE_INTEGRATION,TEMPLATE_VALIDATION,PRIVATE_PROPERTIES,CI_BUILD,BDA_ENABLED,DEPLOYMENT_SHAKEOUT,COMMANDLINE_INSTALLER from PROJECT_CERTIFICATION_STATUS WHERE SUBSTR(BDA_ENABLED,LOCATE('[',BDA_ENABLED)+1,LOCATE('|',BDA_ENABLED)-3) = '(x)' order by product desc");

    FileWriter writer = new FileWriter("x." + dashboardTemplateFile);
    File file = new File(dashboardTemplateFile);
    BufferedReader reader = new BufferedReader(new FileReader(file));


    try {
      String oldline = "";
      String newline = "";

      while ((oldline = reader.readLine()) != null) {
//        println "oldline=" + oldline;
        newline = oldline.replace("|| XXX ||", replacementText) + "\r\n";
//        println "newline=" + newline;

        writer.write(newline);
      }

    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    finally
    {
      reader.close();
      reader.finalize();
      writer.close();
      writer.finalize();
    }

    println "Updating page..." ;

    // update bdafied page
    println "${confluence} -a storePage --space \"" + certificationPageSpace + "\" --title \"" + certificationPageFile + "\"   --file " + "x." + dashboardTemplateFile ;

    doCmd("${confluence} -a storePage --space \"" + certificationPageSpace + "\" --title \"" + certificationPageFile + "\"   --file " + "x." + dashboardTemplateFile) ;

    println "Page updated..." ;
  }


  private String dashboardTableText(String statement) {

    String returnValue = "";

    connection.eachRow(statement) { row ->

      String productString = row.PRODUCT;
      String certificationStatus = row.CERTIFICATION_STATUS;
      String singleCommandBuild = row.SINGLE_COMMAND_BUILD;
      String singleCommandDeployment = row.SINGLE_COMMAND_DEPLOYMENT;
      String databaseIntegration = row.DATABASE_INTEGRATION;
      String remoteUpgrade = row.REMOTE_UPGRADE
      String templateValidation = row.TEMPLATE_VALIDATION;
      String privateProperties = row.PRIVATE_PROPERTIES;
      String ciBuild = row.CI_BUILD;
      String bdaEnabled = row.BDA_ENABLED;
      String deploymentShakeout = row.DEPLOYMENT_SHAKEOUT;
      String commandLineInstaller = row.COMMANDLINE_INSTALLER;

      String productUrl = productString.substring(productString.indexOf("|") + 1, productString.indexOf("]"));
      String productName = productString.substring(productString.indexOf("[") + 1, productString.indexOf("|"));
      String replaceProductString = null
      String replaceBdaEnabledString = null

      boolean isReachable = isReachble(productUrl)

      if (isReachable) {
        replaceProductString = "'[" + productName + "|" + productUrl + "]'";
      }
      else {
        replaceProductString = "'[{color:red}" + productName + "{color}|" + productUrl + "]'";
      }

      if (!checkValiedBdaRevision(bdaEnabled)) {
        if (bdaEnabled != null && bdaEnabled.length() != 0 && !bdaEnabled.substring(bdaEnabled.indexOf("[") + 1, bdaEnabled.indexOf("|")).equals("(x)")) {
          replaceBdaEnabledString = bdaEnabled.replace(bdaEnabled.substring(bdaEnabled.indexOf("[") + 1, bdaEnabled.indexOf("|")), "(!)");
        }
        else {
          replaceBdaEnabledString = bdaEnabled;
        }
      }
      else {
        replaceBdaEnabledString = bdaEnabled;
      }

      String thisRowText = getWikiMarkupForRow(
              replaceProductString, replaceBdaEnabledString, certificationStatus, singleCommandBuild, singleCommandDeployment, databaseIntegration, remoteUpgrade, templateValidation, privateProperties, ciBuild, deploymentShakeout, commandLineInstaller);

      returnValue += thisRowText;

    }

    return returnValue;
  }

  public void updateCertificationStatus() {

    String certificationTemplateFile = properties.getProperty("certification.template.file");//"Deployment_Status_Template"
    String certificationTemplateSpace = properties.getProperty("certification.template.space");//"test"
    String certificationPageFile = properties.getProperty("certification.page.file");//"page1"
    String certificationPageSpace = properties.getProperty("certification.page.space");//"confluence-cli-1.3.0.jar"


    String dashboardVersion = properties.getProperty("dashboard.release.version");//"1.0.0"
    String dashboardRevision = properties.getProperty("dashboard.revision.number");//"100"

    String dashboardRelease = "[" + dashboardVersion + "|#anchor|" + dashboardRevision + "]"

    String certifiedPageTemporaryFile = "certified_temp.txt";

    // get most recent templates
    retrieveTemplate(certificationTemplateSpace, certificationTemplateFile, certifiedPageTemporaryFile)

    String statement = "select PRODUCT,CERTIFICATION_STATUS,SINGLE_COMMAND_BUILD"
    +",SINGLE_COMMAND_DEPLOYMENT,REMOTE_UPGRADE,DATABASE_INTEGRATION"
    +",TEMPLATE_VALIDATION,PRIVATE_PROPERTIES,CI_BUILD,BDA_ENABLED"
    +",DEPLOYMENT_SHAKEOUT,COMMANDLINE_INSTALLER"
    +" from PROJECT_CERTIFICATION_STATUS"
    +" WHERE SUBSTR(BDA_ENABLED,LOCATE('[\',BDA_ENABLED)+1,LOCATE('|',BDA_ENABLED)-3) = '(/)'"
    +"ORDER BY CERTIFICATION_STATUS desc"

    List projectRows = connection.rows(statement)


    int count = projectRows.size()

    println "Updating status for BDA projects"

    connection.eachRow(statement) { row ->

      String productString = row.PRODUCT;
      String certificationStatus = row.CERTIFICATION_STATUS;
      String singleCommandBuild = row.SINGLE_COMMAND_BUILD;
      String singleCommandDeployment = row.SINGLE_COMMAND_DEPLOYMENT;
      String databaseIntegration = row.DATABASE_INTEGRATION;
      String remoteUpgrade = row.REMOTE_UPGRADE
      String templateValidation = row.TEMPLATE_VALIDATION;
      String privateProperties = row.PRIVATE_PROPERTIES;
      String ciBuild = row.CI_BUILD;
      String bdaEnabled = row.BDA_ENABLED;
      String deploymentShakeout = row.DEPLOYMENT_SHAKEOUT;
      String commandLineInstaller = row.COMMANDLINE_INSTALLER;

      String productUrl = productString.substring(productString.indexOf("|") + 1, productString.indexOf("]"));
      String productName = productString.substring(productString.indexOf("[") + 1, productString.indexOf("|"));
      String replaceProductString = null
      String replaceBdaEnabledString = null

      boolean isReachable = isReachble(productUrl)
      if (isReachable) {
        replaceProductString = "'[" + productName + "|" + productUrl + "]'";
      }
      else {
        replaceProductString = "'[{color:red}" + productName + "{color}|" + productUrl + "]'";
      }

      if (!checkValiedBdaRevision(bdaEnabled)) {
        if (bdaEnabled != null && bdaEnabled.length() != 0 && !bdaEnabled.substring(bdaEnabled.indexOf("[") + 1, bdaEnabled.indexOf("|")).equals("(x)")) {
          replaceBdaEnabledString = bdaEnabled.replace(bdaEnabled.substring(bdaEnabled.indexOf("[") + 1, bdaEnabled.indexOf("|")), "(!)");
        }
        else {
          replaceBdaEnabledString = bdaEnabled;
        }
      }
      else {
        replaceBdaEnabledString = bdaEnabled;
      }

      String test = getWikiMarkupForRow(
              productString, bdaEnabled, certificationStatus, singleCommandBuild, singleCommandDeployment, databaseIntegration, remoteUpgrade, templateValidation, privateProperties, ciBuild, deploymentShakeout, commandLineInstaller);

//      println "line=" + test;


      String findReplace = "--findReplace \"Product${count}:${replaceProductString},Certification-Status${count}:${certificationStatus},Single-Command-Build${count}:${singleCommandBuild},Single-Command-Deployment${count}:${singleCommandDeployment},Database-Integration${count}:${databaseIntegration},Remote-Upgrade${count}:${remoteUpgrade}, Template-Validation${count}:${templateValidation},Private-Properties${count}:${privateProperties},CI-Build${count}:${ciBuild},BDA-Enabled${count}:${replaceBdaEnabledString},Deployment-Shakeout${count}:${deploymentShakeout},CommandLine-Installer${count}:${commandLineInstaller}\""

      println "Replace String -->" + findReplace
      // update bdafied page

      doCmd("${confluence} -a storePage --space \""
              + certificationPageSpace
              + "\" --title \""
              + certificationPageFile
              + "\"   --file "
              + certifiedPageTemporaryFile
              + " ${findReplace}")

      doCmd("${confluence} -a getPageSource --space \""
              + certificationPageSpace
              + "\" --title \""
              + certificationPageFile
              + "\"    --file "
              + certifiedPageTemporaryFile)

      count--
    }

    // Update the release version
    String findReplaceVersion = "--findReplace \"DashboardReleaseVersion:${dashboardRelease}\""
    doCmd("${confluence} -a storePage --space \""
            + certificationPageSpace
            + "\" --title \""
            + certificationPageFile
            + "\"   --file "
            + certifiedPageTemporaryFile
            + " ${findReplaceVersion}")
  }

  private Process retrieveTemplate(String certificationTemplateSpace, String certificationTemplateFile, String saveAs) {
    return doCmd("${confluence} -a getPageSource --space \""
            + certificationTemplateSpace
            + "\" --title \""
            + certificationTemplateFile
            + "\" --file "
            + saveAs)
  }


  private String getWikiMarkupForRow(String product, String bdaEnabled, String certification, String singleCommandBuild, String singleCommandDeploy, String databaseIntegration, String remoteUpgrade, String templateValidation, String privateProperties, String ciBuild, String deploymentShakeout, String commandLineInstaller) {

    String returnValue = WIKI_TABLE_BEGIN_ROW;

    certification = WIKI_CERTIFICATION_GREEN ;

    if (bdaEnabled.contains(WIKI_CERTIFICATION_RED)
        || singleCommandBuild.contains(WIKI_CERTIFICATION_RED)
        || singleCommandDeploy.contains(WIKI_CERTIFICATION_RED)
        || remoteUpgrade.contains(WIKI_CERTIFICATION_RED)
        || databaseIntegration.contains(WIKI_CERTIFICATION_RED)
        || privateProperties.contains(WIKI_CERTIFICATION_RED)
        || deploymentShakeout.contains(WIKI_CERTIFICATION_RED)
        || templateValidation.contains(WIKI_CERTIFICATION_RED)
        || ciBuild.contains(WIKI_CERTIFICATION_RED)
        || commandLineInstaller.contains(WIKI_CERTIFICATION_RED)
    ) {

      certification = WIKI_CERTIFICATION_RED ;
    }

    returnValue += removeEndDinks(product) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(certification) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(bdaEnabled) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(singleCommandBuild) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(singleCommandDeploy) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(remoteUpgrade) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(databaseIntegration) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(privateProperties) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(deploymentShakeout) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(templateValidation) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(ciBuild) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += removeEndDinks(commandLineInstaller) + WIKI_TABLE_CELL_TERMINATOR;
    returnValue += WIKI_TABLE_END_ROW;

    returnValue = returnValue.replace("\"", "'");

    return returnValue;
  }

  private String removeEndDinks(String product) {

    if (product.startsWith("'")) {
      product = product.substring(1);
    }

    if (product.endsWith("'")) {
      product = product.substring(0, product.length() - 1);
    }
    return product
  }

  private boolean isReachble(String projectRepoUrl) {
    try {
      URL url = new URL(projectRepoUrl);
      System.out.println("Testing to see if URL connects::" + projectRepoUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      System.out.println("Created HttpURLConnection object");
      conn.connect();
      System.out.println("connecting..");
      int code = conn.getResponseCode();
      if (code >= 400) {
        return false;
      }
      System.out.println("disconnecting..");
      conn.disconnect();
      System.out.println("disconnected");
      return true;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private double getMajorMinorRevision(String bdaVersion) {
    String str = bdaVersion.substring(0, bdaVersion.lastIndexOf("."));
    return Double.valueOf(str);
  }

  private boolean checkValiedBdaRevision(String bdaEnabledStr) {
    try {
      if (bdaEnabledStr != null && bdaEnabledStr.length() != 0 && bdaEnabledStr.substring(bdaEnabledStr.indexOf("[") + 1, bdaEnabledStr.indexOf("|")).equals("(/)")) {
        String str = bdaEnabledStr.substring(bdaEnabledStr.lastIndexOf('|') + 1, bdaEnabledStr.lastIndexOf(']'));
        if (getMajorMinorRevision(str) < Double.valueOf(properties.getProperty("bda.version.check"))) {
          return false;
        }
        return true;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  public void loadProperties() {
    properties = new java.util.Properties();
    properties.load(new java.io.FileInputStream("wiki.properties"));
  }

  public void setDefaultConfluenceString() {
    String wikiServer = properties.getProperty("wiki.server");//"http://localhost:8080"
    String wikiUser = properties.getProperty("wiki.user");//"automation"
    String wikiPassword = properties.getProperty("wiki.password");//"password"
    String concluenceCliArtifact = properties.getProperty("concluence.cli.artifact");//"confluence-cli-1.3.0.jar"

    confluence = "java -jar " + concluenceCliArtifact + " --server " + wikiServer + " --user " + wikiUser + " --password " + wikiPassword
  }

  public void setDBConnection() {
    String monitorDb = properties.getProperty("database.name");
    String monitorUrl = properties.getProperty("database.url");//"jdbc:mysql://localhost/wikiDB"
    String monitorDriver = properties.getProperty("database.driver");//"org.gjt.mm.mysql.Driver"
    String monitorUser = properties.getProperty("database.user");//"wikiuser"
    String monitorPassword = properties.getProperty("database.password");//"password"

    connection = Sql.newInstance(monitorUrl, monitorUser, monitorPassword, monitorDriver)
  }

  public void closeDBConnection() {
    connection.close();
  }

  static void doCmdWithException(String cmd) {

    Process process = doCmd(cmd)

    if (process.exitValue() != 0) {
      println "ERROR VALUE: ${process.exitValue()}"
      println "ERROR TEXT:  ${process.err.text}"
      throw new Exception("Command failure code: ${process.exitValue()}\n ${process.err.text()}\n ${cmd}")
    }
    return
  }

  static Process doCmd(String cmd) {
    //println ">>> ${cmd}"
    def cmdStr
    def osName = System.getProperty("os.name")
    println " OS-NAME:: $osName "

    if (osName.startsWith("Win"))
      cmdStr = "cmd /c ${cmd}"
    else
      cmdStr = ["sh", "-c", "${cmd}"]

    Process process = cmdStr.execute()
    println "${process.text} ${process.err.text}"
    return process

  }

}
