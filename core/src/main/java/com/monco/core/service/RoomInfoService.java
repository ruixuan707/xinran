package com.monco.core.service;

import com.monco.core.entity.RoomInfo;
import com.monco.core.page.RoomInfoPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:17
 * @Description:
 */
public interface RoomInfoService extends BaseService<RoomInfo, Long> {

    /**
     * 房源分页查询
     *
     * @param pageable
     * @param roomInfoPage
     * @return
     */
    Page<RoomInfo> getRoomInfoList(Pageable pageable, RoomInfoPage roomInfoPage);


    /**
     * 按照城市查询房间
     *
     * @param cityName
     * @return
     */
    List<RoomInfo> getRoomInfoListByCityName(String cityName);

}
