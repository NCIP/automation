import groovy.xml.MarkupBuilder

class ConfigBuilder {

	def ConfigBuilder(request)
	{
		println " config builder"
		def file = new File("C:\\dev\\automation\\trunk\\software\\bda-app\\config.xml")
		def builder = new MarkupBuilder(new FileWriter(file))
		println " Project name ${request.projectName} "		
		Project project1=new Project(projectName:request.projectName)
		SubProject subProject1=new SubProject(subProjectName:request.subProjectName,subProjectContainerType:request.subProjectContainerType,databaseType:request.databaseType)
		builder.bda_configuration{
			projectBuilder(builder,project1)
			subProjectBuilder(builder,subProject1)
		}
		println " End config builder"
	}
	
	def projectBuilder(builder,project)
	{	
		builder.'project'{
			projectName(project.projectName)
		}
	}

	def subProjectBuilder(builder,subProject)
	{	
		builder.'sub-project'{
			subProjectName(subProject.subProjectName)
			subProjectContainerType(subProject.subProjectContainerType)
			databaseType(subProject.databaseType)
		}
	}		
}

class Project {
	String projectName
}
class SubProject {
	String subProjectName,subProjectContainerType,databaseType
}