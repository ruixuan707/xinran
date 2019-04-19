package com.monco.core.page;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/25 18:31
 * @Description:
 */
@Data
public class RoomOrderPage extends BasePage {

    private static final long serialVersionUID = 4376124583902808578L;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 绑定房间
     */
    private Long roomInfoId;
    private String roomInfoName;

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
    private String userName;
    private Long userId;

    /**
     * 入住人
     */
    private List<String> userListName;
    private Long[] userListId;
}
