import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

class CloudClientController {
	def cloudClientService
	
	static navigation = [
		[group:'tabs', action:'index', title: 'Create Instance', order: 0],
		[action: 'listInstances', title: 'List Instances', order: 1]
	]
	
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
				flash.message = "Authentication with AWS Failed. Either Access Key ID  or Secret Access Key is invalid. You must have a valid AWS EC2 account."
				redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId])
			}				
		}else
		{
			flash.message = "Access Key ID  and Secret Access Key cannot be empty."
			render(view: 'index')
		}
	}

    def listInstances = {
		def listAllInstances = cloudClientService.listInstances(params)	
		return [listAllInstances: listAllInstances]
		render(view: 'listInstances')
	}	

    def terminate = {
    	println "Params::: ${params}"
		println "Instance Check ${params.instancesTerminating}"
		if(params.instancesTerminating)
			cloudClientService.terminateInstances(params.instancesTerminating)
		redirect(controller: 'cloudClient',action: 'listInstances')		
	}	
	
}
