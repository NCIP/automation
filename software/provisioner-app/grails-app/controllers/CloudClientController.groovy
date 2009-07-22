import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper

class CloudClientController {
	def cloudClientService
    def index = { 
    	}
      
     def generateKey = {
    	Provisioner ec2p = new EC2Provisioner();
    	def privateKey = ec2p.generateKey(params.accessId.trim(),params.secretId.trim())
    	redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId,privateKey:privateKey])
	}
	
	def provisionAMI = {
		cloudClientService.sendMessage(params)	
		render(view: 'confirm')
	}
}
