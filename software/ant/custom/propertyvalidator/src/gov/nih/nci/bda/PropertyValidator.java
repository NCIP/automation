package gov.nih.nci.bda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.tools.ant.BuildException;

public class PropertyValidator extends org.apache.tools.ant.Task {

	private String keyFile;

	private String compareFile;

	public void setCompareFile(String pCompareFile) {
		this.compareFile = pCompareFile;
	}

	public void setKeyFile(String pKeyFile) {
		this.keyFile = pKeyFile;
	}

	public void execute() throws BuildException {
		Properties keyProperties = new Properties();
		Properties compareProperties = new Properties();
		try {
			keyProperties.load(new FileInputStream(new File(this.keyFile)));
			compareProperties.load(new FileInputStream(new File(
					this.compareFile)));

			SortedSet sortedKeyProperties = new TreeSet(keyProperties.keySet());
			SortedSet sortedCompareProperties = new TreeSet(compareProperties
					.keySet());

			if (sortedKeyProperties.equals(sortedCompareProperties)) {
				System.out.println("Task completed successfully. "
						+ this.keyFile + " matches exactly " + this.compareFile
						+ ".");
			} else {
				Iterator keyIterator = sortedKeyProperties.iterator();
				Iterator compareIterator = sortedCompareProperties.iterator();

				StringBuffer msgExcKeys = new StringBuffer(
						"Excess key(s) in template file " + this.keyFile
								+ ": - ");
				while (keyIterator.hasNext()) {
					String key = (String) keyIterator.next();
					if (!sortedCompareProperties.contains(key)) {
						msgExcKeys.append(key).append(" ");
					}
				}

				StringBuffer msgExcComp = new StringBuffer(
						"Excess key(s) in environment file " + this.compareFile
								+ ": - ");
				while (compareIterator.hasNext()) {
					String key = (String) compareIterator.next();
					if (!sortedKeyProperties.contains(key)) {
						msgExcComp.append(key).append(" ");
					}
				}

				throw new BuildException("\n" + this.compareFile
						+ " does not match " + this.keyFile + ". \n"
						+ msgExcKeys.toString() + "\n" + msgExcComp.toString()
						+ "\n" + "Operation aborted.");
			}
		} catch (FileNotFoundException e) {
			throw new BuildException(e);
		} catch (IOException e) {
			throw new BuildException(e);
		}

	}
}
