package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库
        //1.定义SQL
        String sql = "SELECT * FROM user";
        //2.调用query方法
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        //没有查询到返回null，没有则返回user对象
        try {
            //1.定义sql
            String sql = "select * from user where username=? and password=?";
            //2.调用queryForObject方法
            User user = null;
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(User user) {
        //1.定义sql
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        //2.执行sql
        jdbcTemplate.update(sql,
                user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());

    }

    @Override
    public void delete(int id) {
        //1.定义sql
        String sql = "delete from user where id = ?";
        //2.执行sql
        jdbcTemplate.update(sql,id);
    }

    @Override
    public User findById(int id) {
        //1.定义sql
        String sql = "select * from user where id = ?";
        //2.执行sql
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        //1.定义sql
        String sql = "update user set name = ?,gender = ?,age = ?,address = ?,qq = ?,email = ? where id = ?";
        //2.执行sql
        jdbcTemplate.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    /**
     * 查询总记录数
     * @return
     * @param condition
     */
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select count(*) from user where 1=1 ";
        StringBuffer sb = new StringBuffer(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<>();
        for (String key : keySet) {
            //排除分页条件参数
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;//结束当前的循环，继续下一个的循环
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value!=null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);
        //1.定义sql
        //String sql = "select count(*) from user";
        //return jdbcTemplate.queryForObject(sql,Integer.class);

        return jdbcTemplate.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    /**
     * 分页查询每页的记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        //1.定义sql
        String sql = " select * from user where 1 =1";

        StringBuffer sb = new StringBuffer(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<>();
        for (String key : keySet) {
            //排除分页条件参数
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;//结束当前的循环，继续下一个的循环
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value!=null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        //添加分页查询
        sb.append(" limit ? , ?");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);

        sql=sb.toString();

        System.out.println(sql);
        System.out.println(params);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }


}
