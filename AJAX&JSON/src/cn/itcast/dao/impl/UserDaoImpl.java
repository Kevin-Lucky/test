package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User find(String username,String password) {
        //没有查询到返回null，没有则返回user对象
        try {
            //1.定义sql
            String sql = "select * from users where username=? AND password=?";
            //2.调用queryForObject方法
            User user = null;
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findUser(String username) {
        try{
            //1.定义sql
            String sql ="select * from users where username=?";
            //2.调用方法
            List<User> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return query;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
