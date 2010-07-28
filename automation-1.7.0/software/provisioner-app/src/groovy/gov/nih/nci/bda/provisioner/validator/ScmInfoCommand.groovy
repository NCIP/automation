
package gov.nih.nci.bda.provisioner.validator;
/**
 *
 * @author Mahidhar Narra
 */

class ScmInfoCommand  {

   String projectSCMUrl
   String projectBuildTargets
   String projectBuildFile
   
   static constraints = {
           projectSCMUrl(url: true, blank:false)
           projectBuildTargets(blank:false)
           projectBuildFile(blank:false)
   }
		
}
