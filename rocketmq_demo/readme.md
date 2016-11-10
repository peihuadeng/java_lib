#rocketmq_demo
* rocketmq生产者抽象化
* rocketmq生产者demo
* rocketmq消费者demo

#RocketMQ官方安装教程
[quick-start](https://github.com/alibaba/RocketMQ/wiki/quick-start)

#相关命令
##启动name server
bash mqnamesrv

##启动broker
bash mqbroker -n localhost:9876

##创建主题
sh mqadmin updateTopic -n localhost:9876 -c DefaultCluster -t topicList -w 16 -r 16

##显示主题
sh mqadmin topicList -n localhost:9876
sh mqadmin topicList -n localhost:9876 -c DefaultCluster

##删除主题
sh mqadmin deleteTopic -c DefaultCluster -n localhost:9876 -t topicList
