package com.monco.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Auther: monco
 * @Date: 2019/3/24 16:51
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_room_order")
public class RoomOrder extends BaseEntity<Long> {

    private static final long serialVersionUID = -6789317578669295565L;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 订单状态 0 未确认 1 进行中 2 未点评 3 完成
     */
    private Integer orderStatus;

    /**
     * 绑定房间
     */
    private RoomInfo roomInfo;

    /**
     * 开始入住时间
     */
    private Date stayDate;

    /**
     * 入住天数
     */
    private Integer stayDays;

    /**
     * 离开时间
     */
    private Date leaveDate;

    /**
     * 总花费
     */
    private BigDecimal cost;

    /**
     * 付款状态
     */
    private Integer costStatus;

    /**
     * 订单评分
     */
    private Double score;

    /**
     * 订单人
     */
    private User user;

    /**
     * 入住人
     */
    private List<User> userList;

    @OneToOne
    @JoinColumn(name = "room_info_id", referencedColumnName = "id")
    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_order_user", joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    public List<User> getUserList() {
        return userList;
    }
}
