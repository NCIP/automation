package bda.blueprints.business.domain;

public class Study {

	private String id;

	private String name;

	private String researcher;

	private String dateReceived;

	public String getDateReceived() {
		return dateReceived;
	}

	public String getId() {
		return id;

	}

	public String getName() {
		return name;

	}

	public String getResearcher() {
		return researcher;
	}

	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;

	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

}
