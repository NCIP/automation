package gov.nih.nci.bda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;

public class PropertyValidator extends org.apache.tools.ant.Task {

	private String keyFile;

	private String compareFile;

	private String match = "exactly";
	
	private boolean readInMemory = false;
	
	private String excludePatternList;

	public void setCompareFile(String pCompareFile) {
		this.compareFile = pCompareFile;
	}

	public void setKeyFile(String pKeyFile) {
		this.keyFile = pKeyFile;
	}

	public void setMatch(String pMatch) {
		this.match = pMatch;
	}

	public void setReadInMemory(boolean readInMemory) {
		this.readInMemory = readInMemory;
	}
	
	private void matchExactly(Properties pKeyProperties, Properties pCompareProperties) {
		try {
			pKeyProperties.load(new FileInputStream(new File(this.keyFile)));
			pCompareProperties.load(new FileInputStream(new File(this.compareFile)));

			SortedSet sortedKeyProperties = new TreeSet(pKeyProperties.keySet());
			SortedSet sortedCompareProperties = new TreeSet(pCompareProperties.keySet());

			if (sortedKeyProperties.equals(sortedCompareProperties)) {
				System.out.println("Task completed successfully. " + this.keyFile + " matches exactly "
						+ this.compareFile + ".");
			} else {
				Iterator keyIterator = sortedKeyProperties.iterator();
				Iterator compareIterator = sortedCompareProperties.iterator();

				StringBuffer msgExcKeys = new StringBuffer("Excess key(s) in template file " + this.keyFile + ": - ");
				while (keyIterator.hasNext()) {
					String key = (String) keyIterator.next();
					if (!sortedCompareProperties.contains(key)) {
						msgExcKeys.append(key).append(" ");
					}
				}

				StringBuffer msgExcComp = new StringBuffer("Excess key(s) in environment file " + this.compareFile
						+ ": - ");
				while (compareIterator.hasNext()) {
					String key = (String) compareIterator.next();
					if (!sortedKeyProperties.contains(key)) {
						msgExcComp.append(key).append(" ");
					}
				}

				throw new BuildException("\n" + this.compareFile + " does not match " + this.keyFile + ". \n"
						+ msgExcKeys.toString() + "\n" + msgExcComp.toString() + "\n" + "Operation aborted.");
			}
		} catch (FileNotFoundException e) {
			throw new BuildException(e);
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}

	private void matchAtLeast(Properties pKeyProperties, Properties pCompareProperties) {
		try {
			StringBuffer msgExcKeys = new StringBuffer("");
			pKeyProperties.load(new FileInputStream(new File(this.keyFile)));
			pCompareProperties.load(new FileInputStream(new File(this.compareFile)));
			boolean keyMatched = false;
			if(readInMemory)
				loadDefinedProperties(pCompareProperties);

			SortedSet sortedKeyProperties = new TreeSet(pKeyProperties.keySet());
			SortedSet sortedCompareProperties = new TreeSet(pCompareProperties.keySet());

			if (sortedKeyProperties.equals(sortedCompareProperties)) {
				System.out.println("Task completed successfully. " + this.keyFile + " matches exactly "
						+ this.compareFile + ".");
			} else {
				Iterator keyIterator = sortedKeyProperties.iterator();
				Iterator compareIterator = sortedCompareProperties.iterator();
				System.out.println("Exclude Pattern List::"+ excludePatternList);				
				
				while (keyIterator.hasNext()) {
					keyMatched = false;
					String key = (String) keyIterator.next();
					if(excludePatternList != null)
					{
						for (String excludePattern : excludePatternList.split(","))
						{
							Pattern regPattern = Pattern.compile(excludePattern);
							if(regPattern.matcher(key).matches())
							{
								keyMatched = true;
							}
						}
					}else
					{
						// if there are not patterns to be excluded 
						if (!sortedCompareProperties.contains(key)) {						
							msgExcKeys.append(key).append(" ");
						}							
					}
					// exclude the keys to be matched
					if(!keyMatched)
					{
						if (!sortedCompareProperties.contains(key)) {						
							msgExcKeys.append(key).append(" ");
						}
					}
						
				if (msgExcKeys.length() != 0)
					throw new BuildException("\n" + this.compareFile + " does not match " + this.keyFile + ". \n"
							+"Excess key(s) in template file " + this.keyFile +"\n" + msgExcKeys.toString() + "\n" + "Operation aborted.");					
				}

				while (compareIterator.hasNext()) {
					String key = (String) compareIterator.next();
					if (!sortedKeyProperties.contains(key)) {
						msgExcKeys.append(key).append(" ");
					}
				}
				
				if (msgExcKeys.length() != 0){
					System.out.println("Excess key(s) in environment file " + this.compareFile +"\n" + msgExcKeys.toString());
				}								
			}
		} catch (FileNotFoundException e) {
			throw new BuildException(e);
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}

	private void loadDefinedProperties(Properties compareProperties) {
		compareProperties.putAll(this.getProject().getProperties());
	}

	public void execute() throws BuildException {
		Properties keyProperties = new Properties();
		Properties compareProperties = new Properties();

		if (this.match.equalsIgnoreCase("atleast")) {
			this.matchAtLeast(keyProperties, compareProperties);
		} else {
			this.matchExactly(keyProperties, compareProperties);
		}

	}

	public String getExcludePatternList() {
		return excludePatternList;
	}

	public void setExcludePatternList(String excludePatternList) {
		this.excludePatternList = excludePatternList;
	}
}
