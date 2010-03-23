package gov.nih.nci.bda;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import gov.nih.nci.bda.message.ProjectCertificationResponse;


public class CertificationClientManager
{

	public static void main( String[] args ) throws Exception
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
		CertificationClient cc = (CertificationClient) applicationContext.getBean( "certificationClient" );
		System.out.println("Is project Certified  :: "+cc.isCertified("caarray"));

	}
}