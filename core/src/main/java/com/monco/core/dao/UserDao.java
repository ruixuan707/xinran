package com.monco.core.dao;

import com.monco.core.entity.User;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:11
 * @Description:
 */
public interface UserDao extends BaseDao<User, Long> {

    User findUserByUsernameAndPasswordAndDataDelete(String username, String password, Integer dataDelete);

    /**
     * 按照用户名查找用户
     *
     * @param username
     * @param dataDelete
     * @return
     */
    User findUserByUsernameAndDataDelete(String username, Integer dataDelete);


    List<User> findUsersByUsername(String username);
}
