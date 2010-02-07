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

      
    def saveSystemInfo = {
		SystemInfoCommand systemCmd ->
        if(!systemCmd.hasErrors()) {
        println 'NO ERRORS'
        	cloudClientService.configureSystemInfo(params)
			render(view: 'createInstance',model: [ tabName:'systemInfo' ])
        }
		else
		{
		println 'HAS ERRORS'
			render(view: 'createInstance', model: [ instance: systemCmd,tabName:'systemInfo' ])
		}	
	}     

    def saveScmInfo = {
		ScmInfoCommand scmCmd ->
        if(!scmCmd.hasErrors()) {
        println 'NO ERRORS'
			render(view: 'createInstance',model: [ tabName:'scmRepo' ])
        }
		else
		{
		println 'HAS ERRORS'
			render(view: 'createInstance', model: [ instance: scmCmd,tabName:'scmRepo' ])
		}	
	} 
	
    def saveProjectDetails = {
		ProjectDetailsCommand projectCmd ->
        if(!projectCmd.hasErrors()) {
        println 'NO ERRORS'
			render(view: 'createInstance',model: [ tabName:'projectDetails' ])
        }
		else
		{
		println 'HAS ERRORS'
			render(view: 'createInstance', model: [ instance: projectCmd,tabName:'projectDetails' ])
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
				println 'params.userId:::'+ params.userId
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
	
	def provisionInstance = {CreateInstanceCommand cmd ->
        if(!cmd.hasErrors()) {        
        	cloudClientService.sendMessage(params)
			render(view: 'confirm', model: [ fileName: params.privateKeyFileName ])
        }
		else
		{
			render(view: 'createInstance', model: [ instance: cmd ])
		}
	}

    def listInstances = {
    	if(authenticateService.isLoggedIn())
    	{		
	        def user = authenticateService.userDomain()				
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
	    	println "Params::: ${params}"
			println "Instance Check ${params.instancesTerminating}"
			if(params.instancesTerminating)
				cloudClientService.terminateInstances(params.instancesTerminating)
			redirect(controller: 'cloudClient',action: 'listInstances')
		}		
	}

    def validate = {
    /*  	
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
		*/		
	//render(view: 'createInstance',model: [ tabName:'systemInfo' ])
	render(view: 'applications')
	}	
}
