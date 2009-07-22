import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper
class CloudClientService {
	def mailService
    boolean transactional = true
	static expose = ['jms']
	static destination = "provionerQ"
	static listenerCount = 5
    
    void sendMessage(params) {
    	def msg = [userId: 'narra',content: 'narram content']
		sendQueueJMSMessage("provionerQ",params)
	}
	

	void onMessage(msg) 
	{
		try 
		{
			Provisioner ec2p = new EC2Provisioner();
			println 'ACCESS IN PROVISION' + msg.accessId 
  			String hostName = ec2p.runInstance(msg.accessId,msg.secretId,msg.privateKey)
			ec2p.generateSecurityGroup(msg.accessId,msg.secretId,(ArrayList<String>) InvokerHelper.createList(msg.portList.split(",")))		
			ec2p.initializeInstance()
			confirmationEmail(msg,'hostName.nci.nih.gov')  		
		} 
		catch (t) {
			log.error "Error adding post ", t
		}
	}

	def confirmationEmail(msg,hostName) 
	{
	if (msg.email) {
		mailService.sendMail
			{
				to msg.email
				subject "Continuous Integration Server Ready!"
				body """
				Continuous Integration Server is Ready and configured. GO CRAZY!!!!.
				The hostname is ${hostName}.
				The username is 'hudsonuser' and password is 'password'.
				Contract administrator if you are experiencing difficulties accessing the host.
				"""
			}
		
		}
	}	
}
