分布式事务管理
1.两数据库分别有两个表：
test：user
test_2： student

2.配置atomikos，配置分布式事务管理，详情请看spring-context.xml配置

3.使用@Transactional配置事务

4.调用CrossController.cross方法，即访问http://172.31.0.3:8080/jta_demo/cross
会发现在两个不同的数据库上的提交同时回滚了，实现了分布式数据库事务管理
