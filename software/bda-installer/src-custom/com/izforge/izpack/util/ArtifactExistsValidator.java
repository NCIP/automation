package com.izforge.izpack.util;

import java.util.Map;
import java.io.File;

/**
 * A validator to enforce that a specified artifact exists on the filesystem.
 * <p/>
 * This validator will check that the specified artifact exists on the filesystem.
 *
 * @author Douglas Harley <harleyda@mail.nih.gov>
 */
public class ArtifactExistsValidator implements com.izforge.izpack.panels.Validator
{

	static {
		// this is a test comment
		System.out.println("ArtifactExistsValidator class is loaded!");
	}

    private static final String ARTIFACT_PATH_PARAM = "artifactPath";
    private static final String IS_DIR_PARAM = "isDir";

    public boolean validate(com.izforge.izpack.panels.ProcessingClient client) {
		System.out.println("starting validate(com.izforge.izpack.panels.ProcessingClient)...");
		boolean artifactExists = false;
		try {
			if(!client.hasParams()) {
				throw new RuntimeException("Required params missing: 'artifactPath', 'isDir'.");
			}
			Map<String, String> paramMap = client.getValidatorParams();
			String artifactPath = paramMap.get(ARTIFACT_PATH_PARAM);
			System.out.println("artifactPath =" + artifactPath + "=");
			boolean isDir = Boolean.valueOf(paramMap.get(IS_DIR_PARAM)).booleanValue();
			System.out.println("isDir =" + isDir + "=");
			File artifactFile = new File(artifactPath);
			artifactExists = artifactFile.exists();
			if(artifactExists && isDir) {
				artifactExists = artifactFile.isDirectory();
			}
		}
		catch(Exception exception) {
			System.err.println("Cannot validate if artifact exists: " + exception.getMessage());
			exception.printStackTrace(System.err);
		}
		System.out.println("exiting validate(com.izforge.izpack.panels.ProcessingClient): artifactExists =" + artifactExists + "=");
        return artifactExists;
    }

}