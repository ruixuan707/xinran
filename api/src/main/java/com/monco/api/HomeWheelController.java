package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.HomeWheel;
import com.monco.core.page.HomeInfoPage;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.HomeWheelService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: monco
 * @Date: 2019/4/25 16:38
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("home-wheel")
public class HomeWheelController {

    @Autowired
    HomeWheelService homeWheelService;

    @PostMapping
    public ApiResult save(@RequestBody HomeWheel homeWheel) {
        homeWheelService.save(homeWheel);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult update(@RequestBody HomeWheel homeWheel) {
        homeWheelService.save(homeWheel);
        return ApiResult.ok();
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        HomeWheel homeWheel = homeWheelService.find(id);
        homeWheel.setDataDelete(ConstantUtils.DELETE);
        homeWheelService.save(homeWheel);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        HomeWheel homeWheel = homeWheelService.find(id);
        return ApiResult.ok(homeWheel);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          HomeWheel homeWheel, OrderQuery orderQuery) {
        orderQuery.setOrderField("sequence");
        orderQuery.setOrderType("ASC");
        Page<HomeWheel> result = homeWheelService.getHomeWheelList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), homeWheel);
        return ApiResult.ok(result);
    }
}
