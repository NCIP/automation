package gov.nih.nci.bda.domain;

import java.sql.Time;
import java.util.Calendar;
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
    private ProjectActionType type;
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
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        if( c.get(Calendar.HOUR) == 0
            && c.get(Calendar.MINUTE) == 0
            && c.get(Calendar.SECOND) == 0) {
            throw new IllegalArgumentException("Must specify time in date.");
        }
        this.date = date;
    }

    public ProjectActionType getType() {
        return type;
    }

    public void setType(ProjectActionType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
