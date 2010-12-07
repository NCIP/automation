import ConfigBuilder 
class BdaController {

    def index = { }
    
    def save = {  	
    		println "111"
   			ConfigBuilder cb = new ConfigBuilder(params);
   			println "222"
   			render "success"
	}
}
