package gov.nih.nci.bda.certification.domain;


public class ProjectCertificationStatus {
	private int id;
	private String product;
	private String certificationStatus;
	private String certificationWikiStatus;
	private String singleCommandBuild;
	private String singleCommandDeployment;
	private String databaseIntegration;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCertificationStatus() {
		return certificationStatus;
	}
	public void setCertificationStatus(String certificationStatus) {
		this.certificationStatus = certificationStatus;
	}
	public String getCertificationWikiStatus() {
		return certificationWikiStatus;
	}
	public void setCertificationWikiStatus(String certificationWikiStatus) {
		this.certificationWikiStatus = certificationWikiStatus;
	}
	public String getSingleCommandBuild() {
		return singleCommandBuild;
	}
	public void setSingleCommandBuild(String singleCommandBuild) {
		this.singleCommandBuild = singleCommandBuild;
	}
	public String getSingleCommandDeployment() {
		return singleCommandDeployment;
	}
	public void setSingleCommandDeployment(String singleCommandDeployment) {
		this.singleCommandDeployment = singleCommandDeployment;
	}
	public String getDatabaseIntegration() {
		return databaseIntegration;
	}
	public void setDatabaseIntegration(String databaseIntegration) {
		this.databaseIntegration = databaseIntegration;
	}
}
