package gov.nih.nci.bda.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 5, 2010
 * Time: 1:41:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectAction {
    private int id;
    private int type;
    private Date date;
    private String notes;
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
