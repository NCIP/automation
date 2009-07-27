import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.mail.MailException

class CloudClientService {
	def mailService
    boolean transactional = true
	static expose = ['jms']
	static destination = "provionerQ"
	static listenerCount = 1
    
    void sendMessage(params) {
    	def msg = [userId: 'narra',content: 'narram content']
		sendQueueJMSMessage("provionerQ",params)
	}
	

	void onMessage(msg) 
	{
		try 
		{
			Provisioner ec2p = new EC2Provisioner();
			def aID = msg.accessId.trim()
			def sId = msg.secretId.trim()		
			println 'Generating the Private Key with AccessID ' + aID + ' and SecretID ' + sId
			String privateKeyFileName = ec2p.generateKey(aID, sId); 
		
			def defaultPortList = '22,48080,46210'
			def fullPortList 
			if(msg.portList)
			{ 	
				fullPortList =defaultPortList +','+ msg.portList 
			}else
			{
				fullPortList =defaultPortList 
			}
			println 'Adding the ports ' + fullPortList + 'to the Default security group'
			ec2p.generateSecurityGroup(aID,sId,(ArrayList<String>) InvokerHelper.createList(fullPortList.split(",")))
			println 'Creating the AMI with AccessID ' + aID + ' and SecretID ' + sId + 'private key file ' +privateKeyFileName
  			String hostName = ec2p.runInstance(aID,sId,EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home"),privateKeyFileName))
			println 'Configuring the AMI'	
			EC2SystemInitiator si = new EC2SystemInitiator(hostName,System.getProperty("user.home")+"/"+privateKeyFileName);			
			si.initializeSystem()
			
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
					subject "Continuous Integration Server Ready!"
					body """
					There was a problem generating the AMI. Contact your administrator for more details.			
					"""
				}
			println 'Mail sent  to '+ msg.email	
			}				
		}
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
					subject "Continuous Integration Server Ready!"
					body """
					Continuous Integration Server is ready and configured.
					
					1) To begin your application build, launch the Hudson CI server by going to your web browser and typing 
					  http://${hostName}:48080/hudson/?auto_refresh=true
					2) After successful build/deployment (about 15 minutes), open your web browser and type the following to launch the application: 
					  http://${hostName}:46210/caintegrator2/
					
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