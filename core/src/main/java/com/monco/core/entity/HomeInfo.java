package com.monco.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/3/24 16:34
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_home_info")
public class HomeInfo extends BaseEntity<Long> {

    private static final long serialVersionUID = 7142583003829254946L;

    /**
     * 房屋名称
     */
    private String name;

    /**
     * 房屋地址
     */
    private String address;

    /**
     * 城市
     */
    private String city;

    /**
     * 手机号
     */
    private String phoneCode;

    /**
     * 房屋类型
     */
    private String homeType;

    /**
     * 评分
     */
    private Double score;

    /**
     * 信用等级
     */
    private Double credit;

    /**
     * 房屋图片
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
