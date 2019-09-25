package cn.itcast.dao;

import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDao {

    //声明JDBCTemplate对象共用
    private JdbcTemplate template =new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     *
     * @param user
     * @return
     */
    public User login(User user){

        try{
            //1.编写sql
            String sql = "select * from user where username = ? and password = ?";
            //2.调用query方法
            User user1 = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    user.getUsername(), user.getUserpassword());
            return user1;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
