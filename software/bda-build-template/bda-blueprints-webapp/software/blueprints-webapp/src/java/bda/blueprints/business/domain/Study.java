package bda.blueprints.business.domain;

public class Study {

	private String id;

	private String name;

	private String researcher;

	public String getResearcher() {
		return researcher;
	}

	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	private String dateReceived;

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;

	}

	public String getId() {
		return id;

	}

	public String getName() {
		return name;

	}

	public String getDateReceived() {
		return dateReceived;
	}

}
