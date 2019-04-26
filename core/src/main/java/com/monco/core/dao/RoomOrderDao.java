package com.monco.core.dao;

import com.monco.core.entity.RoomOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:14
 * @Description:
 */
public interface RoomOrderDao extends BaseDao<RoomOrder, Long> {

    @Query(value = "select * from sys_room_order where room_info_id = ?1 and stay_date >= ?2 and stay_date < ?3 and data_delete = 0", nativeQuery = true)
    List<RoomOrder> getEnableOrder(Long roomInfoId, Date stayDate, Date leaveDate);
}
