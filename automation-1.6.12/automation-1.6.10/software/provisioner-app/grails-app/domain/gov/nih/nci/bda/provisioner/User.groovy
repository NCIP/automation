package gov.nih.nci.bda.provisioner

import gov.nih.nci.bda.provisioner.Role

/**
 * User domain class.
 */
class User {
	static transients = ['pass','aId','sKey']
	static hasMany = [authorities: Role]
	static belongsTo = Role

	/** Username */
	String username
	/** User Real Name*/
	String userRealName
	/** MD5 Password */
	String passwd
	/** enabled */
	boolean enabled

	String email
	boolean emailShow
	String accessId 
	String secretKey 

	/** description */
	String description = ''

	/** plain password to create a MD5 password */
	String pass = '[secret]'
	String aId = '[secret]'
	String sKey = '[secret]'



	static constraints = {
		username(blank: false, unique: true)
		userRealName(blank: false)
		accessId(blank: false)
		secretKey(blank: false)
		passwd(blank: false)
		email(nullable: true, blank: true)
		enabled()
	}
}
