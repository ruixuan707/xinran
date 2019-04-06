package com.monco.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:07
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_order_evaluate")
public class OrderEvaluate extends BaseEntity<Long> {

    private static final long serialVersionUID = 4167575383332401787L;

    /**
     * 绑定订单
     */
    private RoomOrder roomOrder;

    /**
     * 评价内容
     */
    private String evaluate;

    /**
     * 上传截图
     */
    private String pic;

    /**
     * 房东回复
     */
    private String reply;

    @OneToOne
    @JoinColumn(name = "room_order_id", referencedColumnName = "id")
    public RoomOrder getRoomOrder() {
        return roomOrder;
    }
}
