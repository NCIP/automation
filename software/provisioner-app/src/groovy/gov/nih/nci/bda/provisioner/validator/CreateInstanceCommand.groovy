
package gov.nih.nci.bda.provisioner.validator;
/**
 *
 * @author Mahidhar Narra
 */

class CreateInstanceCommand {

   String email

   
   static constraints = {
           email(email: true,blank:false)
   }
		
}
