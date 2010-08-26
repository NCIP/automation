package gov.nih.nci.bda.domain;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 6, 2010
 * Time: 12:37:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectActionType {

    private int id;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}