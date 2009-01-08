package gov.nih.nci.bda.certification.domain;


public class ProjectCertificationStatus {
	private int id;
	private String product;
    private String dev;
    private String qa;    
    private String stage;
    private String prod;
    
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
	public String getDev() {
		return dev;
	}
	public void setDev(String dev) {
		this.dev = dev;
	}
	public String getQa() {
		return qa;
	}
	public void setQa(String qa) {
		this.qa = qa;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
}
