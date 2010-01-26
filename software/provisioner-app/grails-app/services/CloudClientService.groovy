import gov.nih.nci.bda.provisioner.*
import gov.nih.nci.bda.provisioner.util.*
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.mail.MailException

class CloudClientService {
	def mailService
    boolean transactional = true
	static expose = ['jms']
	static destination = "provionerQ"
	static listenerCount = 1
	ProjectConfigurationHelper projectProperties = new ProjectConfigurationHelper()
    
    void sendMessage(params) {
    	Provisioner ec2p = new EC2Provisioner(); 
    	println(params)
    	//println(params.instanceType
    	def accessId=projectProperties.getProperty("access.id")
    	def secretId=projectProperties.getProperty("secret.id")
    	params.accessId = accessId
    	params.secretId = secretId
    	params.instanceType = projectProperties.getProperty("instance.type")
    	params.portList = projectProperties.getProperty(params.projectName+".portlist")
    	
		println 'Generating the Private Key with AccessID ' + accessId + ' and SecretID ' + secretId
		String privateKeyFileName = ec2p.generateKey(accessId.trim(), secretId.trim()); 
		println 'privateKeyFileName ' + privateKeyFileName
		params.privateKeyFileName = privateKeyFileName
		sendQueueJMSMessage("provionerQ",params)
	}
	
 	def validate(params) {
 		Provisioner ec2p = new EC2Provisioner(); 		
		return ec2p.validate(params.accessId.trim(),params.secretId.trim())
	}

 	def listInstances(accessId, secretId, params) {
 		Provisioner ec2p = new EC2Provisioner(); 		
		return ec2p.listAllInstances(accessId,secretId)
	}

 	def terminateInstances(accessId, secretId, String[] instancesTerminating) {
 		Provisioner ec2p = new EC2Provisioner(); 	
		ec2p.terminateInstance(accessId,secretId,instancesTerminating)
	}

	
	void onMessage(msg) 
	{
		try 
		{
			Provisioner ec2p = new EC2Provisioner(); 
			
			def aID = msg.accessId.trim()
			def sId = msg.secretId.trim()
			println 'configure started'
			
			println 'configure complete'

			def defaultPortList = '22,48080'
			def fullPortList 
			if(msg.portList)
			{ 	
				fullPortList =defaultPortList +','+ msg.portList 
			}else
			{
				fullPortList =defaultPortList 
			}
			println 'Adding the ports ' + fullPortList + ' to the Default security group'
			ec2p.generateSecurityGroup(aID,sId,(ArrayList<String>) InvokerHelper.createList(fullPortList.split(",")))
			println 'Creating the AMI with AccessID ' + aID + ' and SecretID ' + sId + 'private key file ' +msg.privateKeyFileName
  			String hostName = ec2p.runInstance(aID,sId,EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home")+"/provisioner-key",msg.privateKeyFileName),msg.instanceType)
			println 'Loading the local.properties'
			loadLocalProperties(msg,hostName)
			println 'Configuring the AMI'
			EC2SystemInitiator si = new EC2SystemInitiator(hostName,System.getProperty("user.home")+"/provisioner-key"+"/"+msg.privateKeyFileName,msg.projectName);			
			si.initializeSystem()
			println 'System Initiation complete'
			confirmationEmail(msg,hostName)	
		} 
		catch (ex) {
			println ("Failed to post:"+ ex)
			if (msg.email) 
			{
				println 'Could not generate the AMI sending Email to '+ msg.email
				mailService.sendMail
				{
					to msg.email
					subject "Instance Status!"
					body """ The provisioner is not able to create the instance with the following error ${ex}. Contact the System Administrator for more details
					"""
				}
			println 'Mail sent  to '+ msg.email	
			}				
		}
	}
    
    def loadLocalProperties(msg,hostName) 
	{ 	
        	PropertyHelper localProperties = new PropertyHelper("resources/local.properties")        	
         	projectProperties.loadProjectConfiguration(msg.projectName,localProperties)

        	for ( e in msg ) {
   				if(e.key != null && e.key.startsWith("projectproperty"))
   				{
   					String propertyKey = e.key.substring(e.key.indexOf('_')+1,e.key.length()) 
   					localProperties.addProperty(propertyKey.replace('_','.'),e.value)
   					
   				}   			
			}
			localProperties.addProperty("grid.server.hostname",hostName)
			localProperties.addProperty("jboss.server.hostname",hostName)
			localProperties.saveConfiguration()
	}
	
	def confirmationEmail(msg,hostName) 
	{
		try
		 {
			if (msg.email) 
			{
			println 'Sending the confirmation Email to '+ msg.email
				mailService.sendMail
				{
					to msg.email
					cc "aws@stelligent.com"					
					subject "Instance Ready (${hostName})"
					body """ caArray Server is ready and configured.
					
					1) To access the caarray application, navigate to the below URL 
					  http://${hostName}:38080/caarray

					"""
				}
			println 'Mail sent  to '+ msg.email		
			}
		}
		catch (MailException ex) {
			println ("Failed to send emails:::"+ ex)
			return false
		}
		
	}	
}
