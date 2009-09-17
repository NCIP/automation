package gov.nih.nci.bda;

import org.apache.tools.ant.BuildException;

public class ApplicationVersion extends org.apache.tools.ant.Task {

	private String version;
	private String buildTag;
	private String buildNumber;	
	private String buildDateTime;	

	public void setBuildTag(String buildTag) {
		this.buildTag = buildTag;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public void setBuildDateTime(String buildDateTime) {
		this.buildDateTime = buildDateTime;
	}

	public void execute() throws BuildException {
	  try {
		  System.out.println("IN THE ApplicationVersion Ant task. version=" + version + " buildTag=" + 
			  buildTag + " buildNumber=" + buildNumber + " buildDateTime=" + buildDateTime);
	  } catch (Exception e) {
	    throw new BuildException(e);
	  }
	}
}
