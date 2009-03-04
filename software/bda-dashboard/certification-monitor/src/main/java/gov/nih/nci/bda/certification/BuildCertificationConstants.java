package gov.nih.nci.bda.certification;

public class BuildCertificationConstants 
{
	public static final String BUILD_FAILED = "FAILED";
	public static final String BUILD_SUCCESSFUL = "SUCCESSFUL";
	public static final String WIKI_SUCCESSFUL = "(/)";
	public static final String WIKI_FAILED = "(x)";
	public static final String WIKI_NOTBUILD = "(off)";
	public static final String ANCHOR_STRING = "#anchor";	
	public static final String ANTHILL_ADDRESS = "http://cbiocvs2.nci.nih.gov:8080/AnthillPro/intranet/";
	public static final int ERROR_MESSAGE_LENGTH = 200;
	public static final String CERTIFICATION_QUERY = "from ProjectCertificationStatus where product like ?";
}
