package bda.blueprints.business.domain;


public class State {

	private String state;

	private String description;

	public void setState(String state) {
		this.state = state;
	}

	public void setDescription(String description) {
		this.description = description;
		// StudyServiceImpl impl = new StudyServiceImpl();
		// impl.runThis();

	}

	public String getState() {
		return state;

	}

	public String getDescription() {
		return description;

	}

}
