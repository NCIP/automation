import gov.nih.nci.bda.provisioner.*
import gov.nih.nci.bda.provisioner.util.*
import org.codehaus.groovy.runtime.InvokerHelper
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;
import gov.nih.nci.bda.provisioner.validator.CreateInstanceCommand;
import gov.nih.nci.bda.provisioner.validator.SystemInfoCommand;
import gov.nih.nci.bda.provisioner.validator.ScmInfoCommand;
import gov.nih.nci.bda.provisioner.validator.ProjectDetailsCommand;


class CloudClientController {
	def cloudClientService
	def authenticateService 
	 
    def index = { 

		println 'index..'
    	if(authenticateService.isLoggedIn())
    	{
    	    def user = authenticateService.userDomain()
    		render(view: 'applications')
    	}
    }
	
    def provisionSystem = {
		CreateInstanceCommand cmd ->
		if(authenticateService.isLoggedIn())
    	{		
	        if(!cmd.hasErrors()) 
	        {
	        	def user = authenticateService.userDomain()
				
				params.userId = user.id
				params.accessId = user.accessId
    	        params.secretKey = user.secretKey				
				println 'params.userId:::'+ params.userId
				println 'params.accessId:::'+ params.accessId
				println 'params.secretId:::'+ params.secretKey
				println 'user.id:::'+ user.id
	        	cloudClientService.sendMessage(params)
				render(view: 'confirm', model: [ fileName: params.privateKeyFileName ])
	        }
			else
			{
				def viewName = params.projectName+'_createInstance'
				println viewName
				render(view: viewName, model: [ instance: cmd ])
			}
		}			 
		
	}       

    def isApplicationAuthorised = {
    	if(authenticateService.isLoggedIn())
    	{
			def viewName = params.projectName+'_createInstance'
			println viewName
			render(view: viewName)
		}
	}       
	
    def listInstances = {
    	if(authenticateService.isLoggedIn())
    	{		
	        def user = authenticateService.userDomain()
	        println user.accessId
	        println user.secretKey				
			def listAllInstances = cloudClientService.listInstances((int) user.id)	
			return [listAllInstances: listAllInstances]
			render(view: 'listInstances')
		}
	}	
	
    def downloadKey = {
        if(authenticateService.isLoggedIn())
    	{
	    	println params.privateKeyFileName
		    def file = new File(System.getProperty("user.home")+"/provisioner-key/"+params.privateKeyFileName)    
			response.setContentType("application/octet-stream")
			response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
			response.outputStream << file.newInputStream()
		} 
	}	
	

    def terminate = {
        if(authenticateService.isLoggedIn())
    	{
    		def user = authenticateService.userDomain()
	    	params.accessId = user.accessId
    	    params.secretKey = user.secretKey
    	   	println params.accessId
	        println params.secretKey	
			println "Instance Check ${params.instancesTerminating}"
			if(params.instancesTerminating)
				cloudClientService.terminateInstances(params)
			redirect(controller: 'cloudClient',action: 'listInstances')
		}		
	}

    def validate = {
		//render(view: 'createInstance',model: [ tabName:'systemInfo' ])
		render(view: 'applications')
	}	
}
