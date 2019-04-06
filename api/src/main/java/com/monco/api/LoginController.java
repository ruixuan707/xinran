package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.core.entity.User;
import com.monco.core.page.UserPage;
import com.monco.core.service.UserService;
import com.monco.shiro.JwtComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: monco
 * @Date: 2019/4/1 14:56
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping
    public ApiResult login(@RequestBody UserPage userPage) {
        if (StringUtils.isNotBlank(userPage.getUsername()) && StringUtils.isNotBlank(userPage.getPassword())) {
            User user = userService.login(userPage.getUsername(), userPage.getPassword());
            if (user == null) {
                return ApiResult.error("账号或密码不正确");
            }
            String token = JwtComponent.sign(user.getUsername(), user.getVersion(), user.getPassword());
            userPage.setToken(token);
            BeanUtils.copyProperties(user, userPage);
            return ApiResult.ok(userPage);
        } else {
            return ApiResult.error("账号或密码不能为空");
        }
    }
}
