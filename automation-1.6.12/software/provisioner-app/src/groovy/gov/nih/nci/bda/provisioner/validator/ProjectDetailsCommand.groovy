
package gov.nih.nci.bda.provisioner.validator;
/**
 *
 * @author Mahidhar Narra
 */

class ProjectDetailsCommand {
   String databaseName
   String databasePassword
   String databaseSystemUser
   String databaseSystemPassword
   String mailServerHostname
   
   static constraints = {
   		   databaseName(blank:false)
           databasePassword(blank:false)
           databaseSystemUser(blank:false)
           databaseSystemPassword(blank:false)
           mailServerHostname(blank:false)           
   }
		
}
