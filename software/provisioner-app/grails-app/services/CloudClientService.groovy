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
			println 'ACCESS IN PROVISION' + msg.accessId 
  			String hostName = ec2p.runInstance(msg.accessId,msg.secretId,msg.privateKey)
			ec2p.generateSecurityGroup(msg.accessId,msg.secretId,(ArrayList<String>) InvokerHelper.createList(msg.portList.split(",")))		
			ec2p.initializeInstance()
			confirmationEmail(msg,hostName)  		
		} 
		catch (ex) {
			println ("Failed to post:"+ ex)
		}
	}

	def confirmationEmail(msg,hostName) 
	{
		try
		 {
			if (msg.email) 
			{
				mailService.sendMail
				{
					to msg.email
					subject "Continuous Integration Server Ready!"
					body """
					Continuous Integration Server is ready and configured.
					1) Open a new command prompt and type ssh hudsonuser@${hostName}
					2) When prompted Are you sure you want to continue connecting?, type yes
					3) When prompted, the password is password
					4) From the command line, type ./hudson/application/apache-tomcat-5.5.20/bin/startup.sh
					5) To view the build status, go to your web browser and type http://${hostName}:48080/hudson/?auto_refresh=true
					6) After successful build/deployment (after ~10 minutes), start the cai2 container by going back to your command prompt (where you're connected to EC2) and type ./apps/cai2-dac/jboss-4.0.5.GA/bin/run.sh
					7) After waiting for JBoss to start, open your web browser and type the following to launch the CAI2 application: http://${hostName}:46210/caintegrator2/
					Contact administrator if you are experiencing difficulties accessing the host.
					"""
				}
			}
		}
		catch (MailException ex) {
			println ("Failed to send emails:::"+ ex)
			return false
		}
		
	}	
}
