#restful_demo说明
1.使用resteasy框架
2.springMVC+resteasy整合
3.ContactsTest测试类，通过java+rpc方式远程调用方法

#关键url
1.页面：http://localhost:8080/restful_demo/contacts
2.表单操作：post、put - http://localhost:8080/restful_demo/contacts
3.获取列表：get(返回json或xml) - http://localhost:8080/restful_demo/contacts/data
4.获取单个：get(返回json或xml) - http://localhost:8080/restful_demo/contacts/data/{lastName}
5.创建/更新操作：post/put(返回json或xml+查询地址) - http://localhost:8080/restful_demo/contacts/data