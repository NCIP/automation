import gov.nih.nci.bda.provisioner.*
import org.codehaus.groovy.runtime.InvokerHelper

class CloudClientController {

    def index = { 
    	//redirect(action: "cloudClient")
    	}
      
     def generateKey = {
    	Provisioner ec2p = new EC2Provisioner();
    	def aId ='AKIAI53FXM32D5P3LZKA'
    	def sId ='kKQUD+i1jhMgom4hU9RTxxoRS/WEvgsA5v4H4DCY'
    	//println params.accessId 
    	//println params.secretId
    	//println("Status :" +ec2p.generateKey(params.accessId,params.secretId));
    	def privateKey = ec2p.generateKey(params.accessId,params.secretId)
    	redirect(controller: 'cloudClient',action: 'index',params: [accessId:params.accessId,secretId:params.secretId,privateKey:privateKey])
	}
	
	def provisionAMI = {
		Provisioner ec2p = new EC2Provisioner();
		println 'ACCESS IN PROVISION' + params.accessId 
    	println 'SECRET IN PROVISION' + params.secretId
    	println 'PRIVATE IN PROVISION' + params.privateKey
    	println 'Ports IN PROVISION' + params.portList
		String hostName = ec2p.runInstance(params.accessId,params.secretId,params.privateKey)
		ec2p.generateSecurityGroup(params.accessId,params.secretId,(ArrayList<String>) InvokerHelper.createList(params.portList.split(",")))		
		ec2p.initializeInstance()
		//redirect(action: 'index',params: [accessId:params.accessId,secretId:params.secretId,portList:params.portList])
		render(view: 'staticView',params: [hostName:"narram.nci.nih.gov"):
	}
	     
}
