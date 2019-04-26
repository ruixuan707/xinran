package com.monco.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 推荐
 */
@Entity
@Getter
@Setter
@Table(name = "sys_recommend")
public class Recommend extends BaseEntity<Long> {

    private static final long serialVersionUID = -3473271030079571189L;

    /**
     * 图片
     */
    private String pic;

    /**
     * 类型 0 城市 1 房间
     */
    private Integer type;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 房间id
     */
    private Long roomInfoId;
}
