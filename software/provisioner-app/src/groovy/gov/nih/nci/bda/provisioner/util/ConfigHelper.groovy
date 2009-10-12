
package gov.nih.nci.bda.provisioner.util;
/**
 *
 * @author Mahidhar Narra
 */

import java.io.File;


class ConfigHelper {

    def setSCMProjectUrlValue (String fileName,String xPath,String projectUrl)
    {
		def root = new XmlParser().parseText(new File(fileName).getText())			
		def remote = root.scm.locations.'hudson.scm.SubversionSCM_-ModuleLocation'.remote
		remote.each { g ->
    		g.value = projectUrl
		}

		def writer = new StringWriter()
		
		def nodePrinter = new XmlNodePrinter(new PrintWriter(fileName))
		nodePrinter.setPreserveWhitespace(true)
		nodePrinter.print(root)
		println root.scm.locations.'hudson.scm.SubversionSCM_-ModuleLocation'.remote.text()
		/*	
		root.each  { node ->
			println("${node}");
			if(${node}.equals(xPath))
				{
					println("${xPath}");
				}
		}
		*/	
	}
}
