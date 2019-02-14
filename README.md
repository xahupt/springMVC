# springMVC
在IDEA中创建SpringMVC web Application项目
0.导入SpringMVC和Spring的jar包

1.   系统自动生成三个XML配置文件

分别是Spring的ApplicationContext.xml配置文件、SpringMVC的dispatcher-servlet.xml配置文件和tomcat的web.xml配置文件
2.  对各配置文件进行配置
2.1  首先是web.xml文件

<!--在web.xml中定义contextConfigLocation参数，Spring会使用这个参数去加载所有逗号分隔的xml文件，如果没有这个参数，
Spring默认加载web-inf/applicationContext.xml文件。-->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/applicationContext.xml</param-value>
</context-param>
<!--配置监听器-->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
<!--配置SpringMVC的前端控制器DispatcherServlet-->
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--获取前端控制器的配置文件-->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/dispatcher-servlet.xml</param-value>
    </init-param>
    <!--表示在Servlet启动时启动前端控制器-->
    <load-on-startup>1</load-on-startup>
</servlet>
<!--配置前端控制器的拦截规则，按照规则交由Servlet处理-->
<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>*.action</url-pattern>
</servlet-mapping>
2.2  配置dispatcher-servlet.xml文件

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/mvc
                           http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>-->
    <!--<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/>-->
    <!--配置视图处理器的前后缀-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="viewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    <!--配置包扫描，处理注解-->
    <context:component-scan base-package="com.percy"></context:component-scan>
    <!--配置MVC的注解驱动-->
    <mvc:annotation-driven/>
    <!--<mvc:default-servlet-handler/>-->

</beans>
2.3 配置ApplicationContext.xml文件

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">
       <!--注解扫描-->
       <context:component-scan base-package="com.percy.dao"></context:component-scan>

</beans>

3.开发持久层
本例中使用jdbcTemplate进行数据库操作，主要实现用户登录：

数据库DAO接口
public interface adminUserDao {
    public boolean CheckUser(String userName,String passWord);
}
数据库DAO接口实现
package com.percy.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author percy
 * @create 2019-02-12  下午10:42
 * @descreption:
 **/
@Service
@Component()
public class AdminUSerDaoImpl implements adminUserDao {
    private static DriverManagerDataSource driveManagerSource;
    private static JdbcTemplate jdbcTemplate;
    private static String sql;
    static {
        driveManagerSource = new DriverManagerDataSource();
        driveManagerSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driveManagerSource.setUrl("jdbc:mysql:///supermarket?useUnicode=true&useSSL=false&characterEncoding=utf8");//数据库url中？问号之后的部分是为了保证在IDEA中插入中文数据
        driveManagerSource.setUsername("root");
        driveManagerSource.setPassword("china0420");
        jdbcTemplate = new JdbcTemplate(driveManagerSource);

    }
    @Override
    public boolean CheckUser(String userName,String passWord) {
        sql = "Select * From USER Where name=?";
        List<user> users = jdbcTemplate.query(sql,new MyRowMapper(),userName);
        if (users.size()==0){
            return false;
        }else {
            System.out.println(users);
            return true;
        }
    }
}
数据库Model实现
package com.percy.dao;

/**
 * @author percy
 * @create 2019-02-04  上午10:30
 * @descreption:
 **/
public class user{
    private String id;
    private String name;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
数据库jdbcTemplate操作的RowMapper（）实现
package com.percy.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author percy
 * @create 2019-02-04  上午10:53
 * @descreption:
 **/
public class MyRowMapper implements RowMapper<user> {


    @Override
    public user mapRow(ResultSet resultSet, int i) throws SQLException {
        user users = new user();
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        users.setId(id);
        users.setName(name);
        users.setPassword(password);
        return users;
    }
}
4.控制器的开发

5.页面的开发

index.jsp

msg.jsp

6.运行结果


