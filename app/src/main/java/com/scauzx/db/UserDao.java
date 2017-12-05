package com.scauzx.db;

import com.scauzx.models.User;

import java.util.List;

/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public interface UserDao {

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    long addUser(User user);


    /**
     * 删除用户信息
     * @param username
     * @return
     */
    boolean deleteUser(String username);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 根据用户名查用户信息
     * @param username
     * @return
     */
    User queryUserByName(String username);

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> getUsers();

    /**
     * 根据用户信息判断用户是否存在
     * @param user
     * @return
     */
    boolean isExist(User user);

    /**
     * 根据用户名判断用户是否存在
     * @param username
     * @return
     */
    boolean isExist(String username);

    /**
     * 删除所有用户信息
     * @return
     */
    boolean deleteAllUser();
}
