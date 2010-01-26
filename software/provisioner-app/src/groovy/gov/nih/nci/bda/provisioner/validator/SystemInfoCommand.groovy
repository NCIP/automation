
package gov.nih.nci.bda.provisioner.validator;
/**
 *
 * @author Mahidhar Narra
 */

class SystemInfoCommand {

   String email
   String portList

   
   static constraints = {
           email(email: true,blank:false)
           portList(blank:false)
   }
		
}
