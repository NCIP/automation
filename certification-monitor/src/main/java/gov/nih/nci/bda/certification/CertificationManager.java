package gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.domain.ProjectCertificationStatus;
import gov.nih.nci.bda.certification.domain.ProjectCertificationStatusHelper;
import gov.nih.nci.bda.certification.domain.TargetLookup;
import gov.nih.nci.bda.certification.listener.SingleCommandListener;
import gov.nih.nci.bda.certification.util.HibernateUtil;
import gov.nih.nci.bda.certification.util.PropertyLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author narram
 */
public class CertificationManager {

    private Log certLogger = LogFactory.getLog(CertificationManager.class);
    public static boolean projectCertificationStatus = true;

    public static void main(String args[]) throws ConfigurationException, FileNotFoundException {
        String projectName = null;
        if (args.length != 1) {
            System.out.println("Enter the project name for certification");
            System.exit(0);
        } else {
            projectName = args[0];
        }

        CertificationManager cm = new CertificationManager();
        cm.certifyProjects(projectName);
    }

    public void markAllAsFailed(String projectName, String projectUrl) {

        ProjectCertificationStatus status = ProjectCertificationStatusHelper.getProject(projectName,projectUrl) ;

        status.setBdaEnabled( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setCertificationStatus( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setCommandLineInstaller( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setDatabaseIntegration( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setDeploymentShakeout( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setLatestCIBuild( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setPrivateRepositoryProperties( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setRemoteUpgrade( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setSingleCommandBuild( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setSingleCommandDeployment( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
        status.setTemplateValidation( BuildCertificationHelper.getWikiLinkTip( BuildCertificationConstants.WIKI_FAILED,  projectName, BuildCertificationConstants.CERTIFICATION_INCOMPLETE) );
    }

    public void certifyProjects(String projectName) throws ConfigurationException, FileNotFoundException {

        certLogger.info("Certifing project :" + projectName);

        certLogger.info("geting the session");
        Session session = HibernateUtil.getSession();

        certLogger.info("create an ant project");
        Project project = new Project();
        project.init();

        certLogger.info("creating a default log and add to the project");
        DefaultLogger logger = new DefaultLogger();
        logger.setMessageOutputLevel(Project.MSG_INFO);
        logger.setErrorPrintStream(System.err);
        logger.setOutputPrintStream(System.out);
        project.addBuildListener(logger);

        certLogger.info("load all the properties from the database for the project :" + projectName);
        PropertyLoader.loadProjectProperties(projectName, project);

        String svnUrl = project.getProperty("svn.project.url").toString();

        this.markAllAsFailed(projectName,svnUrl);



        certLogger.info("load all the general properties from the database");
        PropertyLoader.loadGeneralProperties(project);

        certLogger.info("load all the features that are optional for the project");
        ArrayList<String> optionalFeaturesList = getListOfOptionalFeaturesForProject(project);

        certLogger.info("load all the System Waivers for the project");
        ArrayList<String> systemWaiversList = getListOfSystemWaiversForProject(project);

        certLogger.info("Add the SingleCommandListener");
        SingleCommandListener scListener = new SingleCommandListener();
        project.addBuildListener(scListener);

        File buildFile = new File("build/build.xml");
        ProjectHelper helper = ProjectHelper.getProjectHelper();
        project.addReference("ant.projectHelper", helper);
        helper.parse(project, buildFile);

        certLogger.info("Get the list of targets or features for the certification");
        session.beginTransaction();
        Query query = session.createQuery(" from TargetLookup ");
        Iterator<Object> targets = query.iterate();

        String userHome = project.getProperty("user.home");
        certLogger.info("certifyProjects: user.home=" + userHome);
        String filePath = userHome + "/" + projectName + ".saved";

        while (targets.hasNext()) {
            TargetLookup targetLookup = (TargetLookup) targets.next();

            certLogger.info("TargetName : " + targetLookup.getTargetName()
                    + " :: MapName : " + targetLookup.getMapName()
                    + " :: ProjectName::" + projectName
                    + " :: IsValue : " + targetLookup.getIsValue()
                    + " :: IsOptional : " + targetLookup.getIsOptional()
            );

            for (String thisProperty : targetLookup.SaveExpressions()) {
                certLogger.info("SaveExpressions:" + thisProperty);
            }

            populateAditionalAntProperties(targetLookup, project,
                    optionalFeaturesList, systemWaiversList);

            if (targetLookup.getSaveProperties() != null && targetLookup.getSaveProperties().trim() != "") {
                certLogger.info("project.setProperty(\"gov.nih.nci.bda.certification.listener.TaskListener.properties.to.save\"=" + targetLookup.getSaveProperties());
                project.setProperty("gov.nih.nci.bda.certification.listener.TaskListener.properties.to.save", targetLookup.getSaveProperties());
                project.setProperty("gov.nih.nci.bda.certification.listener.TaskListener.propertysavefile", filePath);
            } else if (targetLookup.getUseProperties() != null && targetLookup.getUseProperties().trim() != "") {
                this.loadSavedProperties(filePath, project, targetLookup.getUseProperties());
            }

            try {
                project.executeTarget(targetLookup.getTargetName());

            } catch (Exception e) {
                certLogger.error("Exception occurred while executing the target " + targetLookup.getTargetName() + " ::::" + e.getMessage());
            }
        }

        session.close();
        certLogger.info("Certification Complete");
    }

    public void loadSavedProperties(String filePath, Project project, String useProperties) throws ConfigurationException {

        File configFile = new File(filePath);

        if (configFile.exists()) {

            PropertiesConfiguration config = new PropertiesConfiguration(configFile);
            certLogger.info("loadSavedProperties:config.load()");
            config.load();

            java.util.Iterator keys = config.getKeys();
            certLogger.info("loadSavedProperties:config.getKeys().hasNext()=" + keys.hasNext());


            while (keys.hasNext()) {

                String key = keys.next().toString();
                certLogger.info("loadSavedProperties:key=" + key);
                String value = config.getString(key);
                certLogger.info("loadSavedProperties:value=" + value);
                project.setProperty(key, value);
            }

            project.setProperty("gov.nih.nci.bda.certification.CertificationManager.useproperties", useProperties);
        }
        else
        {
            certLogger.info("loadSavedProperties: No file '" + filePath +"'");
        }
    }

    private ArrayList<String> getListOfOptionalFeaturesForProject(Project project) {
        ArrayList<String> optionalList = new ArrayList<String>();
        String optionalStr = project.getProperty("optional.features");
        certLogger.info("Features that are optional : " + optionalStr);
        if (optionalStr != null) {
            String[] result = optionalStr.split(",");
            for (int i = 0; i < result.length; i++) {
                certLogger.info("Adding Optional feature " + i + " : "
                        + result[i]);
                optionalList.add(result[i]);
            }
        }

        return optionalList;
    }

    private ArrayList<String> getListOfSystemWaiversForProject(Project project) {
        ArrayList<String> optionalList = new ArrayList<String>();
        String optionalStr = project.getProperty("systems.waiver");
        certLogger.info("Systems Team Waifers : " + optionalStr);
        if (optionalStr != null) {
            String[] result = optionalStr.split(",");
            for (int i = 0; i < result.length; i++) {
                certLogger.info("Adding Systems Waiver " + i + " : "
                        + result[i]);
                optionalList.add(result[i]);
            }
        }
        return optionalList;
    }

    private void populateAditionalAntProperties(TargetLookup targetLookup,
                                                Project project, ArrayList<String> optionalFeaturesList, ArrayList<String> systemWaiversList) {

        project.setProperty("map.name", targetLookup.getMapName());
        project.setProperty("executed.target.name", targetLookup.getTargetName());

        certLogger.info("Check if the feature has status to update or has a value ");

        if (targetLookup.getIsValue() != null
                && targetLookup.getIsValue().equals("true")) {
            project.setProperty("is.value", "true");
        } else {
            project.setProperty("is.value", "false");
            project.setProperty("certification.property.value", "");
        }

        certLogger.info("Check if the current feature for this project is optional ");

        if (optionalFeaturesList.contains(targetLookup.getTargetName())) {
            certLogger.info("Set is.optional to true");
            project.setProperty("is.optional", "true");
        } else {
            certLogger.info("Set is.optional to false");
            project.setProperty("is.optional", "false");
        }

        certLogger.info("Check if the current feature for this project is optional ");
        if (systemWaiversList.contains(targetLookup.getTargetName())) {
            certLogger.info("Set is.systems.waiver to true");
            project.setProperty("is.systems.waiver", "true");
        } else {
            certLogger.info("Set is.systems.waiver to false");
            project.setProperty("is.systems.waiver", "false");
        }
        certLogger.info("Check if the feature is optional for all projects");

        if (targetLookup.getIsOptional() != null
                && targetLookup.getIsOptional().equals("true")) {
            project.setProperty("is.optional", "true");
        }

    }

}
