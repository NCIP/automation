import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper

class CloudClientController {
	def cloudClientService
	
	static constraints = {
		accessId(matches: '[0-9]{7}[A-Za-z]')
	}
	
    def index = { 
    	}
     
      
     def generateKey = {
    	Provisioner ec2p = new EC2Provisioner();
    	def privateKey = ec2p.generateKey(params.accessId.trim(),params.secretId.trim())
    	redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId,privateKey:privateKey])
	}
	
	def provisionAMI = {
		println "Using access id ${params.accessId} and Secret key ${params.secretId}"
		if(params.accessId && params.secretId)
		{	
			if(cloudClientService.validate(params))
			{	
				cloudClientService.sendMessage(params)	
				render(view: 'confirm')
			}else
			{
				flash.message = "Authentication with AWS Failed. Either Access Key ID  or Secret Access Key is invalid."
				redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId])
			}				
		}else
		{
			flash.message = "Access Key ID  and Secret Access Key cannot be empty."
			render(view: 'index')
		}
	}
}
