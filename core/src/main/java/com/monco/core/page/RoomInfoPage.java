package com.monco.core.page;

import com.monco.core.entity.HomeInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: monco
 * @Date: 2019/3/25 16:57
 * @Description:
 */
@Data
public class RoomInfoPage extends BasePage {

    private static final long serialVersionUID = 3913043151445453553L;

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
     * 绑定房间
     */
    private String homeInfoName;
    private Long homeInfoId;

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
     * 绑定房东
     */
    private Long userId;
    private String userName;
    private String nickName;

    /**
     * 房东头像
     */
    private String userPic;

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
     * 查询条件 价格
     */

    private BigDecimal topPrice;
    private BigDecimal bottomPrice;
}
