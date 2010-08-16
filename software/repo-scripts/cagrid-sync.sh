wget -r -N -nH -np -i inf -w 2 -o /maven/logs/cagrid-1.2-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.2/
wget -r -N -nH -np -i inf -w 2 -o /maven/logs/cagrid-1.2.0.1-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.2.0.1/
wget -r -N -nH -np -i inf -w 2 -o /maven/logs/cagrid-1.3.0.1-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.3.0.1/
wget -r -N -nH -np -i inf -w 2 -o /maven/logs/cagrid-1.3.0.2-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.3.0.2/
wget -r -N -nH -np -i inf -w 2 -o /maven/logs/cagrid-1.3-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.3/
wget -r -N -nH -np -i inf -w 2 -o /maven/logs/cagrid-1.4-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tmp/wget http://software.cagrid.org/repository-1.4/
wget -r -N -nH -np -i inf -w 2 -o /maven/logs/cagrid-`date "+%Y%m%d-%H%d%S"`.log -P /maven/tp/wget http://software.cagrid.org/repository/
cd /maven/tmp/wget
rsync --archive --verbose --exclude ".svn" --exclude "*index.html*" . /maven/cagrid-repo-copy
cd /maven/cagrid-repo-copy
for file in `ls -1`; do echo $file; find $file -name "*.jar" | wc -l; done
