package gov.nih.nci.bda.certification.listener;

import java.util.ArrayList;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

/**
 * @author narram
 *
 */
public class TaskListener implements BuildListener {

	StringBuffer taskList = new StringBuffer();

	public void buildFinished(BuildEvent event) {
		System.out.println("START FROM THE TASKLISTENER");
		System.out.println(taskList);
		System.out.println("END FROM THE TASKLISTENER");
	}

	public void buildStarted(BuildEvent arg0) {
	}

	public void messageLogged(BuildEvent arg0) {

	}

	public void targetFinished(BuildEvent event) {
	}

	public void targetStarted(BuildEvent event) {

	}

	public void taskFinished(BuildEvent event) {
		addTask(event.getTask().getTaskName());
	}

	private void addTask(String taskName) {
		taskList.append("," + taskName);
	}

	public void taskStarted(BuildEvent event) {
	}

}
