package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);


    /**
     * 保存User
     * @param user
     */
    void addUser(User user);

    /**
     * 根据id删除
     * @param id
     */
    void deleteUser(String id);

    /**
     * 根据id查询
     * @param id
     */
    User findById(String id);

    /**
     * 修改
     * @param user
     */
    void updateUser(User user);

    /**
     * 批量删除
     * @param ids
     */
    void deleteUsers(String[] ids);

    /**
     * 分页+条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
