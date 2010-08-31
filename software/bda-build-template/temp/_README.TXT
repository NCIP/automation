PREREQUISITES: 
1) Java SDK and Ant build tool must be installed
2) Ant build tool must be installed
3) Connection to Internet (for initial download of JARs and Apache Tomcat web container)
4) Mozilla Firefox should be installed to run the Selenium web-based functional tests
5) Using Subversion (TortoiseSVN, RapidSVN, etc.) perform a checkout from http://integratebutton-svn.cvsdude.com/bda-blueprints 
Username: integratebutton 
Password: !ntegrate

USAGE:
Open a command window and type 'ant start-db' from command line
Open a new command window and type 'ant' from command line

By typing ant, the following is executed:
1) Compilation (Java source and test files)
2) Database Integration (using HSQLDB - in memory)
3) Runs the Tests (unit, component, functional, performance, dependencies)
4) Runs Inspectons (code coverage, cyclomatic complexity, code duplication check, coding standard checks, dependency analysis)
5) Deployment (Packaging and deployment to Tomcat web container)

Note: Simian is not a free product. After a 15-day trial, you must order it from http://www.redhillconsulting.com.au/products/simian/

CruiseControl
----------------
1) Download from http://cruisecontrol.sourceforge.net/
2) Install by running the installation executable or extracting the .zip file
3) Start the Server
4) Copy the files from http://integratebutton-svn.cvsdude.com/bda-blueprints/config/cruisecontrol to your local CruiseControl installation and restart the CruiseControl server. This runs a full-featured CI system with CruiseControl and Ant for a Java web application