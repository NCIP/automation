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

import com.sshtools.j2ssh.util.InvalidStateException;
import com.xerox.amazonws.ec2.EC2Exception;
import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.GroupDescription.IpPermission;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

import gov.nih.nci.bda.provisioner.util.ConfigurationHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;

public class EC2Provisioner extends BaseProvisioner
{
  //public static String privateKeyFileName="provisioner-4";
  //public static String dnsName="ec2-75-101-204-78.compute-1.amazonaws.com";
  public String privateKeyFileName;
  public String dnsName;
  private static Configuration config;
  private static final Logger LOGGER = Logger.getLogger(EC2Provisioner.class.getName());

  public EC2Provisioner ()
  {
	  super();
	  config = ConfigurationHelper.getConfiguration(new File("instance.properties").getAbsoluteFile());
  }

  public String generateKey(String accessId, String secretKey) throws IOException
  {
	  Jec2 jec2 = new Jec2(accessId, secretKey);
	  KeyPairInfo key = null;
    try
    {
    	List<KeyPairInfo> existingKeys = jec2.describeKeyPairs(new String [] {});
    	int n = 0;
    	while (true)
    	{
    		boolean found = false;
    		for (KeyPairInfo res : existingKeys)
    			if (res.getKeyName().equals("provisioner-" + n))
    				found = true;
    		if (!(found))
    			break;
    		++n;
    	}
    	privateKeyFileName = "provisioner-" + n;
    	key = jec2.createKeyPair(privateKeyFileName);
    	LOGGER.info("keypair : "+key.getKeyName()+", "+key.getKeyFingerprint() + ", "+key.getKeyMaterial());
    	LOGGER.info("Saving key in user home ");
    	EC2PrivateKey.savePrivateKey(key.getKeyMaterial(),System.getProperty("user.home"),privateKeyFileName);

    }
	catch (EC2Exception e)
	{
		LOGGER.log(Level.WARNING, "Failed to check EC2 credential", e);
	}
	return privateKeyFileName;
  }


public String testConnection(String accessId, String secretKey, String privateKey) throws IOException
  {
    Jec2 jec2 = new Jec2(accessId, secretKey);
    try {

      List<String> params = new ArrayList<String>();
      jec2.describeInstances(params);

      if (accessId == null)
        return "Access ID can be not null";
      if (secretKey == null)
        return "Secret key can be not null";
      if (privateKey == null)
        return "Private key can be not null";

      if ((privateKey.trim().length() > 0)
             &&  (new EC2PrivateKey(privateKey).findKeyPair(jec2) == null)
    		  ) {
              return "The private key is not registred";
            }


    } catch (EC2Exception e) {
    	LOGGER.log(Level.WARNING, "Private Key Authentication Failed", e); }
    return "success";
  }

private void listAllKeys(String accessId, String secretKey) {
    Jec2 jec2 = new Jec2(accessId, secretKey);
    try {
	List<KeyPairInfo> info = jec2.describeKeyPairs(new String [] {});
	LOGGER.info("keypair list");
	for (KeyPairInfo i : info) {
		LOGGER.info("keypair : "+i.getKeyName()+", "+i.getKeyFingerprint() + ", "+i.getKeyMaterial());
	}
    } catch (EC2Exception e) {
    	LOGGER.log(Level.WARNING, "Private Key Authentication Failed", e); }
  }


private String  runInstance(String accessId, String secretKey,String privateKey) throws IOException
{
	Jec2 jec2 = new Jec2(accessId, secretKey);
	String instanceState = null;

	try
	{
		KeyPairInfo keyPair = new EC2PrivateKey(privateKey).findKeyPair(jec2);
		if (keyPair == null)
			throw new EC2Exception("No matching keypair found on EC2. Is the EC2 private key a valid one?");
		ReservationDescription inst = (ReservationDescription)jec2.runInstances("ami-3c47a355", 1, 1, new ArrayList<String>(), null, keyPair.getKeyName(), InstanceType.DEFAULT);
		ReservationDescription.Instance ins = inst.getInstances().get(0);
		do
		{
			List<ReservationDescription> instances = jec2.describeInstances(new ArrayList<String>());
			for (ReservationDescription res : instances) {
				//LOGGER.info(res.getOwner()+"\t"+res.getReservationId());
				if(inst.getReservationId().equals(res.getReservationId()) )
				{
					if (res.getInstances() != null) {
						for (Instance instance : res.getInstances()) {
							instanceState = instance.getState();
							dnsName = instance.getDnsName();
						}
					}
				}
			}
		}while(!instanceState.equalsIgnoreCase("running"));

		LOGGER.info("Connect Instance using the below string : ");
		LOGGER.info("ssh -i "+ System.getProperty("user.home") +"/"+ privateKeyFileName + " root@"+ dnsName );
	}
	catch (EC2Exception e) {
		throw new AssertionError();
	}
	return dnsName;
}

  public void provisionInstance() throws Exception
  {
	 	String accessId = config.getString("ec2.access.id");
	  	String secretKey = config.getString("ec2.secret.key");
		listAllKeys(accessId, secretKey);
		generateKey(accessId, secretKey);
		generateSecurityGroup(accessId, secretKey, (ArrayList) config.getProperty("ec2.port.list"));
		testConnection(accessId, secretKey,EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home"),privateKeyFileName));
		runInstance(accessId, secretKey,EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home"),privateKeyFileName));
		initializeInstance();
}


@SuppressWarnings("unchecked")
private void generateSecurityGroup(String accessId, String secretKey, ArrayList<String> portList) throws IOException {
	Jec2 jec2 = new Jec2(accessId, secretKey);
    try
    {
    	List<GroupDescription> securityGroups = jec2.describeSecurityGroups(new String [] {});
    	String ownerId = null;
    	for (String portValue : portList)
    	{   
        	boolean portOpen = false;
			for (GroupDescription res : securityGroups)
			{
				ownerId = res.getOwner();
				if(res.getName().equalsIgnoreCase("default"))
				{				
					List<IpPermission> ipPerms = (List<IpPermission>) res.getPermissions();
			    	for (IpPermission perm : ipPerms)
			    	{
			    		String portNumber = Integer.toString(perm.getFromPort());		
		        		if(portNumber != null && portNumber.equals(portValue))
		        		{
		        			portOpen = true;
		        		}		        	
			    	}
				}
			}
	    	if (!portOpen)
	    	{
	    		jec2.authorizeSecurityGroupIngress("default", "tcp", Integer.valueOf(portValue).intValue(), Integer.valueOf(portValue).intValue(), "0.0.0.0/0");
	    	}	
    	}
  
/*
		if(portList != null)
    	{
    		jec2.createSecurityGroup(privateKeyFileName, privateKeyFileName+" security group");
	    	for (String portValue : portList)
	    	{
	    		jec2.authorizeSecurityGroupIngress(privateKeyFileName, "tcp", Integer.valueOf(portValue).intValue(), Integer.valueOf(portValue).intValue(), "0.0.0.0/0");
	    	}
	    	jec2.authorizeSecurityGroupIngress("default", privateKeyFileName, ownerId);
    	}
 */   
    }    	
	catch (EC2Exception e)
	{
		LOGGER.log(Level.WARNING, "Failed to generate a EC2 security group", e);
	}
}


private void initializeInstance() throws IOException, InvalidStateException, InterruptedException
	{
	  try
	  {
		EC2SystemInitiator si = new EC2SystemInitiator(dnsName,System.getProperty("user.home")+"/"+privateKeyFileName);
		si.initializeSystem();
	  }
	  catch (EC2Exception e)
	  {
		throw new AssertionError();
	  }
	}
}
