import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper
import org.codehaus.groovy.runtime.InvokerHelper

class BDACertificationTest extends GroovyTestCase 
{    

	void testBdafied() 
	{        
		def antFile = new File("build/build.xml")
		assertTrue "Couldn't find ant test script", antFile.exists()

		def project = new Project()
		project.init()
		project.setProperty("project.name", "petstore");
		ProjectHelper.configureProject(project, antFile)
		
		project.executeTarget("validate:svn:checkout-project");

		def ant = new AntBuilder();
		def loader = new GroovyClassLoader(getClass().getClassLoader())
		def testClass = loader.parseClass(new File("build/CertificationUtils.groovy"))			

		InvokerHelper.invokeMethod(testClass.newInstance(ant,project),"checkBdaEnabled",null)

		assertTrue "PROJECT BDAFIED", project.properties['certification.property.value'] != ''
	}
	
	void testDeployment() 
	{        
		
		assertTrue(true);    
	}
}