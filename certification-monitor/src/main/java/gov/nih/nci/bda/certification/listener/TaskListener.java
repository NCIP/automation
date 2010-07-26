package gov.nih.nci.bda.certification.listener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

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

    public void buildFinished(BuildEvent event) {
        System.out.println("START FROM THE TASKLISTENER");
        System.out.println(taskList);
        System.out.println("END FROM THE TASKLISTENER");
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
        addTask(event.getTask().getTaskName());
        System.out.println("taskFinished:" + event.getTask().getTaskName());

    }

    private void addTask(String taskName) {
        taskList.append("," + taskName);
    }

    public void taskStarted(BuildEvent event) {
    }

    public void writeProperty(String propertyName, String propertyValue) throws IOException {

        this.getWriter().write(propertyName + "=" + propertyValue +"\r\n");
        this.getWriter().flush();

    }


    private BufferedWriter out;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();    //To change body of overridden methods use File | Settings | File Templates.
        out.flush();
        out.close();
    }

    public BufferedWriter getWriter() {
        if (out == null) {
            try {
                // Create file
                FileWriter fstream = new FileWriter(this.getPropertyFilename());
                out = new BufferedWriter(fstream);
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }

        return out;  //To change body of created methods use File | Settings | File Templates.
    }

    public String getPropertyFilename() {
        return this.filename;  //To change body of created methods use File | Settings | File Templates.
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        System.out.println("TaskListener:setProject");
        this.project = project;
        this.filename = project.getProperty("gov.nih.nci.bda.certification.listener.TaskListener.propertysavefile");
        System.out.println("TaskListener:filename=" + filename );
    }
}
