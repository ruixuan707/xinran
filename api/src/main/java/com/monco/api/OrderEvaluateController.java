package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.CommonUtils;
import com.monco.core.entity.OrderEvaluate;
import com.monco.core.entity.RoomOrder;
import com.monco.core.page.HomeInfoPage;
import com.monco.core.page.OrderEvaluatePage;
import com.monco.core.page.PageResult;
import com.monco.core.query.OrderQuery;
import com.monco.core.service.OrderEvaluateService;
import com.monco.core.service.RoomOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    @PutMapping
    public ApiResult update(@RequestBody OrderEvaluatePage orderEvaluatePage) {
        OrderEvaluate orderEvaluate = orderEvaluateService.find(orderEvaluatePage.getId());
        if (StringUtils.isNotBlank(orderEvaluatePage.getReply())) {
            orderEvaluate.setReply(orderEvaluatePage.getReply());
            orderEvaluateService.save(orderEvaluate);
        }
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
        List<RoomOrder> roomOrderList = roomOrderService.getRoomOrderList(orderEvaluatePage.getRoomInfoId());
        List<Long> roomOrderIdList = new ArrayList<>();
        for (RoomOrder roomOrder : roomOrderList) {
            roomOrderIdList.add(roomOrder.getId());
        }
        if (CollectionUtils.isNotEmpty(roomOrderIdList)) {
            orderEvaluatePage.setRoomOrderIds(CommonUtils.list2Array(roomOrderIdList));
        }
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


    @GetMapping("home")
    public ApiResult home(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage,
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


    @GetMapping("all")
    public ApiResult all(@RequestParam Long id) {
        RoomOrder roomOrder = roomOrderService.find(id);
        OrderEvaluate orderEvaluate = new OrderEvaluate();
        orderEvaluate.setRoomOrder(roomOrder);
        Example<OrderEvaluate> roomOrderExample = Example.of(orderEvaluate);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<OrderEvaluate> orderEvaluateList = orderEvaluateService.findAll(roomOrderExample, sort);
        List<OrderEvaluatePage> orderEvaluatePageList = new ArrayList<>();
        for (OrderEvaluate evaluate : orderEvaluateList) {
            OrderEvaluatePage orderEvaluatePage = new OrderEvaluatePage();
            entityToPage(evaluate, orderEvaluatePage);
            orderEvaluatePageList.add(orderEvaluatePage);
        }
        return ApiResult.ok(orderEvaluatePageList);
    }

    public void entityToPage(OrderEvaluate orderEvaluate, OrderEvaluatePage orderEvaluatePage) {
        BeanUtils.copyProperties(orderEvaluate, orderEvaluatePage);
        orderEvaluatePage.setId(orderEvaluate.getId());
        if (orderEvaluate.getRoomOrder() != null) {
            orderEvaluatePage.setRoomOrderId(orderEvaluate.getRoomOrder().getId());
            orderEvaluatePage.setRoomOrderName(orderEvaluate.getRoomOrder().getOrderCode());
            orderEvaluatePage.setUserPic(orderEvaluate.getRoomOrder().getUser().getPic());
            orderEvaluatePage.setUserName(orderEvaluate.getRoomOrder().getUser().getNickName());
            orderEvaluatePage.setUserAccount(orderEvaluate.getRoomOrder().getUser().getUsername());
            orderEvaluatePage.setStayDate(orderEvaluate.getRoomOrder().getStayDate());
        }
    }

    public void pageToEntity(OrderEvaluatePage orderEvaluatePage, OrderEvaluate orderEvaluate) {
        BeanUtils.copyProperties(orderEvaluatePage, orderEvaluate);
        if (orderEvaluatePage.getRoomOrderId() != null) {
            orderEvaluate.setRoomOrder(roomOrderService.find(orderEvaluatePage.getRoomOrderId()));
        }
    }
}
