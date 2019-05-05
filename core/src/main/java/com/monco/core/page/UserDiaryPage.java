package com.monco.core.page;

import lombok.Data;

/**
 * @Auther: monco
 * @Date: 2019/5/3 23:40
 * @Description:
 */
@Data
public class UserDiaryPage extends BasePage {

    private static final long serialVersionUID = -7162038021330081542L;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 内容
     */
    private String content;

    /**
     * 截图
     */
    private String pic;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 房东头像
     */
    private String userPic;
}
