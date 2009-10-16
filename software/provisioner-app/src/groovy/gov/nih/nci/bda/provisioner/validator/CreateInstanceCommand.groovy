
package gov.nih.nci.bda.provisioner.validator;
/**
 *
 * @author Mahidhar Narra
 */

class CreateInstanceCommand {

   String email
   String projectSCMUrl
   String projectBuildTargets
   String projectBuildFile
   
   static constraints = {
           email(email: true,blank:false)
           projectSCMUrl(url: true, blank:false)
           projectBuildTargets(blank:false)
           projectBuildFile(blank:false)
   }
		
}
