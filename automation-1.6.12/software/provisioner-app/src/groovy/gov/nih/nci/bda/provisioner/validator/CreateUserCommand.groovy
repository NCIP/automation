
package gov.nih.nci.bda.provisioner.validator;
/**
 *
 * @author Mahidhar Narra
 */
import gov.nih.nci.bda.provisioner.*

class CreateUserCommand {
   String email
   String username
   String userRealName
   String passwd
   String accessId
   String secretKey 
   
   static constraints = {
           email(email: true,blank:false)
           username(blank:false)
           userRealName(blank:false)
           passwd(blank:false)
           accessId(blank:false)
           secretKey(blank:false,
			validator: { secretKey, urc ->
			 	Provisioner ec2p = new EC2Provisioner(urc.accessId.trim(),secretKey); 		
				return ec2p.validate()
			})
   }
		
}
