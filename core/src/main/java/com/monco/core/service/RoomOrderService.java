package com.monco.core.service;

import com.monco.core.entity.RoomOrder;
import com.monco.core.page.RoomOrderPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:17
 * @Description:
 */
public interface RoomOrderService extends BaseService<RoomOrder, Long> {

    /**
     * 分页查询
     *
     * @param pageable
     * @param roomOrderPage
     * @return
     */
    Page<RoomOrder> getRoomOrderList(Pageable pageable, RoomOrderPage roomOrderPage);
}
