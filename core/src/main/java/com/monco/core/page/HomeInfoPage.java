package com.monco.core.page;

import com.monco.core.entity.User;
import lombok.Data;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:33
 * @Description:
 */
@Data
public class HomeInfoPage extends BasePage {

    private static final long serialVersionUID = 4147942142280730841L;

    /**
     * 房屋名称
     */
    private String name;

    /**
     * 房屋地址
     */
    private String address;

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
    private Long userId;
    private String userName;
    private String nickName;
}
