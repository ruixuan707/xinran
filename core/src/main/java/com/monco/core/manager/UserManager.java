package com.monco.core.manager;


import com.monco.core.entity.User;

/**
 * @Auther: monco
 * @Date: 2019/3/23 16:24
 * @Description:
 */
public class UserManager {

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<User>();

    /**
     * 放到当前线程中
     *
     * @param user
     *         用户
     */
    public static void set(User user) {
        LOCAL.set(user);
    }

    /**
     * 从当前线程中获取
     *
     * @return Employee 用户
     */
    public static User get() {
        return LOCAL.get();
    }


    /**
     * 从当前线程删除
     */
    public static void remove() {
        LOCAL.remove();
    }
}
