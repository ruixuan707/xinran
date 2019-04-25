package com.monco.core.service;

import com.monco.core.entity.OrderEvaluate;
import com.monco.core.page.OrderEvaluatePage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:16
 * @Description:
 */
public interface OrderEvaluateService extends BaseService<OrderEvaluate, Long> {

    /**
     * 订单评价分页查询
     *
     * @param pageable
     * @param orderEvaluatePage
     * @return
     */
    Page<OrderEvaluate> getOrderEvaluateList(Pageable pageable, OrderEvaluatePage orderEvaluatePage);

}
