package com.monco.core.page;

import com.monco.core.entity.RoomOrder;
import lombok.Data;

/**
 * @Auther: monco
 * @Date: 2019/3/25 17:11
 * @Description:
 */
@Data
public class OrderEvaluatePage extends BasePage {

    private static final long serialVersionUID = -8445394816442227664L;
    /**
     * 绑定订单
     */
    private Long roomOrderId;
    private String roomOrderName;

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
}
