package gov.nih.nci.bda.provisioner;

import com.sshtools.j2ssh.util.InvalidStateException;
import com.xerox.amazonws.ec2.EC2Exception;
import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.KeyPairInfo;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

import gov.nih.nci.bda.provisioner.util.ConfigurationHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;

public class EC2Provisioner extends BaseProvisioner 
{
  //public static String privateKeyFileName="provisioner-3";
  //public static String dnsName="ec2-75-101-226-217.compute-1.amazonaws.com";
  public static String privateKeyFileName;
  public static String dnsName;
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
	  KeyPairInfo key;
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
	return "success";
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


private void runInstance(String accessId, String secretKey,String privateKey) throws IOException  
{
	Jec2 jec2 = new Jec2(accessId, secretKey);
	String instanceState = null;
	
	try
	{
		KeyPairInfo keyPair = new EC2PrivateKey(privateKey).findKeyPair(jec2);
		if (keyPair == null)
			throw new EC2Exception("No matching keypair found on EC2. Is the EC2 private key a valid one?");
		ReservationDescription inst = (ReservationDescription)jec2.runInstances("ami-0459bc6d", 1, 1, new ArrayList<String>(), null, keyPair.getKeyName(), InstanceType.DEFAULT);
		ReservationDescription.Instance ins = inst.getInstances().get(0);
	//	jec2.authorizeSecurityGroupIngress("default", "tcp", 22, 22, "0.0.0.0/0");
	//	jec2.authorizeSecurityGroupIngress("default", "tcp", 80, 80, "0.0.0.0/0");

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
}
	
  public void provisionInstance() throws Exception
  {
	  	String accessId = config.getString("ec2.access.id");
	  	String secretKey = config.getString("ec2.secret.key");
		listAllKeys(accessId, secretKey);
		generateKey(accessId, secretKey);
		generateSecurityGroup(accessId, secretKey);
		testConnection(accessId, secretKey,EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home"),privateKeyFileName));
		runInstance(accessId, secretKey,EC2PrivateKey.retrivePrivateKey(System.getProperty("user.home"),privateKeyFileName));
		initializeInstance(System.getProperty("user.home")+"/"+privateKeyFileName);
}


@SuppressWarnings("unchecked")
private void generateSecurityGroup(String accessId, String secretKey) throws IOException {
	Jec2 jec2 = new Jec2(accessId, secretKey);
    try 
    {
    	/*
    	List<GroupDescription> securityGroups = jec2.describeSecurityGroups(new String [] {});
    	int n = 0;

    	while (true) 	
    	{
    		boolean found = false;
    		for (GroupDescription res : securityGroups)
    			if (res.getName().equals("provisioner-" + n))
    				found = true;
    		if (!(found))
    			break;
    		++n;
    	} 
    	privateKeyFileName = "provisioner-" + n;
    	*/
    	ArrayList<String> portList=(ArrayList) config.getProperty("ec2.port.list");
		if(portList != null)
    	{
    		jec2.createSecurityGroup(privateKeyFileName, privateKeyFileName+" security group");
	    	for (String portValue : portList)
	    	{
	    		jec2.authorizeSecurityGroupIngress(privateKeyFileName, "tcp", Integer.valueOf(portValue).intValue(), Integer.valueOf(portValue).intValue(), "0.0.0.0/0");
	    	}	    	
	    	jec2.authorizeSecurityGroupIngress("default", privateKeyFileName, "923120264911");
    	}
    } 
	catch (EC2Exception e) 
	{
		LOGGER.log(Level.WARNING, "Failed to generate a EC2 security group", e); 
	}	
}


private void initializeInstance(String privateKeyFile) throws IOException, InvalidStateException, InterruptedException 
	{
	  try
	  {
		EC2SystemInitiator si = new EC2SystemInitiator(dnsName,privateKeyFile);
		si.initializeSystem();
	  }
	  catch (EC2Exception e)
	  {
		throw new AssertionError();	
	  }
	}
}
