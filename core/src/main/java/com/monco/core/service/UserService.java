package com.monco.core.service;

import com.monco.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:17
 * @Description:
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * 分页查询用户
     *
     * @param pageable
     * @param user
     * @return
     */
    Page<User> getUserList(Pageable pageable, User user);

    /**
     * 登录操作
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);


    /**
     * 查找是否含有用户注册
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
