import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper

class CloudClientController {

    def index = { 
    	}
      
     def generateKey = {
    	Provisioner ec2p = new EC2Provisioner();
    	def privateKey = ec2p.generateKey(params.accessId.trim(),params.secretId.trim())
    	redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId,privateKey:privateKey])
	}
	
	def provisionAMI = {
		Provisioner ec2p = new EC2Provisioner();
		println 'ACCESS IN PROVISION' + params.accessId 
		String hostName = ec2p.runInstance(params.accessId.trim(),params.secretId.trim(),params.privateKey.trim())
		ec2p.generateSecurityGroup(params.accessId.trim(),params.secretId.trim(),(ArrayList<String>) InvokerHelper.createList(params.portList.split(",")))		
		ec2p.initializeInstance()
		render(view: 'confirm',model: [hostName:hostName,hudsonUserName:'hudsonuser',hudsonUserPassword:'password'])
	}
}
