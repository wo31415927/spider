@echo off
setlocal
cd ..
set "SPIDER_HOME=%cd%"
cd %SPIDER_HOME%
IF NOT EXIST "logs" MD "logs"
echo %SPIDER_HOME%

rem set JAVA_OPTS
set "JAVA_OPTS=-server -Xms512m -Xmx4G -XX:MaxPermSize=256M -XX:+AggressiveOpts -XX:MaxDirectMemorySize=2G"
rem jvm info
rem JAVA_OPTS="$JAVA_OPTS -XX:+PrintCommandLineFlags -showversion"
rem performance Options
rem JAVA_OPTS="$JAVA_OPTS -Xss256k"
rem JAVA_OPTS="$JAVA_OPTS -XX:+UseBiasedLocking"
rem JAVA_OPTS="$JAVA_OPTS -XX:+UseFastAccessorMethods"
rem JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC"
rem JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC"
rem JAVA_OPTS="$JAVA_OPTS -XX:+CMSParallelRemarkEnabled"
rem JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSCompactAtFullCollection"
rem JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSInitiatingOccupancyOnly"
rem JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=75"
rem JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=75"

rem GC Log Options
rem JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime"
set "JAVA_OPTS=%JAVA_OPTS% -XX:+PrintGCDateStamps"
set "JAVA_OPTS=%JAVA_OPTS% -XX:+PrintGCDetails"
set "JAVA_OPTS=%JAVA_OPTS% -Xloggc:%SPIDER_HOME%\logs\gc.log"
rem OOM dump
set "JAVA_OPTS=%JAVA_OPTS% -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%SPIDER_HOME%"
rem debug Options
rem JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8805"
rem  ZK
rem JAVA_OPTS="$JAVA_OPTS -Djava.security.auth.login.config=/etc/security/apps_client_jaas.conf"

set "JAVA_OPTS=%JAVA_OPTS% -showversion -DSPIDER_HOME=%SPIDER_HOME% -DappName=SPIDER -cp %SPIDER_HOME%\lib\*;%SPIDER_HOME%\conf startup.SpiderMain"
echo JAVA_OPTS=%JAVA_OPTS%

:start
java %JAVA_OPTS%
goto exit
:exit
endlocal
@echo on
pause