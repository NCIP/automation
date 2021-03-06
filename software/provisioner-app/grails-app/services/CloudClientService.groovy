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
    	
    	println(params)
    	//println(params.instanceType
    	def accessId=params.accessId
    	def secretId=params.secretKey
    	Provisioner ec2p = new EC2Provisioner(params.accessId,params.secretKey); 
    	params.instanceType = projectProperties.getProperty("instance.type")
    	params.ebsVolumeSize = projectProperties.getProperty("ebsvolume.size")
    	params.portList = projectProperties.getProperty(params.projectName+".portlist")
    	println 'params.userId::'+params.userId
		println 'Generating the Private Key with AccessID ' + accessId + ' and SecretID ' + secretId
		String privateKeyFileName = ec2p.generateKey(); 
		println 'privateKeyFileName ' + privateKeyFileName
		params.privateKeyFileName = privateKeyFileName
		sendQueueJMSMessage("provionerQ",params)
	}
	
 	def validate(params) {
 		Provisioner ec2p = new EC2Provisioner(params.accessId.trim(),params.secretId.trim()); 		
		return ec2p.validate()
	}

 	def listInstances(userId) {
 		println 'Listing all the instance for userId:'+ userId 
 		InstancesDAO instancesDao = new InstancesDAO(); 		
		return instancesDao.listInstancesByUserId(userId)
	}

 	def terminateInstances(params) {
		Instances instance = new Instances();
		InstancesDAO instancesDao = new InstancesDAO();	
    	def accessId=params.accessId
    	def secretId=params.secretKey
    	println accessId
    	println secretId
    	String[] instancesTerminatingArray
    	if(params.instancesTerminating instanceof java.lang.String)
    	{
    		instancesTerminatingArray = (String[]) params.instancesTerminating.split(',') 
    	}
    	else
    	{
    		instancesTerminatingArray = params.instancesTerminating	
    	}
 		Provisioner ec2p = new EC2Provisioner(accessId,secretId); 	
		ec2p.terminateInstance(instancesTerminatingArray)
	
		for (int i=0;i<instancesTerminatingArray.size();i++){
			instancesDao.updateInstanceStatus(instancesTerminatingArray[i])		
		}

	}

	
	void onMessage(msg) 
	{
		try 
		{
			def aID = msg.accessId.trim()
			def sId = msg.secretKey.trim()
			
			Provisioner ec2p = new EC2Provisioner(aID,sId); 
			Instances instance = new Instances();
			InstancesDAO instancesDao = new InstancesDAO();
			ProjectInitializationDAO projectInitializationDAO = new ProjectInitializationDAO();
						
			

			
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
			ec2p.generateSecurityGroup((ArrayList<String>) InvokerHelper.createList(fullPortList.split(",")))
			println 'Creating the AMI with AccessID ' + aID + ' and SecretID ' + sId + 'private key file ' +msg.privateKeyFileName
  			instance = ec2p.runInstance(EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home")+"/provisioner-key",msg.privateKeyFileName),msg.instanceType,instance)
			println 'Loading the local.properties'
			loadLocalProperties(msg,instance.getInstanceName())
			println 'volume id:'+msg.volumeId
			if(msg.volumeId)
			{
				ec2p.attachCreatedVolume(msg.volumeId,instance.getInstanceId(),"/dev/sdi")
				PropertyHelper localProperties = new PropertyHelper("resources/local.properties")
				localProperties.removeProperty("force.reinstall")
				localProperties.addProperty("nodbintegration","true")
				localProperties.saveConfiguration()	
				projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mkfs.ext3 -F /dev/sdi","false")
				//projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mkdir /mnt/datamnt","false")
				//projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mount -t ext3 /dev/sdi /mnt/datamnt","false")
				projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mysqladmin -u root password mysql","false")
			}
			else
			{
				instance = ec2p.createAttachVolume(msg.ebsVolumeSize,instance)
				PropertyHelper localProperties = new PropertyHelper("resources/local.properties")
				localProperties.removeProperty("nodbintegration")
				localProperties.saveConfiguration()
				projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mkfs.ext3 -F /dev/sdi","true")
				//projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mkdir /mnt/datamnt","true")
				//projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mount -t ext3 /dev/sdi /mnt/datamnt","true")
				projectInitializationDAO.updateRunCommandStatus(msg.projectName,"mysqladmin -u root password mysql","true")							
			}
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
					cc "cbiit-provisioner@stelligent.com"					
					subject "Instance Ready (${hostName})"
					body """The caArray application has been installed and configured and is available at http://${hostName}:38080/caarray
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
