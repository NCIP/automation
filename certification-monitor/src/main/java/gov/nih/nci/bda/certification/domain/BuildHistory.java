package gov.nih.nci.bda.certification.domain;

import java.sql.Timestamp;

public class BuildHistory 
{
	private int buildId;
	private String product;
	private String buildTier;
    private String buildStatus;
    private String wikiBuildStatus;    
    private Timestamp lastBuildTime;
    
	public int getBuildId() {
		return buildId;
	}
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBuildTier() {
		return buildTier;
	}
	public void setBuildTier(String buildTier) {
		this.buildTier = buildTier;
	}
	public String getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}
	public String getWikiBuildStatus() {
		return wikiBuildStatus;
	}
	public void setWikiBuildStatus(String wikiBuildStatus) {
		this.wikiBuildStatus = wikiBuildStatus;
	}
	public Timestamp getLastBuildTime() {
		return lastBuildTime;
	}
	public void setLastBuildTime(Timestamp lastBuildTime) {
		this.lastBuildTime = lastBuildTime;
	}
    

}
