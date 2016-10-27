#process_demo简介
* 以自定义命令方式启动服务进程

##启动命令
* java -jar process_demo.jar start

##停止命令
* java -jar process_demo.jar stop

##重启命令
* java -jar process_demo.jar restart

##原理
###执行启动命令
1. 获取pid文件内记录的进程号pid，并执行jps -l命令判断进程是否正在运行。如果检测到进程正在运行则提示并退出，否则执行步骤2
2. 执行启动目标java进程命令：${JAVA_HOME}/bin/java -classpath ${classPath} ${className} ${serviceClassName}
3. 获取目标java进程输出流的输出内容，判断是否启动成功，并提示。如果启动成功，则从输出内容中获取进程号pid并执行步骤4
4. 保存进程号pid到pid文件内。

###执行停止命令
1. 获取pid文件内记录的进程号pid，并执行jps -l命令判断进程是否正在运行。如果检测到进程已经停止则移除pid文件并退出，否则执行步骤2
2. 依据操作系统类型，执行停止指定进程命令。其中windows目前只能强制杀进程。windows：taskkill /f /pid ${pid}。linux：kill -15 ${pid}。
3. 持续执行jps -l命令检测进程是否已停止。如果是，则提示并移除pid文件。否则执行步骤4
4. 依据操作系统类型，执行强制停止指定进程命令并移除pid文件。windows：taskkill /f /pid ${pid}。linux：kill -9 ${pid}。