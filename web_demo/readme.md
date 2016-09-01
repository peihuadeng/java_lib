#web_demo简介
后端框架：
spring
mybatis（使用@Mybatis注解来定义mapper） + 自定义mybatis分页插件
ehcache + ehcache页面缓存（未启用）
shiro安全框架
spring websocket + stomp
druid统计分析
cas单点登录（未启用）
sitemash页面装饰

前端框架：
bootstrap
jquery
echarts百度报表
My97DatePicker日期组件
sockjs + stomp


#单点登录配置
1.移除以下文件：
/web_demo/src/main/resources/spring-context-shiro.xml
/web_demo/src/main/webapp/WEB-INF/web.xml

2.启用以下文件并改名：
/web_demo/src/main/resources/spring-context-shiro-cas.xml_cas -> spring-context-shiro-cas.xml
/web_demo/src/main/webapp/WEB-INF/web.xml_cas -> web.xml

3.需与java_lib/cas_demo/cas-server-webapp工程（单点登录服务器端）配合使用


#数据库
1.创建名为web_demo的数据库，编码:utf8
2.修改jdbc.properties配置文件中的相关参数
3.系统初始化数据中，账号密码为:admin/123456, user/123456

#部署配置说明
1.将conf下所有文件拷贝到${catalina.home}/webapps_conf/web_demo/下
2.修正拷贝的配置文件中的参数
3.把war包放置在${catalina.home}/webapps
4.启动tomcat
5.完成部署

#遗留问题
1.ehcache持久化：在杀进程后启动tomcat，发现持久化缓存被清空了


