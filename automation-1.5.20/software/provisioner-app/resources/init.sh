#!/bin/sh
### ====================================================================== ###
##                                                                          ##
##      System Setup Script                                                 ##
##                                                                          ##
### ====================================================================== ###

echo $PATH
mkdir /tmp/test1
wget 'http://gforge.nci.nih.gov/svnroot/commonlibrary/trunk/techstack-2007/linux/jdk-1_5_0_19-linux-i586.rpm'
wget 'http://www.surfybeach.net/download/rpms/java-1.6.0-sun-compat-1.6.0.11-1jpp.i586.rpm'
chmod 700 jdk-1_5_0_19-linux-i586.rpm
rpm -i jdk-1_5_0_19-linux-i586.rpm
chmod 700 java-1.6.0-sun-compat-1.6.0.11-1jpp.i586.rpm
rpm -i java-1.6.0-sun-compat-1.6.0.11-1jpp.i586.rpm
wget http://apache.securedservers.com/ant/binaries/apache-ant-1.7.1-bin.zip
unzip apache-ant-1.7.1-bin.zip
mv apache-ant-1.7.1 /usr/local/ 
yum -y install subversion
yum -y install mysql mysql-server
ln -s /usr/java/jdk1.5.0_19/bin/java /usr/bin/java
ln -s /usr/java/jdk1.5.0_19/bin/javac /usr/bin/javac
ln -s /usr/local/apache-ant-1.7.1/bin/ant /usr/bin/ant
pass=$(perl -e 'print crypt($ARGV[0], "password")' 'password')
useradd -m -p $pass -d /mnt/hudsonuser hudsonuser
perl -i -pe 's/PasswordAuthentication no/ PasswordAuthentication yes/' /etc/ssh/sshd_config
ls -al