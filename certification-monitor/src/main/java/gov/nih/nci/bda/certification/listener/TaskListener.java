package gov.nih.nci.bda.certification.listener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import gov.nih.nci.bda.certification.domain.TargetLookup;
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
        this.saveProperties();
    }

    private void saveProperties() {
        //To change body of created methods use File | Settings | File Templates.
        String propertiesToSave = this.getProject().getProperties().get("gov.nih.nci.bda.certification.listener.TaskListener.properties.to.save").toString() ;
        System.out.println("TaskListener:propertiesToSave=" + propertiesToSave);


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
        String taskName = event.getTask().getTaskName() ;
        addTask(taskName);
        System.out.println("taskFinished:" + taskName);
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
        System.out.println("TaskListener:filename=" + this.filename );
        for(Object k:project.getProperties().keySet())
        {
            System.out.println("TaskListener:" + k.toString() + "=" + project.getProperty(k.toString()));
        }
    }

    public void writeFile() {
        
    }

    public void saveProperties(TargetLookup targetLookup, Project project) throws Exception {

        System.out.println("saveProperties()");

        for(Object checkToSave:project.getProperties().keySet())
        {
            if(this.ShouldSave(targetLookup,checkToSave.toString()))
            {
                System.out.println("Saving property:" + checkToSave.toString() + "=" + project.getProperties().get(checkToSave.toString()).toString());
                this.addPropertyValue(  checkToSave.toString()
                                        , project.getProperties().get(checkToSave.toString()).toString());
            }
        }
    }

    public void addPropertyValue(String name, String value) throws Exception {

        if (name == null || name.trim().length() == 0)
        {
            throw new NullPointerException();
        }

        if (value == null || value.trim().length() == 0)
        {
            throw new NullPointerException();
        }

        if (this.getPropertyValues().containsKey(name))
        {
            throw new Exception("Duplicate key '" + name + "'");
        }

        this.getPropertyValues().put(name,value);
    }

    private Map<String,String> propertyValues ;

    public Map<String,String> getPropertyValues() {

        if (propertyValues == null)
        {
            propertyValues = new HashMap<String,String>();
        }

        return propertyValues;
    }

    public boolean ShouldSave(TargetLookup target, String propertyNameExpression) {

        boolean returnValue = false ;
        String[] expressions = target.SaveExpressions();

        Pattern p ;


        for(String targetPropertyName:expressions)
        {
            p = Pattern.compile(targetPropertyName);

            if (p.matcher(propertyNameExpression).matches())
            {
                returnValue = true ;
                System.out.println("ShouldSave:" + targetPropertyName + propertyNameExpression + "=true");
                break;
            }
            System.out.println("ShouldSave:" + targetPropertyName + propertyNameExpression + "=false");

        }

        return returnValue;
    }


}
