package cn.itcast.service;

import cn.itcast.domain.User;

import java.util.List;

public interface UserService {
    User login(User user);


    List<User> findUserName(String username);
}
