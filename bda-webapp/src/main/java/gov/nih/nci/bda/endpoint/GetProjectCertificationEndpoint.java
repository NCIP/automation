package gov.nih.nci.bda.endpoint;

import gov.nih.nci.bda.message.ProjectCertificationRequest;
import gov.nih.nci.bda.message.ProjectCertificationResponse;
import gov.nih.nci.bda.service.ProjectCertificationManager;
import gov.nih.nci.bda.types.ProjectCertification;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;

public class GetProjectCertificationEndpoint extends
		AbstractMarshallingPayloadEndpoint {

	private ProjectCertificationManager projectCertificationManager;

	protected Object invokeInternal(Object requestObject) throws Exception {
		System.out.println("ENTER invokeInternal:::");
		ProjectCertificationRequest request = (ProjectCertificationRequest) requestObject;
		ProjectCertification projectCertification = projectCertificationManager.getProjectCertification(request
				.getProjectName());
		ProjectCertificationResponse response = new ProjectCertificationResponse(projectCertification);
		System.out.println("EXIT invokeInternal:::");
		return response;

	}

	public void setProjectCertificationManager(ProjectCertificationManager projectCertificationManager) {
		this.projectCertificationManager = projectCertificationManager;
	}


}
