#!/bin/sh

#set HOME
CURR_DIR=`pwd`
cd `dirname "$0"`/..
SPIDER_HOME=`pwd`
cd $CURR_DIR


if [ -z "$SPIDER_HOME" ] ; then
    echo
    echo "Error: SPIDER_HOME environment variable is not defined correctly."
    echo
    exit 1
fi


#check JAVA_HOME & java
noJavaHome=false
if [ -z "$JAVA_HOME" ] ; then
    noJavaHome=true
fi
if [ ! -e "$JAVA_HOME/bin/java" ] ; then
    noJavaHome=true
fi
if $noJavaHome ; then
    echo
    echo "Error: JAVA_HOME environment variable is not set."
    echo
    exit 1
fi

#set JAVA_OPTS
JAVA_OPTS="-server -Xms512m -Xmx2G -XX:MaxPermSize=256M -XX:+AggressiveOpts -XX:MaxDirectMemorySize=2G"
#jvm info
#JAVA_OPTS="$JAVA_OPTS -XX:+PrintCommandLineFlags -showversion"
#performance Options
#JAVA_OPTS="$JAVA_OPTS -Xss256k"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseBiasedLocking"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseFastAccessorMethods"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
#JAVA_OPTS="$JAVA_OPTS -XX:+CMSParallelRemarkEnabled"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSCompactAtFullCollection"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSInitiatingOccupancyOnly"
#JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=75"
#JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=75"

#GC Log Options
#JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
JAVA_OPTS="$JAVA_OPTS -Xloggc:${SPIDER_HOME}/logs/gc.log"
#OOM dump
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${SPIDER_HOME}/"
#debug Options
#JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8805"
# ZK
#JAVA_OPTS="$JAVA_OPTS -Djava.security.auth.login.config=/etc/security/apps_client_jaas.conf"

#get input arg
SPIDER_ARG=""
for arg in "$@"
do
	SPIDER_ARG="$SPIDER_ARG '$arg'"
done

#set CLASSPATH
SPIDER_CLASSPATH="$SPIDER_HOME/conf:$SPIDER_HOME/lib/*"


#start
RUN_CMD="\"$JAVA_HOME/bin/java\""
RUN_CMD="$RUN_CMD -DappName=SPIDER -DSPIDER_HOME=\"$SPIDER_HOME\""
RUN_CMD="$RUN_CMD -classpath \"$SPIDER_CLASSPATH\""
RUN_CMD="$RUN_CMD $JAVA_OPTS"
RUN_CMD="$RUN_CMD package sample.av.HhhInfo $SPIDER_ARG"
RUN_CMD="$RUN_CMD 2>&1 "
#echo $RUN_CMD
eval $RUN_CMD
#echo $! > $SPIDER_HOME/bin/DUMP.pid

