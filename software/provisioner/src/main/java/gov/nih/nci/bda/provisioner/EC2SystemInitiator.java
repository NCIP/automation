package gov.nih.nci.bda.provisioner;
import com.sshtools.j2ssh.ScpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.authentication.PublicKeyAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.connection.ChannelState;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.ConsoleKnownHostsKeyVerification;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKey;
import com.sshtools.j2ssh.transport.publickey.SshPrivateKeyFile;
import com.sshtools.j2ssh.util.InvalidStateException;
import com.sshtools.j2ssh.forwarding.ForwardingIOChannel;
import com.sshtools.j2ssh.io.IOStreamConnector;
//import com.trilead.ssh2.Connection;
//import com.trilead.ssh2.SCPClient;
//import com.trilead.ssh2.Session;
import com.xerox.amazonws.ec2.EC2Exception;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.apache.commons.io.IOUtils;



public class EC2SystemInitiator
{
	private static final Logger LOGGER = Logger.getLogger(EC2SystemInitiator.class.getName());
	private String hostName;
	private String privateKeyFile;


  public EC2SystemInitiator(String hostName, String privateKeyFile)
  {
    this.hostName = hostName.trim();
    this.privateKeyFile = privateKeyFile.trim();
  }

  public void initializeSystem() throws IOException, EC2Exception, InvalidStateException, InterruptedException
  {
    LOGGER.info("Connecting to " + hostName);
    LOGGER.info("Using key at " + privateKeyFile);

	SshClient ssh = new SshClient();

	Thread.sleep(100000);
	ssh.connect(hostName, new IgnoreHostKeyVerification());

	//Authenticate
	// Open up the private key file
	SshPrivateKeyFile pkFile = SshPrivateKeyFile.parse(new File(privateKeyFile));
	PublicKeyAuthenticationClient authenticationClient = new PublicKeyAuthenticationClient();
	authenticationClient.setUsername("root");
	authenticationClient.setKey(pkFile.toPrivateKey(null));
	LOGGER.log(Level.INFO, "KEY FILE " + authenticationClient.getKeyfile());
	if( ssh.authenticate(authenticationClient)== AuthenticationProtocolState.COMPLETE)
	{
	
		LOGGER.log(Level.INFO, "Authetication Successful using key ");
		SessionChannelClient  sc = ssh.openSessionChannel();
		ScpClient scp = ssh.openScpClient();
		scp.put(new File("init.sh").getAbsolutePath(), "", true);
		scp.put(new File("hosts").getAbsolutePath(), "/etc/", true);


		sc.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		sc.executeCommand("chmod 700 init.sh");
		sc.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		sc.close();


		SessionChannelClient  utils = ssh.openSessionChannel();
		utils.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		utils.executeCommand("yum install sysutils");
		utils.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		utils.close();


		SessionChannelClient  dos = ssh.openSessionChannel();
		dos.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		dos.executeCommand("dos2unix init.sh");
		dos.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		dos.close();



		SessionChannelClient  scc = ssh.openSessionChannel();
		scc.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		scc.executeCommand("sh init.sh");
		scc.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		int exitStatus = scc.getExitCode().intValue();
		if (exitStatus != 0)
		{
			List remoteOutput = IOUtils.readLines(scc.getStderrInputStream());
			LOGGER.log(Level.WARNING, "Failed to excute the init script with the following error ");
			for (Iterator iter = remoteOutput.iterator(); iter.hasNext();)
			{
				LOGGER.log(Level.WARNING, (String) iter.next());
			}
			//OutputStream out = null ;
			//IOUtils.copy(session.getStdout(),out);
			//IOStreamConnector output =	new IOStreamConnector(scc.getInputStream(), out);
			//LOGGER.addHandler(streamHandler);
		}else
		{
			List remoteOutput = IOUtils.readLines(scc.getInputStream());
			for (Iterator iter = remoteOutput.iterator(); iter.hasNext();)
			{
				LOGGER.log(Level.INFO, (String) iter.next());
			}

		}
		scc.close();

		SessionChannelClient  reboot = ssh.openSessionChannel();
		reboot.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		reboot.executeCommand("reboot");
		reboot.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		reboot.close();
		
	}
	else
	{
		LOGGER.log(Level.WARNING, "Authetication Failed for root ");
	}
	ssh.disconnect();
	
	Thread.sleep(100000);
	SshClient ssh1 = new SshClient();
	ssh1.connect(hostName, new IgnoreHostKeyVerification());
	PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
	pwd.setUsername("hudsonuser");
	pwd.setPassword("password");

	if( ssh1.authenticate(pwd)== AuthenticationProtocolState.COMPLETE)
	{

		LOGGER.log(Level.INFO, "Authetication Successful for hudsonuser ");
		ScpClient scp = ssh1.openScpClient();
		scp.put(new File("build-hudson.xml").getAbsolutePath(), "", true);
		scp.put(new File(".bash_profile").getAbsolutePath(), "", true);
		
		SessionChannelClient  bash1 = ssh1.openSessionChannel();
		bash1.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		bash1.executeCommand(". .bash_profile >> profile.log");		
		bash1.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		bash1.close();
				
		SessionChannelClient  scb = ssh1.openSessionChannel();
		scb.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");		
		scb.executeCommand("ant -f build-hudson.xml >> build.log");
		scb.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		scb.close(); 
		
	}
	else
	{
		LOGGER.log(Level.WARNING, "Authetication Failed for hudsonuser");
	}
	
	ssh1.disconnect();

	SshClient ssh2 = new SshClient();

	Thread.sleep(100000);
	ssh2.connect(hostName, new IgnoreHostKeyVerification());	
	if( ssh2.authenticate(authenticationClient)== AuthenticationProtocolState.COMPLETE)
	{
		SessionChannelClient  reboot = ssh2.openSessionChannel();
		reboot.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		reboot.executeCommand("reboot");
		reboot.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		reboot.close();
	}
	ssh2.disconnect();

	SshClient ssh4 = new SshClient();

	Thread.sleep(100000);
	ssh4.connect(hostName, new IgnoreHostKeyVerification());

	//Authenticate
	// Open up the private key file
	
	if( ssh4.authenticate(authenticationClient)== AuthenticationProtocolState.COMPLETE)
	{
		ScpClient scp = ssh4.openScpClient();
		scp.put(new File("mysqld").getAbsolutePath(), "/etc/init.d/", true);
		scp.put(new File("my.cnf").getAbsolutePath(), "/etc/", true);
		
		
		
		SessionChannelClient  startMysql = ssh4.openSessionChannel();
		startMysql.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		startMysql.executeCommand("/etc/init.d/mysqld start");
		startMysql.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		startMysql.close();

		SessionChannelClient  setRootMysql = ssh4.openSessionChannel();
		setRootMysql.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		setRootMysql.executeCommand("mysqladmin -u root password mysql");
		setRootMysql.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		setRootMysql.close();		
		
	}
	ssh4.disconnect();
	
	/*
	SshClient ssh3 = new SshClient();

	Thread.sleep(100000);
	ssh3.connect(hostName, new IgnoreHostKeyVerification());

	//Authenticate
	// Open up the private key file
	
	if( ssh3.authenticate(pwd)== AuthenticationProtocolState.COMPLETE)
	{
		ScpClient scp = ssh3.openScpClient();
		scp.put(new File("start-hudson.sh").getAbsolutePath(), "", true);
		
		
	   	SessionChannelClient session = ssh3.openSessionChannel();
		session.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
		session.executeCommand("sh start-hudson.sh >> start.log");
		//session.executeCommand("ant -f build-hudson.xml start-hudson >> start.log");
		session.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		session.close();
	}
	ssh3.disconnect();
*/
  }
/*  
  public void executeRemoteCommand(String command ) {
	  try 
	  {
			OutputStream out = session.getOutputStream();
			out.write(command.getBytes());
			out.flush(); 
			InputStream in = session.getInputStream();
			
			List remoteOutput = IOUtils.readLines(in);
			for (Iterator iter = remoteOutput.iterator(); iter.hasNext();)
			{		
				LOGGER.log(Level.INFO, (String) iter.next());
			}
*/			
		/*	
		  byte buffer[] = new byte[255];
   		  int read;   		  
   		  while((read = in.read(buffer))>0 ) 
   		  {
   		    response = new String(buffer, 0, read);
   			LOGGER.log(Level.INFO, (String) response);
   		  }
   		   		     
	}
	catch(Exception e) 
	{		
		e.printStackTrace();
    }
  }
    */  
}
