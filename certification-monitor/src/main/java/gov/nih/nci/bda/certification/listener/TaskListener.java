package gov.nih.nci.bda.certification.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

/**
 * @author narram
 */
public class TaskListener implements BuildListener {

    StringBuffer taskList = new StringBuffer();
    Project project = null;
    private String filename;
    private String propertiesToSaveExpression;

    public void buildFinished(BuildEvent event) {
        System.out.println("START FROM THE TASKLISTENER");
        System.out.println(taskList);
        System.out.println("END FROM THE TASKLISTENER");


        Object propertiesToSave = null;

        Project thisProject = event.getProject();

        Hashtable properties = thisProject.getProperties();

        if (properties != null &&  properties.containsKey("gov.nih.nci.bda.certification.listener.TaskListener.properties.to.save")) {

            propertiesToSave = properties.get("gov.nih.nci.bda.certification.listener.TaskListener.properties.to.save");

            if (propertiesToSave != null) {
                this.setPropertiesToSaveExpression(propertiesToSave.toString());
            }
        }

        try {
            this.saveProperties(thisProject);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void buildStarted(BuildEvent arg0) {

        if (this.getProject() == null) {
            this.setProject(arg0.getProject());
        }
    }

    public void messageLogged(BuildEvent arg0) {

    }

    public void targetFinished(BuildEvent event) {
    }

    public void targetStarted(BuildEvent event) {

    }

    public void taskFinished(BuildEvent event) {
        String taskName = event.getTask().getTaskName();
        addTask(taskName);
        System.out.println("taskFinished:" + taskName);
    }

    private void addTask(String taskName) {
        taskList.append("," + taskName);
    }

    public void taskStarted(BuildEvent event) {
    }

    public void writeProperty(String propertyName, String propertyValue) throws IOException {

        this.getWriter(project).write(propertyName + "=" + propertyValue + "\r\n");
    }


    private BufferedWriter out;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();    //To change body of overridden methods use File | Settings | File Templates.
        out.flush();
        out.close();
    }

    public BufferedWriter getWriter(Project project) {

        if (out == null) {
            try {
                // Create file
                File theFile = new File(this.getPropertyFilename(project));
                theFile.createNewFile();
                System.out.println("TaskListener:getWrite theFile = " + theFile.getAbsolutePath());
                FileWriter fstream = new FileWriter(theFile);
                out = new BufferedWriter(fstream);
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }

        return out;  //To change body of created methods use File | Settings | File Templates.
    }

    public String getPropertyFilename(Project project) {
        this.setProject(project);
        return this.filename;  //To change body of created methods use File | Settings | File Templates.
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        System.out.println("TaskListener:setProject");
        this.project = project;
        this.filename = project.getProperty("gov.nih.nci.bda.certification.listener.TaskListener.propertysavefile");
        System.out.println("TaskListener:filename=" + this.filename);
        for (Object k : project.getProperties().keySet()) {
            System.out.println("TaskListener:" + k.toString() + "=" + project.getProperty(k.toString()));
        }
    }

    public void saveProperties(Project project) throws Exception {


        System.out.println("saveProperties()");

        if (this.getWriter(project) != null) {

            for (Object checkToSave : project.getProperties().keySet()) {
                if (this.ShouldSave(checkToSave.toString())) {
                    System.out.println("Saving property:" + checkToSave.toString() + "=" + project.getProperties().get(checkToSave.toString()).toString());

                    this.addPropertyValue(checkToSave.toString()
                            , project.getProperties().get(checkToSave.toString()).toString());
                }
            }

            for (String key : this.getPropertyValues().keySet()) {
                this.writeProperty(key, this.getPropertyValues().get(key));
            }

            this.getWriter(project).flush();
            this.getWriter(project).close();
        }

    }

    public void addPropertyValue(String name, String value) throws Exception {

        if (name == null || name.trim().length() == 0) {
            throw new NullPointerException();
        }

        if (value == null || value.trim().length() == 0) {
            throw new NullPointerException();
        }

        if (this.getPropertyValues().containsKey(name)) {
            throw new Exception("Duplicate key '" + name + "'");
        }

        this.getPropertyValues().put(name, value);
    }

    private Map<String, String> propertyValues;

    public Map<String, String> getPropertyValues() {

        if (propertyValues == null) {
            propertyValues = new HashMap<String, String>();
        }

        return propertyValues;
    }

    public boolean ShouldSave(String propertyNameExpression) {

        boolean returnValue = false;

        String[] expressions = this.getPropertiesToSaveExpression().split(",");

        Pattern p;


        for (String targetPropertyName : expressions) {
            p = Pattern.compile(targetPropertyName);

            if (p.matcher(propertyNameExpression).matches()) {
                returnValue = true;
                System.out.println("ShouldSave:" + targetPropertyName + propertyNameExpression + "=true");
                break;
            }
            System.out.println("ShouldSave:" + targetPropertyName + propertyNameExpression + "=false");

        }

        return returnValue;
    }


    public String getPropertiesToSaveExpression() {
        if (this.propertiesToSaveExpression == null) {
            this.propertiesToSaveExpression = "";
        }
        return this.propertiesToSaveExpression;
    }

    public void setPropertiesToSaveExpression(String value) {
        System.out.println("TaskListener.setPropertiesToSaveExpression()=" + value);
        this.propertiesToSaveExpression = value;
    }
}
