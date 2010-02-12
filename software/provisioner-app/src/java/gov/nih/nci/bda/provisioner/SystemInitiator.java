package gov.nih.nci.bda.provisioner;

import gov.nih.nci.bda.provisioner.domain.ProjectInitialization;
import gov.nih.nci.bda.provisioner.util.HibernateUtil;
import gov.nih.nci.bda.provisioner.util.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sshtools.j2ssh.ScpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.authentication.PublicKeyAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelState;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKeyFile;
import com.sshtools.j2ssh.util.InvalidStateException;
import com.xerox.amazonws.ec2.EC2Exception;


public abstract class SystemInitiator{
	protected String hostName;
	protected String privateKeyFile;
	protected String projectName;
	protected SshClient sshRoot;
	protected SshClient sshHudson;
	protected SystemUtils rootUserUtils;
	protected SystemUtils appUserUtils;
	private static final Logger LOGGER = Logger
	.getLogger(SystemInitiator.class.getName());
	
	public SystemInitiator(String hostName, String privateKeyFile, String projectName) throws Exception 
	{
		this.hostName = hostName.trim();
		this.privateKeyFile = privateKeyFile.trim();
		this.projectName = projectName.trim();
		System.out.println("hostName::"+hostName);
		System.out.println("projectName::"+projectName);
		System.out.println("privateKeyFile::"+privateKeyFile);
		rootUserUtils = new SystemUtils(hostName,"root",privateKeyFile,"KEY");
		appUserUtils = new SystemUtils(hostName,"appuser","password","PASSWORD");
	}
	
	public void initializeSystem() throws Exception {
		LOGGER.info("Connecting to " + hostName);
		LOGGER.info("Using key at " + privateKeyFile);
		
		//command
	
		LOGGER.info("Getting session " );
		//Session session = HibernateUtil.getSession();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		LOGGER.info("Begin traansaction " );
		session.beginTransaction();
		LOGGER.info("Create query " );
		Query query = session.createQuery(" from ProjectInitialization as projectInitialization where projectInitialization.projectName='" + projectName +"' order by projectInitialization.id");
		Iterator<Object> projectCommands = query.iterate();

		while (projectCommands.hasNext()) 
		{
			LOGGER.info("getting the projectInitialization object" );
			ProjectInitialization projectInitialization = (ProjectInitialization) projectCommands.next();
			if (projectInitialization.getRunCommand() != null
					&& projectInitialization.getRunCommand().equals("true")) 
			{				
				if (projectInitialization.getIsFile() != null
						&& projectInitialization.getIsFile().equals("true")) 
				{
	
					if (projectInitialization.getRunAsRoot() != null
							&& projectInitialization.getRunAsRoot().equals("true")) 
					{	
						if(rootUserUtils.isUserAuthorised())
						{
							System.out.println ("Executing Command::"+projectInitialization.getCommandName());
							rootUserUtils.createRemoteFile(new File(projectInitialization.getCommandName()).getAbsolutePath(), projectInitialization.getToLocation());
						}
						
					}
					else
					{		
						if(appUserUtils.isUserAuthorised())
						{
							appUserUtils.createRemoteFile(new File(projectInitialization.getCommandName()).getAbsolutePath(), projectInitialization.getToLocation());
						}
						
					}
					if (projectInitialization.getIsRebootRequired() != null
							&& projectInitialization.getIsRebootRequired().equals("true")) 
					{
						//executeReboot();
						rootUserUtils.executeRemoteCommand("reboot");
						Thread.sleep(100000);						
					}
					
				}else
				{
					if (projectInitialization.getRunAsRoot() != null
							&& projectInitialization.getRunAsRoot().equals("true")) 
					{	
	
						if(rootUserUtils.isUserAuthorised())
						{
							System.out.println ("Executing Command::"+projectInitialization.getCommandName());
							rootUserUtils.executeRemoteCommand(projectInitialization.getCommandName());
						}
						
					}
					else
					{		
						if(appUserUtils.isUserAuthorised())
						{
							System.out.println ("Executing Command as user::"+projectInitialization.getCommandName());
							appUserUtils.executeRemoteCommand(projectInitialization.getCommandName());
						}
						
					}
					if (projectInitialization.getIsRebootRequired() != null
							&& projectInitialization.getIsRebootRequired().equals("true")) 
					{
						//executeReboot();
						rootUserUtils.executeRemoteCommand("reboot");
						Thread.sleep(100000);	
					}
					
				}
			}
			resetConnection();
		}
		session.getTransaction().commit();
		//session.close();
		//HibernateUtil.closeSession();
	}

	protected void connectToPseudoTerminal(SshClient ssh1, String command)
			throws IOException, EC2Exception, InvalidStateException,
			InterruptedException {

		SessionChannelClient bash1 = ssh1.openSessionChannel();
		bash1.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		bash1.executeCommand(command);
		bash1.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		bash1.close();

	}

	protected PasswordAuthenticationClient connectToHudsonUser(SshClient ssh1,
			String username, String password) throws IOException, EC2Exception,
			InvalidStateException, InterruptedException {

		Thread.sleep(100000);
		ssh1.connect(hostName, new IgnoreHostKeyVerification());

		PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
		pwd.setUsername(username);
		pwd.setPassword(password);

		return pwd;
	}

	protected PublicKeyAuthenticationClient connectToRemoteHost(String username)
			throws IOException, EC2Exception, InvalidStateException,
			InterruptedException {
		Thread.sleep(100000);
		sshRoot.connect(hostName, new IgnoreHostKeyVerification());

		// Authenticate
		// Open up the private key file
		SshPrivateKeyFile pkFile = SshPrivateKeyFile.parse(new File(
				privateKeyFile));
		PublicKeyAuthenticationClient authenticationClient = new PublicKeyAuthenticationClient();
		authenticationClient.setUsername(username);
		authenticationClient.setKey(pkFile.toPrivateKey(null));
		LOGGER.log(Level.INFO, "KEY FILE " + authenticationClient.getKeyfile());
		return authenticationClient;
	}

	protected SshClient createSshConnection() throws InterruptedException,IOException {
		SshClient ssh = new SshClient();
		Thread.sleep(100000);
		ssh.connect(hostName, new IgnoreHostKeyVerification());
		return ssh;
	}

	protected ScpClient createScpConnection(SshClient ssh) throws InterruptedException, IOException {
		ScpClient scp = ssh.openScpClient();
		return scp;
	}

	protected int executeRemoteCommand(String command) throws IOException,
		EC2Exception, InvalidStateException, InterruptedException {
		LOGGER.log(Level.INFO, "Executing System command using " + command);
		SessionChannelClient sc = sshRoot.openSessionChannel();
		sc.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		sc.executeCommand(command);
		sc.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		int exitStatus = sc.getExitCode().intValue();
		sc.close();
		return exitStatus;
	}
	
	private void resetConnection() throws IOException {
		rootUserUtils.resetConnection();
		appUserUtils.resetConnection();
	}
}
