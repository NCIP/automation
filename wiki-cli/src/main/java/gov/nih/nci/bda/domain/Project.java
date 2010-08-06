package gov.nih.nci.bda.domain;

/**
 * Created by IntelliJ IDEA.
 * User: hudsonuser
 * Date: Aug 5, 2010
 * Time: 12:51:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Project {
    private int id;
    private String name;
    private String developer;
    private String developerEmail;
    private String government;
    private String governmentEmail;
    private String cotr;
    private String cotrEmail;
    private String friendlyName;
    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getCotrEmail() {
        return cotrEmail;
    }

    public void setCotrEmail(String cotrEmail) {
        this.cotrEmail = cotrEmail;
    }

    public String getCotr() {
        return cotr;
    }

    public void setCotr(String cotr) {
        this.cotr = cotr;
    }

    public String getGovernmentEmail() {
        return governmentEmail;
    }

    public void setGovernmentEmail(String governmentEmail) {
        this.governmentEmail = governmentEmail;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public void setDeveloperEmail(String developerEmail) {
        this.developerEmail = developerEmail;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
