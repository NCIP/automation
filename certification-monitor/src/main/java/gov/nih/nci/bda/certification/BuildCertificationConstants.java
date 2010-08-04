package gov.nih.nci.bda.certification;

/**
 * @author narram
 *
 */
public class BuildCertificationConstants {
	public static final String ANCHOR_STRING = "#anchor";
	public static final String ANTHILL_ADDRESS = "http://cbiocvs2.nci.nih.gov:8080/AnthillPro/intranet/";
	public static final String BUILD_FAILED = "FAILED";
	public static final String BUILD_SUCCESSFUL = "SUCCESSFUL";
	public static final String CERTIFICATION_QUERY = "from ProjectCertificationStatus where product like ?";
	public static final String CI_SERVER_JOB_PREFIX = "certify";
	public static final String CI_SERVER_LASTBUILD_CONSOLE = "lastBuild/console";
	public static final String CI_SERVER_NAME = "ci.server.name";
	public static final int ERROR_MESSAGE_LENGTH = 400;
	public static final String WIKI_FAILED = "(x)";
	public static final String WIKI_NOTBUILD = "(off)";
	public static final String WIKI_SUCCESSFUL = "(/)";
	public static final String WIKI_OPTIONAL = "(+)";
	public static final String WIKI_SYSTEMS_WAIVER = "(on)";
	public static final String OPTIONAL_MESSAGE = "Feature is Optional";
	public static final String WAIVER_MESSAGE = "Systems team Waiver";
	public static final String SUCCESSFUL_MESSAGE = "Feature implementation is successful";
    public static final String CERTIFICATION_INCOMPLETE = "Status unknown; certification build incomplete (canceled or failed)." ;
}
