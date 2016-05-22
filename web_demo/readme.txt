#单点登录配置
1.移除以下文件：
/web_demo/src/main/resources/spring-context-shiro.xml
/web_demo/src/main/webapp/WEB-INF/web.xml

2.启用以下文件并改名：
/web_demo/src/main/resources/spring-context-shiro-cas.xml_cas -> spring-context-shiro-cas.xml
/web_demo/src/main/webapp/WEB-INF/web.xml_cas -> web.xml

3.需与java_lib/cas_demo/cas-server-webapp工程（单点登录服务器端）配合使用