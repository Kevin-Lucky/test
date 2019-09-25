package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao dao=new UserDaoImpl();

    @Override
    public User login(User user) {
        return dao.find(user.getUsername(),user.getPassword());
    }

    @Override
    public List<User> findUserName(String username) {
        return dao.findUser(username);
    }


}
