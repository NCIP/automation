security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true
	cacheUsers = false
	
	loginFormUrl = "/"
	defaultTargetUrl = "/cloudClient/index"
	
	loginUserDomainClass = "gov.nih.nci.bda.provisioner.User"
	authorityDomainClass = "gov.nih.nci.bda.provisioner.Role"
	requestMapClass = "gov.nih.nci.bda.provisioner.Requestmap"
	
	useRequestMapDomainClass = false

	requestMapString = """\
	CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
	PATTERN_TYPE_APACHE_ANT
	/=IS_AUTHENTICATED_ANONYMOUSLY
	/login/auth=IS_AUTHENTICATED_ANONYMOUSLY
	/js/**=IS_AUTHENTICATED_ANONYMOUSLY
	/css/**=IS_AUTHENTICATED_ANONYMOUSLY
	/images/**=IS_AUTHENTICATED_ANONYMOUSLY
	/plugins/**=IS_AUTHENTICATED_ANONYMOUSLY
	/**=IS_AUTHENTICATED_FULLY
	"""
}
