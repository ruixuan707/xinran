package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.CommonUtils;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.HomeInfo;
import com.monco.core.entity.RoomInfo;
import com.monco.core.entity.RoomOrder;
import com.monco.core.entity.User;
import com.monco.core.manager.UserManager;
import com.monco.core.page.HomeInfoPage;
import com.monco.core.page.PageResult;
import com.monco.core.page.RoomInfoPage;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.HomeInfoService;
import com.monco.core.service.RoomInfoService;
import com.monco.core.service.RoomOrderService;
import com.monco.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/25 16:56
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("room-info")
public class RoomInfoController {

    @Autowired
    RoomInfoService roomInfoService;

    @Autowired
    HomeInfoService homeInfoService;

    @Autowired
    UserService userService;

    @Autowired
    RoomOrderService roomOrderService;

    @PostMapping
    public ApiResult save(@RequestBody RoomInfoPage roomInfoPage) {
        RoomInfo roomInfo = new RoomInfo();
        pageToEntity(roomInfoPage, roomInfo);
        roomInfo.setRoomStatus(ConstantUtils.NUM_0);
        roomInfo.setRecommend(ConstantUtils.NUM_0);
        roomInfo.setScore(0.00);
        roomInfoService.save(roomInfo);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        RoomInfoPage roomInfoPage = new RoomInfoPage();
        RoomInfo roomInfo = roomInfoService.find(id);
        entityToPage(roomInfo, roomInfoPage);
        return ApiResult.ok(roomInfoPage);
    }

    @PutMapping
    public ApiResult update(@RequestBody RoomInfoPage roomInfoPage) {
        RoomInfo roomInfo = new RoomInfo();
        pageToEntity(roomInfoPage, roomInfo);
        roomInfoService.save(roomInfo);
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult save(@RequestParam Long id) {
        RoomInfo roomInfo = roomInfoService.find(id);
        roomInfo.setDataDelete(ConstantUtils.DELETE);
        roomInfoService.save(roomInfo);
        return ApiResult.ok();
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          RoomInfoPage roomInfoPage, OrderQuery orderQuery) {
        if (StringUtils.isNotBlank(roomInfoPage.getRoomType())) {
            roomInfoPage.setRoomTypes(roomInfoPage.getRoomType().split(","));
        }
        if (StringUtils.isNotBlank(roomInfoPage.getFacilities())) {
            roomInfoPage.setFacilitiess(roomInfoPage.getFacilities().split(","));
        }
        Page<RoomInfo> result = roomInfoService.getRoomInfoList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), roomInfoPage);
        List<RoomInfo> roomInfoList = result.getContent();
        List<RoomInfoPage> roomInfoPageList = new ArrayList<>();
        for (RoomInfo roomInfo : roomInfoList) {
            RoomInfoPage page = new RoomInfoPage();
            entityToPage(roomInfo, page);
            roomInfoPageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), roomInfoPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    @GetMapping("user-list")
    public ApiResult userList(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                              @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                              RoomInfoPage roomInfoPage, OrderQuery orderQuery) {
        User user = UserManager.get();
        if (user != null) {
            roomInfoPage.setUserId(user.getId());
        }
        if (StringUtils.isNotBlank(roomInfoPage.getRoomType())) {
            roomInfoPage.setRoomTypes(roomInfoPage.getRoomType().split(","));
        }
        if (StringUtils.isNotBlank(roomInfoPage.getFacilities())) {
            roomInfoPage.setFacilitiess(roomInfoPage.getFacilities().split(","));
        }
        Page<RoomInfo> result = roomInfoService.getRoomInfoList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), roomInfoPage);
        List<RoomInfo> roomInfoList = result.getContent();
        List<RoomInfoPage> roomInfoPageList = new ArrayList<>();
        for (RoomInfo roomInfo : roomInfoList) {
            RoomInfoPage page = new RoomInfoPage();
            entityToPage(roomInfo, page);
            roomInfoPageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), roomInfoPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    @GetMapping("user-collection")
    public ApiResult userCollection(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                                    @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                                    RoomInfoPage roomInfoPage, OrderQuery orderQuery) {
        User user = UserManager.get();
        List<Long> collectionList = new ArrayList<>();
        List<RoomInfo> roomInfos = user.getRoomCollection();
        if (CollectionUtils.isEmpty(roomInfos)) {
            return ApiResult.ok();
        }
        for (RoomInfo roomInfo : roomInfos) {
            collectionList.add(roomInfo.getId());
        }
        if (CollectionUtils.isNotEmpty(collectionList)) {
            roomInfoPage.setRoomCollectionIds(CommonUtils.list2Array(collectionList));
        }
        if (StringUtils.isNotBlank(roomInfoPage.getRoomType())) {
            roomInfoPage.setRoomTypes(roomInfoPage.getRoomType().split(","));
        }
        if (StringUtils.isNotBlank(roomInfoPage.getFacilities())) {
            roomInfoPage.setFacilitiess(roomInfoPage.getFacilities().split(","));
        }
        Page<RoomInfo> result = roomInfoService.getRoomInfoList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), roomInfoPage);
        List<RoomInfo> roomInfoList = result.getContent();
        List<RoomInfoPage> roomInfoPageList = new ArrayList<>();
        for (RoomInfo roomInfo : roomInfoList) {
            RoomInfoPage page = new RoomInfoPage();
            entityToPage(roomInfo, page);
            roomInfoPageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), roomInfoPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    public void entityToPage(RoomInfo roomInfo, RoomInfoPage roomInfoPage) {
        BeanUtils.copyProperties(roomInfo, roomInfoPage);
        roomInfoPage.setId(roomInfo.getId());
        if (roomInfo.getHomeInfo() != null) {
            roomInfoPage.setHomeInfoName(roomInfo.getHomeInfo().getName());
            roomInfoPage.setHomeInfoId(roomInfo.getHomeInfo().getId());
        }
        if (roomInfo.getUser() != null) {
            roomInfoPage.setUserName(roomInfo.getUser().getRealName());
            roomInfoPage.setUserId(roomInfo.getUser().getId());
            roomInfoPage.setUserPic(roomInfo.getUser().getPic());
            roomInfoPage.setUserPhone(roomInfo.getUser().getPhoneCode());
            roomInfoPage.setIdentityCode(roomInfo.getUser().getIdentityCode());
        }
        List<RoomOrder> roomOrderList = roomOrderService.getRoomOrderList(roomInfo.getId());
        roomInfoPage.setEvaluateNumber(roomOrderList.size());
    }

    public void pageToEntity(RoomInfoPage roomInfoPage, RoomInfo roomInfo) {
        BeanUtils.copyProperties(roomInfoPage, roomInfo);
        if (roomInfoPage.getHomeInfoId() != null) {
            roomInfo.setHomeInfo(homeInfoService.find(roomInfoPage.getHomeInfoId()));
        }
        if (UserManager.get() != null) {
            roomInfo.setUser(UserManager.get());
        }

    }
}
