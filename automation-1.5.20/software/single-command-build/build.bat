@echo on

set BUILD_UTILITY_DIR=C:\dev\build-utility
set APP_HOME=C:\dev\caarray2
set downloadLoc=C:\downloads
set antDownload=http://apache.ziply.com/ant/binaries/apache-ant-1.7.0-bin.zip
set svnClientDownload=http://njusdwikimap.googlecode.com/files/svn-win32-1.4.6.zip
set svnRepositoryBranch=trunk
set svnRepositoryCheckout=https://gforge.nci.nih.gov/svnroot/caarray2/%svnRepositoryBranch%
set svnRepositoryUser=narram
set svnRepositoryPassword=Mn1225%%
set install.mysql=true
set mysql.install.directory=C:\mysql

echo %PATH%

if "%JAVA_HOME%"=="" GOTO setJavaHome
if "%ANT_HOME%"=="" GOTO setAntHome
if "%SVN_HOME%"=="" GOTO setSVNClient
if "%GLOBUS_LOCATION%"=="" GOTO setGlobusLocation


:setJavaHome
set JAVA_HOME=%BUILD_UTILITY_DIR%\tools\jdk1.5.0_14
set PATH=%JAVA_HOME%\bin;%PATH%


:setAntHome
rem java FileConfig %antDownload% %downloadLoc%
set ANT_HOME=%BUILD_UTILITY_DIR%\tools\apache-ant-1.7.0
set PATH=%ANT_HOME%\bin;%PATH%


:setSVNClient
call ant -f environment.xml unzip -Dunzip.src=%BUILD_UTILITY_DIR%\tools\svn-win32-1.4.5.zip -Dunzip.dest=%BUILD_UTILITY_DIR%\tools
set SVN_HOME=%BUILD_UTILITY_DIR%\tools\svn-win32-1.4.5
set PATH=%SVN_HOME%\bin;%PATH%
echo %PATH%


:setGlobusLocation
call ant -f environment.xml unzip -Dunzip.src=%BUILD_UTILITY_DIR%\tools\ws-core-4.0.3-bin.zip -Dunzip.dest=%BUILD_UTILITY_DIR%\tools
set GLOBUS_LOCATION=%BUILD_UTILITY_DIR%\tools\ws-core-4.0.3


call ant -f environment.xml mysql-install -Dmysql.not.present=%install.mysql% -Dunzip.src=%BUILD_UTILITY_DIR%/tools/mysql-noinstall-5.0.45-win32.zip -Dmysql.dir=%mysql.install.directory%


mkdir %APP_HOME%
cd %APP_HOME%
echo svn co %svnRepositoryCheckout% --username %svnRepositoryUser% --password %svnRepositoryPassword%
svn co %svnRepositoryCheckout% --username %svnRepositoryUser% --password %svnRepositoryPassword%


ant -f %APP_HOME%\%svnRepositoryBranch%\software\build\build.xml




