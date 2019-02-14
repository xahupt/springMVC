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
