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
