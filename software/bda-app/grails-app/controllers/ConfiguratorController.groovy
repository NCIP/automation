class ConfiguratorController {

    def index = 
	{ 
	redirect(action: "main")
    	}
    def main =
	{
	}
    def home =
	{
		render"<h1>This is my test home</h1>"
	}
    def configWizardFlow =
	{
		projectInfo
		{
		 	on("next")
			{
				//collect project info
			}.to("buildDetails")
			on("cancel").to("main")
		}
		buildDetails
		{
		 	on("next")
			{
				//collect sub-project info
			}.to("distDeployDetails")
			on("cancel").to("main")
			on("previous").to("projectInfo")
		}
		distDeployDetails
		{
		 	on("next")
			{
				//collect sub-project info
			}.to("jbossContainerInfo")
			on("cancel").to("main")
			on("previous").to("buildDetails")
		}
		jbossContainerInfo
		{
		 	on("next")
			{
				//collect sub-project info
			}.to("tomcatContainerInfo")
			on("cancel").to("main")
			on("previous").to("archInfo")
		}
		tomcatContainerInfo
		{
		 	on("next")
			{
				//collect sub-project info
			}.to("databaseContainerInfo")
			on("cancel").to("main")
			on("previous").to("jbossContainerInfo")
		}
		databaseContainerInfo
		{
		 	on("next")
			{
				//collect sub-project info
			}.to("customContainerInfo")
			on("cancel").to("main")
			on("previous").to("tomcatContainerInfo")
		}
		customContainerInfo
		{
		 	on("next")
			{
				//collect sub-project info
			}.to("acceptConfiguration")
			on("cancel").to("main")
			on("previous").to("databaseContainerInfo")
		}
		acceptConfiguration
		{
		 	on("next")
			{
				//collect sub-project info
			}.to("finishConfigurator")
			on("cancel").to("main")
			on("previous").to("databaseContainerInfo")
		}
		finishConfigurator
		{
			redirect(action: "main")
		}
	}
}
