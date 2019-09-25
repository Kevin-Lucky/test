package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;

public interface UserDao {

    User find(String username,String password);


    List<User> findUser(String username);
}
