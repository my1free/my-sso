# my-sso
sso project
##支持功能
* 用户登录
* 用户注册
* 用户注销
* AB认证


##接入方法
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
>${sso.login}是sso登录页面地址

>${sso.auth}是用户认证地址

>${sso.logout}是注销地址

>${my.host}是本机域名，用于跳转回来用
