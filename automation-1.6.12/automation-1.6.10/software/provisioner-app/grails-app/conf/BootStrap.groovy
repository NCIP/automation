import gov.nih.nci.bda.provisioner.Role
import gov.nih.nci.bda.provisioner.User

class BootStrap {
 def authenticateService 
 
     def init = { servletContext ->
   
		if (!User.findByUsername("admin"))
		{
		
	        def password = authenticateService.encodePassword("password") 
	        def superadmin = new User(username:"admin",userRealName:"Administrator",passwd:password,enabled:true,emailShow:true,description:"admin user",email:"put email here",accessId:"AKIAI53FXM32D5P3LZKA",secretKey:"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").save()
	
	        //create admin role
	        def sudo = new Role(authority:"ROLE_ADMIN",description:"Site Administrator")
	        // now add the User to the role
	         sudo.addToPeople(superadmin)
	        sudo.save()
	
	        new Role(authority:"ROLE_USER",description:"User").save()
        }     
     }
     def destroy = {
     }
} 