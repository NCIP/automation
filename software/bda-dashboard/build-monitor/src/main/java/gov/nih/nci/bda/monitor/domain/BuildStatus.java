package gov.nih.nci.bda.monitor.domain;

import java.sql.Timestamp;

public class BuildStatus {
	private int buildId;
	private String buildTier;
    private String buildStatus;
    private String wikiBuildStatus;    
    private Timestamp lastBuildTime;
    
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

	public int getBuildId() {
		return buildId;
	}
	public void setBuildId(int buildId) {
		this.buildId = buildId;
	}
	public Timestamp getLastBuildTime() {
		return lastBuildTime;
	}
	public void setLastBuildTime(Timestamp lastBuildTime) {
		this.lastBuildTime = lastBuildTime;
	}
	public String getWikiBuildStatus() {
		return wikiBuildStatus;
	}
	public void setWikiBuildStatus(String wikiBuildStatus) {
		this.wikiBuildStatus = wikiBuildStatus;
	}


}
