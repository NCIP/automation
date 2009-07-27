# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
	. ~/.bashrc
fi
PATH=$PATH:$HOME/bin
# User specific environment and startup programs
ANT_HOME=/usr/local/apache-ant-1.7.1
JAVA_HOME=/usr/java/jdk1.5.0_19
HUDSON_HOME=$HOME/hudson_data
export ANT_HOME JAVA_HOME HUDSON_HOME
export PATH=$ANT_HOME/bin:$JAVA_HOME/bin:$PATH
