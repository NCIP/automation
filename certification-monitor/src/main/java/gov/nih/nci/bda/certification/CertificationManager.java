package gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.domain.TargetLookup;
import gov.nih.nci.bda.certification.listener.SingleCommandListener;
import gov.nih.nci.bda.certification.util.HibernateUtil;
import gov.nih.nci.bda.certification.util.PropertyLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author narram
 *
 */
public class CertificationManager {

	private Log certLogger = LogFactory.getLog(CertificationManager.class);
	public static boolean projectCertificationStatus = true;

	public static void main(String args[]) {
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

	public void certifyProjects(String projectName) {

		certLogger.info("Certifing project :" + projectName);

		certLogger.info("geting the session" );
		Session session = HibernateUtil.getSession();

		certLogger.info("create an ant project" );
		Project project = new Project();
		project.init();

		certLogger.info("creating a default log and add to the project" );
		DefaultLogger logger = new DefaultLogger();
		logger.setMessageOutputLevel(Project.MSG_INFO);
		logger.setErrorPrintStream(System.err);
		logger.setOutputPrintStream(System.out);
		project.addBuildListener(logger);

		certLogger.info("load all the properties from the database for the project :" + projectName);
		PropertyLoader.loadProjectProperties(projectName, project);

		certLogger.info("load all the general properties from the database" );
		PropertyLoader.loadGeneralProperties(project);

		certLogger.info("load all the features that are optional for the project" );
		ArrayList<String> optionalFeaturesList = getListOfOptionalFeaturesForProject(
				projectName, project);

		certLogger.info("Add the SingleCommandListener" );
		SingleCommandListener scListener = new SingleCommandListener();
		project.addBuildListener(scListener);

		File buildFile = new File("build/build.xml");
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		project.addReference("ant.projectHelper", helper);
		helper.parse(project, buildFile);

		certLogger.info("Get the list of targets or features for the certification" );
		session.beginTransaction();
		Query query = session.createQuery(" from TargetLookup ");
		Iterator<Object> targets = query.iterate();

		while (targets.hasNext()) {
			TargetLookup targetLookup = (TargetLookup) targets.next();

			certLogger.info("TargetName : " + targetLookup.getTargetName()
					+ " :: MapName : " + targetLookup.getMapName()
					+ " :: ProjectName::" + projectName
					+ " :: IsValue : "	+ targetLookup.getIsValue()
					+ " :: IsOptional : "	+ targetLookup.getIsOptional()
					);

			populateAditionalAntProperties(targetLookup, project,
					optionalFeaturesList);

			try {
				project.executeTarget(targetLookup.getTargetName());
			} catch (Exception e) {
				certLogger.error("Exception occured while executing the target " + targetLookup.getTargetName()+ " ::::" + e.getMessage());
			}
		}

		session.close();
		certLogger.info("Certification Complete");
	}

	private ArrayList<String> getListOfOptionalFeaturesForProject(String projectName,
			Project project) {
		ArrayList<String> optionalList = new ArrayList<String>();
		String optionalStr = project.getProperty(projectName
				+ ".optional.features");
		certLogger.info("Optional Property : " +  optionalStr);
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

	private void populateAditionalAntProperties(TargetLookup targetLookup,
			Project project, ArrayList<String> optionalFeaturesList) {
		project.setProperty("map.name", targetLookup.getMapName());
		project.setProperty("executed.target.name", targetLookup
				.getTargetName());

		certLogger.info("Check if the feature has status to update or has a value ");
		if (targetLookup.getIsValue() != null
				&& targetLookup.getIsValue().equals("true")) {
			project.setProperty("is.value", "true");
		} else {
			project.setProperty("is.value", "false");
			project.setProperty("certification.property.value", "");
		}

		certLogger.info("Check if the current feature for this project is optional ");
		certLogger.info("optionalFeaturesList:: "+optionalFeaturesList.toArray());
		if (optionalFeaturesList.contains(targetLookup.getTargetName())) {
			certLogger.info("Set is.optional to true");
			project.setProperty("is.optional", "true");
		} else {
			certLogger.info("Set is.optional to false");
			project.setProperty("is.optional", "false");
		}

		certLogger.info("Check if the feature is optional for all projects");
		if (targetLookup.getIsOptional() != null
				&& targetLookup.getIsOptional().equals("true")) {
			project.setProperty("is.optional", "true");
		}

	}

}
