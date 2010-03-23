package gov.nih.nci.bda;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import gov.nih.nci.bda.message.ProjectCertificationRequest;
import gov.nih.nci.bda.message.ProjectCertificationResponse;
import org.springframework.ws.client.core.WebServiceTemplate;

public class CertificationClient implements ICertificationClient{

    private WebServiceTemplate webServiceTemplate;

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate)
    {
		this.webServiceTemplate = webServiceTemplate;
    }

    public ProjectCertificationResponse processCertification(String projectName)
    {
		ProjectCertificationRequest request = new ProjectCertificationRequest();
		request.setProjectName(projectName);
		return (ProjectCertificationResponse) webServiceTemplate.marshalSendAndReceive(request);
    }

    public boolean isCertified(String projectName)
    {
		ProjectCertificationResponse response = processCertification(projectName);
		String certificationStatus = response.getProjectCertification().getProduct().getCertificationStatus().getStatus();
		return new Boolean(certificationStatus).booleanValue();

    }
 }