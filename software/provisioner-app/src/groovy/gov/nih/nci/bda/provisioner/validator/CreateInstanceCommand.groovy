
package gov.nih.nci.bda.provisioner.validator;
/**
 *
 * @author Mahidhar Narra
 */

class CreateInstanceCommand {

   String email

   
   static constraints = {
     email(validator: { val, obj ->
	   		if ( !obj.email ) 
	   		{
	     		return ['Invalid Email Address']
	   		}           
   		})
	}	
}
