package gov.nih.nci.bda.certification.domain;

public class TargetLookup {
private int id;
private String targetName;
private String mapName;
private String isValue;
private String isOptional;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTargetName() {
	return targetName;
}
public void setTargetName(String targetName) {
	this.targetName = targetName;
}
public String getMapName() {
	return mapName;
}
public void setMapName(String mapName) {
	this.mapName = mapName;
}
public String getIsValue() {
	return isValue;
}
public void setIsValue(String isValue) {
	this.isValue = isValue;
}
public String getIsOptional() {
	return isOptional;
}
public void setIsOptional(String isOptional) {
	this.isOptional = isOptional;
}

}