package gov.nih.nci.bda.provisioner.domain;

import com.xerox.amazonws.ec2.InstanceType;

public class Instances {
	private int id;
	private String instanceId;
	private int  userId;
	private String projectName;
	private String instanceName;	
	private String instanceType;
	private String instanceStatus;
	private String instanceZone;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getInstanceType() {
		return instanceType;
	}
	public void setInstanceType(String instanceType2) {
		this.instanceType = instanceType2;
	}
	public String getInstanceStatus() {
		return instanceStatus;
	}
	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}	
	public String getInstanceZone() {
		return instanceZone;
	}
	public void setInstanceZone(String instanceZone) {
		this.instanceZone = instanceZone;
	}	
}

