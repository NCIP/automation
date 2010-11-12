package gov.nih.nci.bda.provisioner.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.sshtools.j2ssh.ScpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.authentication.PublicKeyAuthenticationClient;
import com.sshtools.j2ssh.authentication.SshAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelState;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.sshtools.j2ssh.transport.publickey.InvalidSshKeyException;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKeyFile;
import com.sshtools.j2ssh.util.InvalidStateException;
import com.xerox.amazonws.ec2.EC2Exception;


public class SystemUtils {
	public static final String PASSWORD_AUTENTICATION="PASSWORD";
	public static final String KEY_AUTENTICATION="KEY";
	private SshAuthenticationClient authenticationClient = null;
	private SshClient sshClient = null;
	private String userName = null;
	private String password = null;
	private String hostName = null;
	private String authenticationType = null;
	
	public SystemUtils(String hostName, String userName, String password, String authenticationType) throws EC2Exception, InvalidStateException, IOException, InterruptedException{
		this.userName = userName;
		this.password = password;
		this.hostName = hostName;
		this.authenticationType = authenticationType;
		sshClient = new SshClient();
		authenticationClient = getRemoteConnection(hostName, userName, password, authenticationType);
	}
	
	public void connectToRemoteNode(String username,
			PublicKeyAuthenticationClient authenticationClient, String command)
			throws IOException, EC2Exception, InvalidStateException,
			InterruptedException {
		SshClient ssh = createSshConnection();
		if (ssh.authenticate(authenticationClient) == AuthenticationProtocolState.COMPLETE) {
			connectToPseudoTerminal(ssh, command);
		}

		ssh.disconnect();
	}
	
	public void connectToPseudoTerminal(SshClient ssh1, String command) throws IOException, EC2Exception, InvalidStateException,InterruptedException {

		SessionChannelClient bash1 = ssh1.openSessionChannel();
		bash1.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		bash1.executeCommand(command);
		bash1.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		bash1.close();

	}
	
	protected SshClient createSshConnection() throws InterruptedException,IOException {
		SshClient ssh = new SshClient();
		Thread.sleep(100000);
	//	ssh.connect(hostName, new IgnoreHostKeyVerification());
		return ssh;
	}

	protected ScpClient createScpConnection(SshClient ssh) throws InterruptedException, IOException {
		ScpClient scp = ssh.openScpClient();
		return scp;
	}
	
	public int executeRemoteCommand(String command) throws IOException,
	EC2Exception, InvalidStateException, InterruptedException {
		
		SessionChannelClient sc = getSshClient().openSessionChannel();
		sc.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		sc.executeCommand(command);
		sc.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		int exitStatus = sc.getExitCode().intValue();
		sc.close();
		return exitStatus;
	}

	public SshAuthenticationClient getRemoteConnection(String hostName,String userName,String password,String authenticationType) throws IOException,
	EC2Exception, InvalidStateException, InterruptedException {	
		if(authenticationClient != null)
		{
			return this.authenticationClient;
		}	
		else
		{
			authenticationClient = getConnection(hostName, userName, password,authenticationType);	
			return authenticationClient;
		}
	}

	private SshAuthenticationClient getConnection(String hostName,String userName, String password, String authenticationType) throws IOException, InterruptedException {
		if(authenticationType != null && authenticationType.equalsIgnoreCase(this.PASSWORD_AUTENTICATION)) 
		{
			Thread.sleep(100000);
			authenticationClient = new PasswordAuthenticationClient();
			getSshClient().connect(hostName, new IgnoreHostKeyVerification());
			authenticationClient = new PasswordAuthenticationClient();
			authenticationClient.setUsername(userName);
			((PasswordAuthenticationClient) authenticationClient).setPassword(password);						
		}
		else
		{
			Thread.sleep(100000);
			authenticationClient = new PublicKeyAuthenticationClient();
			getSshClient().connect(hostName, new IgnoreHostKeyVerification());
			SshPrivateKeyFile pkFile = SshPrivateKeyFile.parse(new File(password));
			authenticationClient.setUsername(userName);
			((PublicKeyAuthenticationClient) authenticationClient).setKey(pkFile.toPrivateKey(null));
		}
		return authenticationClient;
	}

	public boolean isUserAuthorised() throws IOException {
		if (getSshClient().authenticate(authenticationClient) == AuthenticationProtocolState.COMPLETE)
			return true;
		return false;
		}

	private SshClient getSshClient() 
	{
		if (sshClient != null)
			return sshClient;
		else
		{
			SshClient sshClient = new SshClient();
			return sshClient;
		}			
	}
	
	public void createRemoteFile(String path, String dir) throws IOException {
		ScpClient scp = getSshClient().openScpClient();
		scp.put(new File(path).getAbsolutePath(), dir, true);
	}

	public void disconnectClient() throws IOException {
		if(getSshClient()!= null)
		{
			getSshClient().disconnect();
			sshClient = null;
			authenticationClient = null;
		}
	}

	public void resetConnection() throws IOException {
		
		if(getSshClient()!= null)
		{
			
			getSshClient().disconnect();
			authenticationClient.reset();
			if(authenticationType != null && authenticationType.equalsIgnoreCase(this.PASSWORD_AUTENTICATION)) 
			{
				((PasswordAuthenticationClient) authenticationClient).setPassword(password);
			}
			System.out.println ("Resetting Connection::"+hostName);
			getSshClient().connect(hostName, new IgnoreHostKeyVerification());
		}
	}
}
