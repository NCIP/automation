/*
 * The caBIG software, the software subject to this notice and license, includes both human readable source code form and machine readable, binary, object code form (Software). The Software was developed for and in conjunction with the National Cancer Institute (NCI) by Stelligent (an Automation for the People, LLC company). To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States Code,
 * section 105.

 * This Software License (License) is between NCI and You. You (or Your) shall mean a person or an entity, and all other entities that control, are controlled by, or are under common control with the entity. Control for purposes of this definition means the direct or indirect power to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity.

 * Provided that You agree to the conditions described below, NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights in the Software to use, install, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, publicly perform, and prepare derivative works of the Software, in any manner and for any purpose, and to have or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/otherwise dispose of the Software (or portions thereof); (iii) distribute and have distributed to and by third parties the Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights set out in , (ii) and (iii) to third parties, including the right to license such rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting or right of payment from You or Your sub-licensees for the rights granted under this License. This License is granted at no charge to You. Your downloading, copying, modifying, displaying, distributing or use of the Software constitutes acceptance of all of the terms and conditions of this License. If you do not agree to such terms and conditions, you have no right to download, copy modify, display, distribute or use the Software.

 * 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.

 * 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: This product includes software developed
by Stelligent. If You do not include such end-user documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments normally appear.

 * 3. You may not use the names "The National Cancer Institute", "NCI", NCICB, NCI CBIIT, "Stelligent", Automation for the People, LLC to endorse or promote products derived from this Software. This License does not authorize You to use any trademarks, service marks, trade names, logos or
product names of either NCI, Stelligent except as required to comply with the terms of this License.

 * 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and into any third party proprietary
programs. However, if You incorporate the Software into third party proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties required to incorporate the Software into such third party proprietary programs and for informing Your sub-licensees, including without limitation Your end-users, of their obligation to secure any required permissions from such third parties before incorporating the Software into such third party proprietary software programs. In the event that You fail to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.

 * 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, and distribution of the Software otherwise complies with the conditions stated in this License.

 * 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, STELLIGENT OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.bda.provisioner;

/**
 *
 * @author Mahidhar Narra
 */

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

import gov.nih.nci.bda.provisioner.domain.ProjectInitialization;
import gov.nih.nci.bda.provisioner.util.HibernateUtil;
import gov.nih.nci.bda.provisioner.util.PropertyHelper;
import gov.nih.nci.bda.provisioner.util.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.hibernate.Query;
import org.hibernate.Session;

public class EC2SystemInitiator extends SystemInitiator implements Initiator{
	private static final Logger LOGGER = Logger
			.getLogger(EC2SystemInitiator.class.getName());


	public EC2SystemInitiator(String hostName, String privateKeyFile, String projectName) throws Exception {
		super(hostName, privateKeyFile,projectName);		
	}
	/*
	public void initializeSystem() throws IOException, InvalidStateException, InterruptedException, EC2Exception {
		
			
		
		if(rootUserUtils.isUserAuthorised())
		{
			rootUserUtils.createRemoteFile(new File("resources/init.sh").getAbsolutePath(), "");

			rootUserUtils.executeRemoteCommand("chmod 700 init.sh");
			rootUserUtils.executeRemoteCommand("yum install sysutils");
			rootUserUtils.executeRemoteCommand("dos2unix init.sh");
			rootUserUtils.executeRemoteCommand("sh init.sh");
			rootUserUtils.executeRemoteCommand("reboot");
			Thread.sleep(100000);
			rootUserUtils.resetConnection(hostName);
		}
		//rootUserUtils.disconnectClient();
		//SystemUtils rootMysqlUtils = new SystemUtils(hostName,"root",privateKeyFile,"KEY");
		if(rootUserUtils.isUserAuthorised())
		{
			rootUserUtils.createRemoteFile(new File("resources/mysqld").getAbsolutePath(), "/etc/init.d/");
			rootUserUtils.createRemoteFile(new File("resources/my.cnf").getAbsolutePath(), "/etc/");
			rootUserUtils.executeRemoteCommand("/etc/init.d/mysqld start");
			rootUserUtils.executeRemoteCommand("mysqladmin -u root password mysql");
		}
		//rootMysqlUtils.disconnectClient();
		
		if(hudsonUserUtils.isUserAuthorised())
		{
			hudsonUserUtils.createRemoteFile(new File("resources/build-hudson.xml").getAbsolutePath(), "");
			hudsonUserUtils.createRemoteFile(new File("resources/.bash_profile").getAbsolutePath(), "");			
			hudsonUserUtils.executeRemoteCommand(". .bash_profile >> profile.log");
			hudsonUserUtils.executeRemoteCommand("mkdir ~/project-working");	
		}
		hudsonUserUtils.disconnectClient();
		/*

		PublicKeyAuthenticationClient authenticationClient = connectToRemoteHost("root");

		if (sshRoot.authenticate(authenticationClient) == AuthenticationProtocolState.COMPLETE) {

			createFile(new File("resources/init.sh").getAbsolutePath(), "");
			createFile(new File("resources/hosts").getAbsolutePath(), "/etc/");

			executeRemoteCommand("chmod 700 init.sh");
			executeRemoteCommand("yum install sysutils");
			executeRemoteCommand("dos2unix init.sh");

			SessionChannelClient scc = sshRoot.openSessionChannel();
			scc.requestPseudoTerminal("ansi", 80, 24, 0, 0, "");
			scc.executeCommand("sh init.sh");
			scc.getState().waitForState(ChannelState.CHANNEL_CLOSED);
			int exitStatus = scc.getExitCode().intValue();
			if (exitStatus != 0) {
				List remoteOutput = IOUtils.readLines(scc
						.getStderrInputStream());
				LOGGER
						.log(Level.WARNING,
								"Failed to excute the init script with the following error ");
				for (Iterator iter = remoteOutput.iterator(); iter.hasNext();) {
					LOGGER.log(Level.WARNING, (String) iter.next());
				}
			} else {
				List remoteOutput = IOUtils.readLines(scc.getInputStream());
				for (Iterator iter = remoteOutput.iterator(); iter.hasNext();) {
					LOGGER.log(Level.INFO, (String) iter.next());
				}

			}
			scc.close();

		} else {
			LOGGER.log(Level.WARNING, "Authetication Failed for root ");
		}
		sshRoot.disconnect();

		connectToRemoteNode("root", authenticationClient, "reboot");
		SshClient ssh4 = createSshConnection();

		if (ssh4.authenticate(authenticationClient) == AuthenticationProtocolState.COMPLETE) {
			ScpClient scp = createScpConnection(ssh4);
			scp.put(new File("resources/mysqld").getAbsolutePath(),
					"/etc/init.d/", true);
			scp.put(new File("resources/my.cnf").getAbsolutePath(), "/etc/",
					true);

			connectToPseudoTerminal(ssh4, "/etc/init.d/mysqld start");
			connectToPseudoTerminal(ssh4, "mysqladmin -u root password mysql");

		}
		ssh4.disconnect();

		SshClient ssh1 = new SshClient();

		PasswordAuthenticationClient pwd = connectToHudsonUser(ssh1,
				"hudsonuser", "password");

		if (ssh1.authenticate(pwd) == AuthenticationProtocolState.COMPLETE) {
			LOGGER.log(Level.INFO, "Authetication Successful for hudsonuser ");
			ScpClient scp = ssh1.openScpClient();
			scp.put(new File("resources/build-hudson.xml").getAbsolutePath(),
					"", true);
			scp.put(new File("resources/.bash_profile").getAbsolutePath(), "",
					true);

			connectToPseudoTerminal(ssh1, ". .bash_profile >> profile.log");
			
			connectToPseudoTerminal(ssh1, "mkdir ~/project-working");
			ProvisionerCommands.getCommand("checkout_url"); 
			connectToPseudoTerminal(ssh1,"ant -f build.xml >> build.log");			
		
			connectToPseudoTerminal(ssh1,
					"ant -f build.xml >> build.log");

			connectToPseudoTerminal(ssh1, "mkdir ~/hudson_data/jobs");
			connectToPseudoTerminal(ssh1, "mkdir ~/hudson_data/jobs/cai2");
			connectToPseudoTerminal(ssh1, "mkdir ~/hudson_data/jobs/project");

			scp.put(new File("resources/cai2/config.xml").getAbsolutePath(),
					"~/hudson_data/jobs/cai2", true);
			scp.put(new File("resources/project/config.xml").getAbsolutePath(),
					"~/hudson_data/jobs/project", true);
			scp
					.put(
							new File("resources/catalina.sh").getAbsolutePath(),
							"/mnt/hudsonuser/hudson/application/apache-tomcat-5.5.20/bin",
							true);

			connectToPseudoTerminal(
					ssh1,
					"dos2unix /mnt/hudsonuser/hudson/application/apache-tomcat-5.5.20/bin/catalina.sh");

		} else {
			LOGGER.log(Level.WARNING, "Authetication Failed for hudsonuser");
		}

		ssh1.disconnect();

		SshClient ssh3 = createSshConnection();

		if (ssh3.authenticate(pwd) == AuthenticationProtocolState.COMPLETE) {
			ScpClient scp = createScpConnection(ssh3);
			scp.put(new File("resources/start-hudson.sh").getAbsolutePath(),
					"", true);

			connectToPseudoTerminal(
					ssh3,
					". /mnt/hudsonuser/.bash_profile;env | sort>> start.log;nohup /mnt/hudsonuser/hudson/application/apache-tomcat-5.5.20/bin/startup.sh 2>&1 >> start.log&");

		}
		ssh3.disconnect();
		sshHudson.disconnect();
		sshRoot.disconnect();

	}
	*/
}
