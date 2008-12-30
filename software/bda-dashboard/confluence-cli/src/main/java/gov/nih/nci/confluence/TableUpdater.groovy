package gov.nih.nci.confluence

import groovy.sql.Sql

class TableUpdater {

	static void main(String[] args) {

	    // DB drivers
	    protected final String DERBY_DRIVER   = "org.apache.derby.jdbc.EmbeddedDriver"


	    // Datamart DB access
	    protected final String DATAMART_DB       = "wikiDB"
	    protected final String DATAMART_URL      = "jdbc:derby:${DATAMART_DB};create=true"
	    protected final String DATAMART_DRIVER   = DERBY_DRIVER
	    protected final String DATAMART_USER     = ""
	    protected final String DATAMART_PASSWORD = ""

		protected final Sql connection = Sql.newInstance(DATAMART_URL, DATAMART_USER, DATAMART_PASSWORD, DATAMART_DRIVER)

		// Don't use bat file as it hides the exit code
		// Due to authority problems with automation, you may need to replace automation with personal id and password
		String confluence = "java -jar ./lib/confluence-cli-1.3.0.jar --server http://localhost:8080 --user automation --password password"

        // get most recent tempates
		doCmd("${confluence} -a getPageSource --space \"test\" --title \"table-template-page\"    --file test.txt")
		
        String whereClause

        if (args.length > 0) {
        	whereClause = ""
        } else {
        	whereClause = ""  
        }
        String orderClause = ""
		String statement   = "select TOOL_NAME, PROJECT_STATUS from MATURITY_MATRIX ${whereClause} ${orderClause}"

		boolean initialCreate = true

        int count = 0
        connection.eachRow(statement) { row ->
            count++

            String toolName    = row.TOOL_NAME.toLowerCase();
            String projectStatus = row.PROJECT_STATUS.toLowerCase();


			String findReplace = "--findReplace \"\$TOOL_NAME${count}:${toolName},\$PROJECT_STATUS${count}:${projectStatus}\""
	        
	        
			println toolName
			println projectStatus
			println findReplace

			// add pages
			doCmd("${confluence} -a storePage --space \"test\" --title \"page1\"   --file test.txt ${findReplace}")
			doCmd("${confluence} -a getPageSource --space \"test\" --title \"page1\"    --file test.txt")

		}
	
        println count + " users processed " + whereClause
        
	}

	static void doCmdWithException(String cmd) {

		Process process = doCmd(cmd)

	    if (process.exitValue() != 0) {
	    	println "ERROR VALUE: ${process.exitValue()}"
		   	println "ERROR TEXT:  ${process.err.text}"
		    throw new Exception("Command failure code: ${process.exitValue()}\n ${process.err.text()}\n ${cmd}")
		}
		return
	}

	static Process doCmd(String cmd) {

		println ">>> ${cmd}"
	    Process process = "cmd /c ${cmd}".execute()
	    println "${process.text} ${process.err.text}"
		return process
	}
}