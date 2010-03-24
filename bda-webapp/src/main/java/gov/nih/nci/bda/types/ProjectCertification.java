package gov.nih.nci.bda.types;

import gov.nih.nci.bda.types.Product;

public class ProjectCertification {
	public ProjectCertification() {

	}

	public ProjectCertification(Product product) {
		this.product = product;
	}

	private Product product;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String id;
	private String name;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
