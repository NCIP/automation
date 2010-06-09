#!/bin/bash

alias ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"='ps -ef |grep java |grep -v grep |awk "{print \"kill -9 \"\$2}" | sh'

cd ..
rm -rf ../target ~/.ivy2-bda-utils-cbiit
# Backup the properties file
cp install.properties test/
cp project.properties test/

# set java home
export JAVA_HOME=/opt/jdk1.5.0_21-64bit
export PATH=$JAVA_HOME/bin:$PATH

# Copy in test properties files
cp test/install-40-nondac.properties install.properties
cp test/project-40-nondac.properties project.properties

# install
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:install
if [ $? -ne 0 ]; then
	mail -s "Test failed install-40-nondac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi
# upgrade
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:upgrade
if [ $? -ne 0 ]; then
	mail -s "Test failed upgrade-40-nondac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi

# Copy in test properties files
cp test/install-40-dac.properties install.properties
cp test/project-40-dac.properties project.properties

# install
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:install
if [ $? -ne 0 ]; then
	mail -s "Test failed install-40-dac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi
# upgrade
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:upgrade
if [ $? -ne 0 ]; then
	mail -s "Test failed upgrade-40-dac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi

# set java home
export JAVA_HOME=/opt/jdk1.6.0_14-64bit
export PATH=$JAVA_HOME/bin:$PATH

# Copy in test properties files
cp test/install-51-nondac.properties install.properties
cp test/project-51-nondac.properties project.properties

# install
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:install
if [ $? -ne 0 ]; then
	mail -s "Test failed install-51-nondac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi
# upgrade
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:upgrade
if [ $? -ne 0 ]; then
	mail -s "Test failed upgrade-51-nondac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi

# Copy in test properties files
cp test/install-51-dac.properties install.properties
cp test/project-51-dac.properties project.properties

# install
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:install
if [ $? -ne 0 ]; then
	mail -s "Test failed install-51-dac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi
# upgrade
ps -ef |grep java |grep -v grep |awk "{print "kill -9 "$2}"
ant deploy:local:upgrade
if [ $? -ne 0 ]; then
	mail -s "Test failed upgrade-51-dac" steven.saksa@stelligent.com < /dev/null
	exit 1
fi

