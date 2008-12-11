package com.izforge.izpack.util;

import com.izforge.izpack.panels.ProcessingClient;
import com.izforge.izpack.panels.Validator;

import java.util.Map;
import java.io.File;

/**
 * A validator to enforce that a specified artifact exists on the filesystem.
 * <p/>
 * This validator will check that the specified artifact exists on the filesystem.
 *
 * @author Douglas Harley <harleyda@mail.nih.gov>
 */
public class ArtifactExistsValidator implements Validator
{

    private static final String ARTIFACT_PATH_PARAM = "artifactPath";
    private static final String IS_DIR_PARAM = "isDir";

    public boolean validate(ProcessingClient client) {
        if(!client.hasParams()) {
			throw new RuntimeException("Required params missing: 'artifactPath', 'isDir'.");
        }
		Map<String, String> paramMap = client.getValidatorParams();
		String artifactPath = paramMap.get(ARTIFACT_PATH_PARAM);
		System.out.println("artifactPath =" + artifactPath + "=");
		boolean isDir = Boolean.valueOf(paramMap.get(IS_DIR_PARAM)).booleanValue();
		System.out.println("isDir =" + isDir + "=");
		File artifactFile = new File(artifactPath);
		boolean artifactExists = artifactFile.exists();
		if(artifactExists && isDir) {
			artifactExists = artifactFile.isDirectory();
		}
		System.out.println("exiting: artifactExists =" + artifactExists + "=");
        return artifactExists;
    }

}