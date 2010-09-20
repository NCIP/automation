if [ $# -ne 2 ]; then 
echo "Usage : cagrid-sync.sh <svn.user> <svn.password>"
exit 1
fi
wget -r  -l 100 --no-parent --reject "index.html*" -o /maven/logs/cagrid-1.2-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.2/
wget -r  -l 100 --no-parent --reject "index.html*" -o /maven/logs/cagrid-1.2.0.1-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.2.0.1/
wget -r  -l 100 --no-parent --reject "index.html*" -o /maven/logs/cagrid-1.3.0.1-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.3.0.1/
wget -r  -l 100 --no-parent --reject "index.html*" -o /maven/logs/cagrid-1.3.0.2-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.3.0.2/
wget -r  -l 100 --no-parent --reject "index.html*" -o /maven/logs/cagrid-1.3-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.3/
wget -r  -l 100 --no-parent --reject "index.html*" -o /maven/logs/cagrid-1.4-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.4/
wget -r  -l 100 --no-parent --reject "index.html*" -o /maven/logs/cagrid-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tp/wget http://software.cagrid.org/repository/

cd /maven/tmp/wget/software.cagrid.org
cp -r * /maven/cagrid-repo-copy

cd /maven/cagrid-repo-copy
find . -name "*.jar" > file-list.txt

cd /maven/cagrid-repo-copy
/maven/bin/cagrid-svn.pl -u $1 -p $2
