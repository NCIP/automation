package gov.nih.nci.bda.provisioner;

public class ProvisionerManager {
	  public static void main(String args[])
	  {
		  Provisioner ec2p = new EC2Provisioner();
		  try {
			ec2p.provisionInstance();  

		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
}
