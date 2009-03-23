package gov.nih.nci.bda.certification;

import gov.nih.nci.bda.certification.domain.TargetLookup;
import gov.nih.nci.bda.certification.listener.SingleCommandListener;
import gov.nih.nci.bda.certification.util.HibernateUtil;
import gov.nih.nci.bda.certification.util.PropertyLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

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

	public static boolean projectCertificationStatus = true;

	public void certifyProjects(String projectName) {

		System.out.println("Certifing project :" + projectName);

		Session session = HibernateUtil.getSession();

		Project project = new Project();
		project.init();

		DefaultLogger logger = new DefaultLogger();
		logger.setMessageOutputLevel(Project.MSG_INFO);
		logger.setErrorPrintStream(System.err);
		logger.setOutputPrintStream(System.out);
		project.addBuildListener(logger);

		PropertyLoader.loadProjectProperties(projectName, project);
		PropertyLoader.loadGeneralProperties(project);

		ArrayList optionalFeaturesList = getListOfOptionalFeaturesForProject(
				projectName, project);

		SingleCommandListener scListener = new SingleCommandListener();
		project.addBuildListener(scListener);

		File buildFile = new File("build/build.xml");

		ProjectHelper helper = ProjectHelper.getProjectHelper();
		project.addReference("ant.projectHelper", helper);

		helper.parse(project, buildFile);

		session.beginTransaction();
		Query query = session.createQuery(" from TargetLookup ");

		Iterator targets = query.iterate();

		while (targets.hasNext()) {
			TargetLookup targetLookup = (TargetLookup) targets.next();
			System.out.println("targetName::" + targetLookup.getMapName()
					+ "::MAPNAME::" + targetLookup.getTargetName()
					+ "::projectName::" + projectName + "::ISVALUE::"
					+ targetLookup.getIsValue());

			populateAditionalAntProperties(targetLookup, project,
					optionalFeaturesList);

			try {
				project.executeTarget(targetLookup.getTargetName());
			} catch (Exception e) {
				System.out.println("EXCEPTION::::" + e.getMessage());
			}
		}

		session.close();
		System.out.println("FINISH THE EXECUTE METHOD");
	}

	private void populateAditionalAntProperties(TargetLookup targetLookup,
			Project project, ArrayList optionalFeaturesList) {
		project.setProperty("map.name", targetLookup.getMapName());
		project.setProperty("executed.target.name", targetLookup
				.getTargetName());

		if (targetLookup.getIsValue() != null
				&& targetLookup.getIsValue().equals("true")) {
			project.setProperty("is.value", "true");
		} else {
			project.setProperty("is.value", "false");
			project.setProperty("certification.property.value", "");
		}

		if (optionalFeaturesList.contains(targetLookup.getTargetName())) {
			System.out.println("CHECK THE OPTIONAL"
					+ targetLookup.getTargetName());
			project.setProperty("is.optional", "true");
		} else {
			project.setProperty("is.optional", "false");
		}

		if (targetLookup.getIsOptional() != null
				&& targetLookup.getIsOptional().equals("true")) {
			project.setProperty("is.optional", "true");
		}

	}

	private ArrayList getListOfOptionalFeaturesForProject(String projectName,
			Project project) {
		ArrayList optionalList = new ArrayList();
		String optionalStr = project.getProperty(projectName
				+ ".optional.features");
		if (optionalStr != null) {
			String[] result = optionalStr.split(",");
			for (int i = 0; i < result.length; i++) {
				System.out.println("ADDING OPTION LIST " + i + " : "
						+ result[i]);
				optionalList.add(result[i]);
			}
		}
		return optionalList;
	}

	public static void main(String args[]) {
		String projectName = null;
		System.out.println("args.length::" + args.length);
		if (args.length != 1) {
			System.out.println("Enter the project name for certification");
			System.exit(0);
		} else {
			projectName = args[0];
		}

		CertificationManager cm = new CertificationManager();
		cm.certifyProjects(projectName);
	}

}
