import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

class CloudClientController {
	def cloudClientService
	
    def index = { 
		session.accessId=null
		session.secretId=null
    }
     
      
     def generateKey = {
    	Provisioner ec2p = new EC2Provisioner();
    	def privateKey1 = "KEYFILE"
    	//ec2p.generateKey(params.accessId.trim(),params.secretId.trim())
    	//redirect(controller: 'cloudClient',action: 'createInstance',params: [accessId:params.accessId,secretId:params.secretId,privateKey:privateKey])
    	render(view: 'createInstance',model: [privateKey:'privateKey1'])
	}
	
	def provisionInstance = {
		println "Using access id ${session.accessId} and Secret key ${session.secretId}"
		
		if(params.email)
		{	
			cloudClientService.sendMessage(session.accessId,session.secretId,params)
			println "IN PROVISION INSTANCE" + params.privateKeyFileName
			//return [fileName: fileName]	
			render(view: 'confirm', model: [ fileName: params.privateKeyFileName ])
		}
		else
		{
			flash.message = "Enter a valid email address"
			render(view: 'createInstance')
		}
	}

    def listInstances = {
		def listAllInstances = cloudClientService.listInstances(session.accessId,session.secretId,params)	
		return [listAllInstances: listAllInstances]
		render(view: 'listInstances')
	}	
	
    def downloadKey = {
    	println params.privateKeyFileName
	    def file = new File(System.getProperty("user.home")+"/provisioner-key/"+params.privateKeyFileName)    
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
		response.outputStream << file.newInputStream() 
	}	
	

    def terminate = {
    	println "Params::: ${params}"
		println "Instance Check ${params.instancesTerminating}"
		if(params.instancesTerminating)
			cloudClientService.terminateInstances(session.accessId,session.secretId,params.instancesTerminating)
		redirect(controller: 'cloudClient',action: 'listInstances')		
	}

    def validate = {  	
    	if(!session.accessId && !session.secretId)
    	{
    	println "Using access id ${params.accessId} and Secret key ${params.secretId}"
			if(params.accessId && params.secretId)
			{	
				if(cloudClientService.validate(params))
				{	
					session.accessId=params.accessId
					session.secretId=params.secretId
					render(view: 'createInstance')
				}else
				{
					flash.message = "Authentication with AWS Failed. Either Access Key ID  or Secret Access Key is invalid. You must have a valid AWS EC2 account."
					redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId])
				}					
			}
			else
			{
				flash.message = "Access Key ID  and Secret Access Key cannot be empty."
				render(view: 'index')
			}
		}else
		{
				if(cloudClientService.validate(session))
				{	
					render(view: 'createInstance')
				}else
				{
					flash.message = "Authentication with AWS Failed. Either Access Key ID  or Secret Access Key is invalid. You must have a valid AWS EC2 account."
					redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId])
				}
		}		
				
	}			
	
}
