package gov.nih.nci.bda.provisioner.domain;

public class ProjectInitialization {
private int id;
private String projectName;
private String commandName;
private String toLocation;
private String isFile;
private String runAsRoot;
private String isRebootRequired;
private String runCommand;

public String getRunCommand() {
	return runCommand;
}
public void setRunCommand(String runCommand) {
	this.runCommand = runCommand;
}
public String getIsRebootRequired() {
	return isRebootRequired;
}
public void setIsRebootRequired(String isRebootRequired) {
	this.isRebootRequired = isRebootRequired;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getProjectName() {
	return projectName;
}
public void setProjectName(String projectName) {
	this.projectName = projectName;
}
public String getCommandName() {
	return commandName;
}
public void setCommandName(String commandName) {
	this.commandName = commandName;
}
public String getToLocation() {
	return toLocation;
}
public void setToLocation(String toLocation) {
	this.toLocation = toLocation;
}
public String getIsFile() {
	return isFile;
}
public void setIsFile(String isFile) {
	this.isFile = isFile;
}
public String getRunAsRoot() {
	return runAsRoot;
}
public void setRunAsRoot(String runAsRoot) {
	this.runAsRoot = runAsRoot;
}

}
