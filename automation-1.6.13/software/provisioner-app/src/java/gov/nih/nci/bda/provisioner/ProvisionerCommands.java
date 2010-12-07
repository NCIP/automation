package gov.nih.nci.bda.provisioner;

import java.util.HashMap;

public class ProvisionerCommands 
{
	private static HashMap<String, String> allCommands;
	
	public static void addCommand(String commandName, String commandValue)
	{
		allCommands.put(commandName, commandValue);
	}

	public static String getCommand(String commandName)
	{
		return (String) allCommands.get(commandName);
	}

}
