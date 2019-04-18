package com.monco.core.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: monco
 * @Date: 2019/4/11 22:05
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_user_diary")
public class UserDiary extends BaseEntity<Long> {

    private static final long serialVersionUID = 1395366700398099422L;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 内容
     */
    private String content;

    /**
     * 截图
     */
    private String pic;

    /**
     * 绑定用户
     */
    private User user;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }
}
