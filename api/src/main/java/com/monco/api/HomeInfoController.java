package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.core.entity.HomeInfo;
import com.monco.core.entity.User;
import com.monco.core.page.HomeInfoPage;
import com.monco.core.page.PageResult;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.HomeInfoService;
import com.monco.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:31
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("home-info")
public class HomeInfoController {

    @Autowired
    HomeInfoService homeInfoService;

    @Autowired
    UserService userService;

    @PostMapping
    public ApiResult save(@RequestBody HomeInfoPage homeInfoPage) {
        HomeInfo homeInfo = new HomeInfo();
        pageToEntity(homeInfoPage, homeInfo);
        homeInfoService.save(homeInfo);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        HomeInfo homeInfo = homeInfoService.find(id);
        HomeInfoPage homeInfoPage = new HomeInfoPage();
        entityToPage(homeInfo, homeInfoPage);
        return ApiResult.ok(homeInfoPage);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          HomeInfoPage homeInfoPage, OrderQuery orderQuery) {
        Page<HomeInfo> result = homeInfoService.getHomeInfoList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), homeInfoPage);
        List<HomeInfo> homeInfoList = result.getContent();
        List<HomeInfoPage> homeInfoPageList = new ArrayList<>();
        for (HomeInfo homeInfo : homeInfoList) {
            HomeInfoPage page = new HomeInfoPage();
            entityToPage(homeInfo, page);
            homeInfoPageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), homeInfoPageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }


    public void entityToPage(HomeInfo homeInfo, HomeInfoPage homeInfoPage) {
        BeanUtils.copyProperties(homeInfo, homeInfoPage);
        homeInfoPage.setId(homeInfo.getId());
        if (homeInfo.getUser() != null) {
            homeInfoPage.setUserId(homeInfo.getUser().getId());
            homeInfoPage.setUserName(homeInfo.getUser().getRealName());
            homeInfoPage.setNickName(homeInfo.getUser().getNickName());
        }
    }

    public void pageToEntity(HomeInfoPage homeInfoPage, HomeInfo homeInfo) {
        BeanUtils.copyProperties(homeInfoPage, homeInfo);
        if (homeInfoPage.getUserId() != null) {
            homeInfo.setUser(userService.find(homeInfoPage.getUserId()));
        }
    }
}
