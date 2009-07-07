package gov.nih.nci.bda.provisioner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.DigestInputStream;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import com.xerox.amazonws.ec2.EC2Exception;
import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.KeyPairInfo;

public class EC2PrivateKey {
	  private final String privateKey;

	  EC2PrivateKey(String privateKey)
	  {
	    this.privateKey = privateKey.trim();
	  }

	  public String getFingerprint()
	    throws IOException
	  {
	    Security.addProvider(new BouncyCastleProvider());
	    Reader r = new BufferedReader(new StringReader(this.privateKey.toString()));
	    PEMReader pem = new PEMReader(r);
	    KeyPair pair = (KeyPair)pem.readObject();
	    if (pair == null) return null;
	    PrivateKey key = pair.getPrivate();
	    return digest(key);
	  }

	  public boolean isPrivateKey() throws IOException
	  {
	    String line;
	    BufferedReader br = new BufferedReader(new StringReader(this.privateKey.toString()));
	    do
	      if ((line = br.readLine()) == null) 
	    	  return false;
	    while (!(line.equals("-----BEGIN RSA PRIVATE KEY-----")));
	    return true;
	  }

	  public KeyPairInfo findKeyPair(Jec2 ec2) throws IOException, EC2Exception
	  {
	    String fp = getFingerprint();
	    for (KeyPairInfo kp : ec2.describeKeyPairs(new String[0]))
	      if (kp.getKeyFingerprint().equals(fp))
	        return new KeyPairInfo(kp.getKeyName(), fp, this.privateKey.toString());

	    return null;
	  }
	  
	  private static String digest(PrivateKey k) throws IOException {
	    MessageDigest md5;
	    try {
	      md5 = MessageDigest.getInstance("SHA1");

	      DigestInputStream in = new DigestInputStream(new ByteArrayInputStream(k.getEncoded()), md5);
	      try {
	        while (in.read(new byte[128]) > 0);
	      }
	      finally {
	        in.close();
	      }
	      StringBuilder buf = new StringBuilder();
	      char[] hex = Hex.encodeHex(md5.digest());
	      for (int i = 0; i < hex.length; i += 2) {
	        if (buf.length() > 0) buf.append(':');
	        buf.append(hex, i, 2);
	      }
	      return buf.toString();	      
	    } catch (NoSuchAlgorithmException e) {
	      throw new AssertionError(e);
	    }
	  }

	public static void savePrivateKey(String keyMaterial, String location , String fileName) throws IOException {
		  byte[] key = keyMaterial.getBytes();
	      FileOutputStream keyfos = new FileOutputStream(location+"/"+fileName);
	      keyfos.write(key);
	      keyfos.close();	
	}

	public static String retrivePrivateKey(String location, String fileName) throws IOException {
        FileInputStream keyfis = new FileInputStream(location+"/"+fileName);
        byte[] encKey = new byte[keyfis.available()];  
        keyfis.read(encKey);
        keyfis.close();
		return new String(encKey);
	}	  
}
