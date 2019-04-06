package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.User;
import com.monco.core.page.PageResult;
import com.monco.core.page.UserPage;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.RoomInfoService;
import com.monco.core.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:22
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoomInfoService roomInfoService;

    @PostMapping("register")
    public ApiResult save(@RequestBody UserPage userPage) {
        User user = new User();
        pageToEntity(userPage, user);
        User userValidate = userService.getUserByUsername(userPage.getUsername());
        if (userValidate == null) {
            userService.save(user);
            return ApiResult.ok("注册成功");
        } else {
            return ApiResult.error("用户已注册！");
        }
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        UserPage userPage = new UserPage();
        User user = userService.find(id);
        entityToPage(user, userPage);
        return ApiResult.ok(userPage);
    }

    @PutMapping
    public ApiResult update(@RequestBody UserPage userPage) {
        User user = new User();
        pageToEntity(userPage, user);
        userService.save(user);
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        User user = userService.find(id);
        user.setDataDelete(ConstantUtils.DELETE);
        userService.save(user);
        return ApiResult.ok();
    }


    @GetMapping("username")
    public ApiResult getUserByUsername(@RequestParam(required = false) String username) {
        if (StringUtils.isBlank(username)) {
            return ApiResult.error("您需要查询的条件为空");
        } else {

            return ApiResult.ok();
        }
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          User user, OrderQuery orderQuery) {
        Page<User> result = userService.getUserList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), user);
        List<User> userList = result.getContent();
        List<UserPage> userPageList = new ArrayList<>();
        for (User entity : userList) {
            UserPage userPage = new UserPage();
            entityToPage(entity, userPage);
            userPageList.add(userPage);
        }
        PageResult pageResult = new PageResult(result.getPageable(), userPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    public void entityToPage(User user, UserPage userPage) {
        BeanUtils.copyProperties(user, userPage);
        userPage.setId(user.getId());
    }

    public void pageToEntity(UserPage userPage, User user) {
        BeanUtils.copyProperties(userPage, user);
        if (ArrayUtils.isNotEmpty(userPage.getRoomCollectionIds())) {
            user.setRoomCollection(roomInfoService.findByIds(userPage.getRoomCollectionIds()));
        }
    }
}
