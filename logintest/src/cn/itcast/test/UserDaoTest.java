package cn.itcast.test;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void testLogin(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setUserpassword("123");

        UserDao dao = new UserDao();
        User login = dao.login(user);

        System.out.println(login);
    }
}
