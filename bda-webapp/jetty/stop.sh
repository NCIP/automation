PID=$(cat jetty.pid)
if [ -e /proc/${PID} -a /proc/${PID}/exe -ef /usr/bin/program ]; then
kill -9 ${PID}
fi