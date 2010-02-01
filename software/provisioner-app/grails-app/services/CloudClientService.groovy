import gov.nih.nci.bda.provisioner.*
import gov.nih.nci.bda.provisioner.util.*
import gov.nih.nci.bda.provisioner.domain.*
import gov.nih.nci.bda.provisioner.dao.*
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
    	println 'params.userId::'+params.userId
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

 	def listInstances(userId) {
 		println 'Listing all the instance for userId:'+ userId 
 		InstancesDAO instancesDao = new InstancesDAO(); 		
		return instancesDao.listInstancesByUserId(userId)
	}

 	def terminateInstances(String[] instancesTerminating) {
		Instances instance = new Instances();
		InstancesDAO instancesDao = new InstancesDAO();	
 	    def accessId=projectProperties.getProperty("access.id")
    	def secretId=projectProperties.getProperty("secret.id")
 		Provisioner ec2p = new EC2Provisioner(); 	
		ec2p.terminateInstance(accessId,secretId,instancesTerminating)
		for (int i=0;i<instancesTerminating.size();i++){
			instancesDao.updateInstanceStatus(instancesTerminating[i])		
		}
	}

	
	void onMessage(msg) 
	{
		try 
		{
			Provisioner ec2p = new EC2Provisioner(); 
			Instances instance = new Instances();
			InstancesDAO instancesDao = new InstancesDAO();			
			
			def aID = msg.accessId.trim()
			def sId = msg.secretId.trim()
			
			println 'configure started'
			println 'msg.userId::'+msg.userId
			instance.setUserId((int) msg.userId)
			instance.setProjectName(msg.projectName)
			instance.setInstanceType(msg.instanceType)
			
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
  			instance = ec2p.runInstance(aID,sId,EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home")+"/provisioner-key",msg.privateKeyFileName),msg.instanceType,instance)
			println 'Loading the local.properties'
			loadLocalProperties(msg,instance.getInstanceName())
			println 'Configuring the AMI' + instance.getInstanceName()
			EC2SystemInitiator si = new EC2SystemInitiator(instance.getInstanceName(),System.getProperty("user.home")+"/provisioner-key"+"/"+msg.privateKeyFileName,msg.projectName);			
			si.initializeSystem()
			println 'System Initiation complete'
			instance.setInstanceStatus("RUNNING")
			println 'Saving instance information'
			instancesDao.saveInstance(instance)
			println 'Sending confirmation email'
			confirmationEmail(msg,instance.getInstanceName())	
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
					subject "Instance Ready (${hostName})"
					body """caArray Server is configured and ready.
To access the caarray application, navigate to the below URL. 
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
