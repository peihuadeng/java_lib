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



#部分重要的url
首页页面：http://dev:8080/web_demo/index.jsp
用户列表（需要登录）：http://dev:8080/web_demo/user
登出页面：http://dev:8080/web_demo/sys/logout
druid统计页面：http://dev:8080/web_demo/druid
websocket测试页面（连接websocket需要登录）：http://dev:8080/web_demo/html/websocket/websocket.html
stomp测试页面：http://dev:8080/web_demo/websocket/stomp

