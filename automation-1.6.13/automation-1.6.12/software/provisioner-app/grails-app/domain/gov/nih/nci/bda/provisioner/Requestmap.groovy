package gov.nih.nci.bda.provisioner

/**
 * Request Map domain class.
 */
class Requestmap {

	String url
	String configAttribute

	static constraints = {
		url(blank: false, unique: true)
		configAttribute(blank: false)
	}
}
