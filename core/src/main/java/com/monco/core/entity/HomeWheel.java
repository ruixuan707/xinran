package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/4/25 16:24
 * @Description:
 */
@Entity
@Getter
@Setter
@Table(name = "home_manage")
public class HomeWheel extends BaseEntity<Long> {

    private static final long serialVersionUID = -2497219736956658810L;

    /**
     * 首页轮播
     */
    private String pic;

    /**
     * 是否展示
     */
    private String display;

    /**
     * 排序
     */
    private Integer sequence;

}
