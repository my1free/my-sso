# my-sso
sso project


## 支持功能
* 用户登录
* 用户注册
* 用户注销
* AB认证


## 接入方法

1. 引入依赖
pom.xml中添加
```xml
<dependency>
    <groupId>com.michealyang</groupId>
    <artifactId>my-sso-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
2. 增加Filter
web.xml中添加
```xml
<filter>
    <filter-name>ssoFilter</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    <init-param>
        <param-name>targetFilterLifecycle</param-name>
        <param-value>true</param-value>
    </init-param>
</filter>

<filter-mapping>
    <filter-name>ssoFilter </filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
3. 配置参数
applicationContext.xml中添加
```xml
<bean id="ssoFilter" class="com.michealyang.sso.client.filter.SsoFilter">
    <property name="ssoLogin" value="${sso.login}"></property>
    <property name="ssoAuth" value="${sso.auth}"></property>
    <property name="ssoLogout" value="${sso.logout}"></property>
    <property name="host" value="${my.host}"></property>
</bean>
```
其中
> ${sso.login}是sso登录页面地址

> ${sso.auth}是用户认证地址

> ${sso.logout}是注销地址

> ${my.host}是本机域名，用于跳转回来用

## 高级设置
### 无需登录验证uri设置
配置SsoFilter中的成员变量uriIgnore，所配置的uri都不会进行登录验证。
多个uri通过逗号分隔
```xml
<bean id="ssoFilter" class="com.michealyang.sso.client.filter.SsoFilter">
    <property name="uriIgnore" value="
    /appStore/,
    /webappStore/
    "></property>
</bean>
```

## 部署流程
1. 将`sso-server.war`部署到servlet容器中
2. 创建数据库和数据表
```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL DEFAULT '',
  `passwd` varchar(256) NOT NULL DEFAULT '',
  `phone_num` varchar(20) NOT NULL DEFAULT '',
  `salt` varchar(45) NOT NULL DEFAULT '',
  `valid` tinyint(3) NOT NULL DEFAULT '1',
  `ctime` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uq_username` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
```

