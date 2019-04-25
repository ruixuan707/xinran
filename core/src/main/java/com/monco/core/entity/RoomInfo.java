package com.monco.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Auther: monco
 * @Date: 2019/3/24 16:43
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_room_info")
public class RoomInfo extends BaseEntity<Long> {

    private static final long serialVersionUID = -1609926212803781434L;

    /**
     * 房间号
     */
    private String roomCode;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间图片
     */
    private String pic;

    /**
     * 出租类型 1 整套 2 独立单间 3 合住房价
     */
    private Integer rentType;

    /**
     * 房间价格
     */
    private BigDecimal price;

    /**
     * 房间大小
     */
    private Double roomSize;

    /**
     * 容纳人数
     */
    private Integer holdSize;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 户型
     */
    private String roomType;

    /**
     * 床铺信息
     */
    private String bedInfo;

    /**
     * 配套设施
     */
    private String facilities;

    /**
     * 房间是否审核 0 未审核  1  已审核
     */
    private Integer roomStatus;

    /**
     * 周边情况
     */
    private String perimeter;

    /**
     * 交通情况
     */
    private String traffic;

    /**
     * 绑定房屋
     */
    private HomeInfo homeInfo;

    /**
     * 是否推荐展示 0 否 1 是
     */
    private Integer recommend;

    @OneToOne
    @JoinColumn(name = "home_info_id", referencedColumnName = "id")
    public HomeInfo getHomeInfo() {
        return homeInfo;
    }

    /**
     * 房东
     */
    public User user;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }
}
