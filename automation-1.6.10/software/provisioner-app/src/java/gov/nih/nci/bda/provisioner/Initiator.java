package gov.nih.nci.bda.provisioner;

import java.io.IOException;

import com.sshtools.j2ssh.util.InvalidStateException;


public interface Initiator {
	public void initializeSystem() throws Exception;
}
