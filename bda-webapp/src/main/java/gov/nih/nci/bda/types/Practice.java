package gov.nih.nci.bda.types;

public class Practice {
	public Practice() {

	}
	public Practice(String name) {
		this.name = name;
	}
	
	private String name;
	private String status;
	private String errorMessage;
	private String errorLocation;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorLocation() {
		return errorLocation;
	}
	public void setErrorLocation(String errorLocation) {
		this.errorLocation = errorLocation;
	}


}
