package com.monco.core.page;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecommendPage extends BasePage {

    private static final long serialVersionUID = 4560254340970232089L;

    /**
     * 图片
     */
    private String pic;

    /**
     * 类型 0 城市 1 房间
     */
    private String type;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 房间数量
     */
    private Integer roomCount;

    /**
     * 房间id
     */
    private Long roomInfoId;

    /**
     * 房东名
     */
    private String roomUserName;

    /**
     * 房东头像
     */
    private String userPic;

    /**
     * 房间价格
     */
    private BigDecimal price;
}
