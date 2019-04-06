package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.core.entity.OrderEvaluate;
import com.monco.core.page.HomeInfoPage;
import com.monco.core.page.OrderEvaluatePage;
import com.monco.core.page.PageResult;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.OrderEvaluateService;
import com.monco.core.service.RoomOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/25 17:13
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("order-evaluate")
public class OrderEvaluateController {

    @Autowired
    OrderEvaluateService orderEvaluateService;

    @Autowired
    RoomOrderService roomOrderService;

    @PostMapping
    public ApiResult save(@RequestBody OrderEvaluatePage orderEvaluatePage) {
        OrderEvaluate orderEvaluate = new OrderEvaluate();
        pageToEntity(orderEvaluatePage, orderEvaluate);
        orderEvaluateService.save(orderEvaluate);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        OrderEvaluatePage orderEvaluatePage = new OrderEvaluatePage();
        OrderEvaluate orderEvaluate = orderEvaluateService.find(id);
        entityToPage(orderEvaluate, orderEvaluatePage);
        return ApiResult.ok(orderEvaluatePage);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          OrderEvaluatePage orderEvaluatePage, OrderQuery orderQuery) {
        Page<OrderEvaluate> result = orderEvaluateService.getOrderEvaluateList(OrderQuery.getQuery(orderQuery, currentPage, pageSize), orderEvaluatePage);
        List<OrderEvaluate> orderEvaluateList = result.getContent();
        List<OrderEvaluatePage> orderEvaluatePageList = new ArrayList<>();
        for (OrderEvaluate orderEvaluate : orderEvaluateList) {
            OrderEvaluatePage page = new OrderEvaluatePage();
            entityToPage(orderEvaluate, page);
            orderEvaluatePageList.add(page);
        }
        PageResult pageResult = new PageResult(result.getPageable(), orderEvaluatePageList, result.getTotalElements());
        return ApiResult.ok(pageResult);
    }

    public void entityToPage(OrderEvaluate orderEvaluate, OrderEvaluatePage orderEvaluatePage) {
        BeanUtils.copyProperties(orderEvaluate, orderEvaluatePage);
        orderEvaluatePage.setId(orderEvaluate.getId());
        if (orderEvaluate.getRoomOrder() != null) {
            orderEvaluatePage.setRoomOrderId(orderEvaluate.getRoomOrder().getId());
            orderEvaluatePage.setRoomOrderName(orderEvaluate.getRoomOrder().getOrderCode());
        }
    }

    public void pageToEntity(OrderEvaluatePage orderEvaluatePage, OrderEvaluate orderEvaluate) {
        BeanUtils.copyProperties(orderEvaluatePage, orderEvaluate);
        if (orderEvaluatePage.getRoomOrderId() != null) {
            orderEvaluate.setRoomOrder(roomOrderService.find(orderEvaluatePage.getRoomOrderId()));
        }
    }
}
