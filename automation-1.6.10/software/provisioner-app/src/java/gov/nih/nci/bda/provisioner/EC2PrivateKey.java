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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
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
		  System.out.println("SAVING FILING IN " + location+"/"+fileName);
	      FileOutputStream keyfos = new FileOutputStream(location+"/"+fileName);
	      keyfos.write(key);
	      keyfos.close();
	}

	public static void saveKeyToDownloadDir(String keyMaterial, String location , String fileName) throws IOException {
		  byte[] key = keyMaterial.getBytes();
		  File locationDir = new File(location);
		  if(locationDir.exists())
			  locationDir.delete();
		  else
			  locationDir.mkdirs();	
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