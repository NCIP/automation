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
		System.out.println("args.length :: "+args.length);
		System.out.println("project name  :: "+ args[0]);
		if(args.length != 0 && args.length <= 1)
		{
			System.out.println("Is project Certified  :: "+cc.isCertified(args[0]));
		}else
		{
			System.out.println("usage:: java gov.nih.nci.bda.CertificationClientManager caarray");
			System.exit(1);
		}


	}
}