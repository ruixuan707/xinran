package com.monco.core.page;

import com.monco.core.entity.BaseEntity;
import com.monco.core.entity.RoomInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/31 21:16
 * @Description:
 */
@Data
public class UserPage extends BaseEntity<Long> {

    private static final long serialVersionUID = -8143414683338876955L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String identityCode;

    /**
     * 手机号
     */
    private String phoneCode;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生地点
     */
    private String address;

    /**
     * 照片
     */
    private String pic;

    /**
     * 微信号
     */
    private String weChatCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型 0 普通用户 1 商家  2 管理员
     */
    private Integer userType;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 血型
     */
    private String blood;

    /**
     * 教育程度
     */
    private String education;

    /**
     * 工作
     */
    private String job;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 收藏房间
     */
    private Long[] roomCollectionIds;

    /**
     * 返回前台token
     */
    private String token;
}
