package gov.nih.nci.bda.certification.domain;

import java.util.ArrayList;

public class TargetLookup {
private int id;
private String targetName;
private String mapName;
private String isValue;
private String isOptional;
    private String saveProperties;
    private String useProperties;

    public String getUseProperties() {
        if (useProperties == null)
        {
            useProperties = "" ;
        }
        return useProperties;
    }

    public void setUseProperties(String useProperties) {
        this.useProperties = useProperties;
    }

    public String getSaveProperties() {
        if ( saveProperties == null )
        {
            saveProperties = "";
        }
        return saveProperties;
    }

    public String[] SaveExpressions() {
        return this.getSaveProperties().split(",");
    }

    public void setSaveProperties(String saveProperties) {
        this.saveProperties = saveProperties;
    }

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

    public String[] UseExpressions() {
        return this.getUseProperties().split(",");
    }
}
