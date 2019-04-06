package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.core.entity.RoomOrder;
import com.monco.core.entity.User;
import com.monco.core.page.PageResult;
import com.monco.core.page.RoomInfoPage;
import com.monco.core.page.RoomOrderPage;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.RoomInfoService;
import com.monco.core.service.RoomOrderService;
import com.monco.core.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/25 18:14
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("room-order")
public class RoomOrderController {

    @Autowired
    RoomOrderService roomOrderService;

    @Autowired
    RoomInfoService roomInfoService;

    @Autowired
    UserService userService;

    @PostMapping
    public ApiResult save(@RequestBody RoomOrderPage roomOrderPage) {
        RoomOrder roomOrder = new RoomOrder();
        pageToEntity(roomOrderPage, roomOrder);
        roomOrderService.save(roomOrder);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult update(@RequestBody RoomOrderPage roomOrderPage) {
        RoomOrder roomOrder = new RoomOrder();
        pageToEntity(roomOrderPage, roomOrder);
        roomOrderService.save(roomOrder);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        RoomOrderPage roomOrderPage = new RoomOrderPage();
        RoomOrder roomOrder = roomOrderService.find(id);
        entityToPage(roomOrder, roomOrderPage);
        return ApiResult.ok(roomOrderPage);
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        RoomOrder roomOrder = roomOrderService.find(id);
        roomOrder.setDataDelete(1);
        roomOrderService.save(roomOrder);
        return ApiResult.ok();
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          RoomOrderPage roomOrderPage, OrderQuery orderQuery) {
        Page<RoomOrder> result = roomOrderService.getRoomOrderList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), roomOrderPage);
        List<RoomOrder> roomOrderList = result.getContent();
        List<RoomOrderPage> roomOrderPageList = new ArrayList<>();
        for (RoomOrder roomOrder : roomOrderList) {
            RoomOrderPage page = new RoomOrderPage();
            entityToPage(roomOrder, page);
            roomOrderPageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), roomOrderPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }


    /**
     * 页面转实体
     *
     * @param roomOrderPage
     * @param roomOrder
     */
    public void pageToEntity(RoomOrderPage roomOrderPage, RoomOrder roomOrder) {
        BeanUtils.copyProperties(roomOrderPage, roomOrder);
        if (roomOrderPage.getRoomInfoId() != null) {
            roomOrder.setRoomInfo(roomInfoService.find(roomOrderPage.getRoomInfoId()));
        }
        if (roomOrderPage.getUserId() != null) {
            roomOrder.setUser(userService.find(roomOrderPage.getUserId()));
        }
        if (ArrayUtils.isNotEmpty(roomOrderPage.getUserListId())) {
            List<User> userList = userService.findByIds(roomOrderPage.getUserListId());
            roomOrder.setUserList(userList);
        }
    }

    /**
     * 实体转页面
     *
     * @param roomOrder
     * @param roomOrderPage
     */
    public void entityToPage(RoomOrder roomOrder, RoomOrderPage roomOrderPage) {
        BeanUtils.copyProperties(roomOrder, roomOrderPage);
        roomOrderPage.setId(roomOrder.getId());
        if (roomOrder.getRoomInfo() != null) {
            roomOrderPage.setRoomInfoId(roomOrder.getRoomInfo().getId());
            roomOrderPage.setRoomInfoName(roomOrder.getRoomInfo().getRoomName());
        }
        if (CollectionUtils.isNotEmpty(roomOrder.getUserList())) {
            List<String> userList = new ArrayList<>();
            for (User user : roomOrder.getUserList()) {
                userList.add(user.getNickName());
            }
            roomOrderPage.setUserListName(userList);
        }
        if (roomOrder.getUser() != null) {
            roomOrderPage.setUserId(roomOrder.getUser().getId());
            roomOrderPage.setUserName(roomOrder.getUser().getNickName());
        }
    }

}
