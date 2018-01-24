#tomcat∆Ù∂Øpid

export JAVA_HOME=/opt/jdk

export CATALINA_HOME=/opt/tomcat

export CATALINA_BASE=/opt/tomcat

#add tomcat pid

CATALINA_PID="$CATALINA_BASE/tomcat.pid"

#add java opts


JAVA_OPTS="-server -Xms8192m -Xmx8192m -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=2048m -XX:MaxNewSize=896m -XX:NewSize=896m -XX:SurvivorRatio=6  -XX:+UseLargePages -Djava.net.preferIPv4Stack=true -Dserver=8080 -Dstart_scheduler=true -Dstart_biz_scheduler=true -Dfile.encoding=utf-8"