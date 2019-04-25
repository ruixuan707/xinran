package com.monco.core.service;

import com.monco.core.entity.HomeWheel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: monco
 * @Date: 2019/4/25 16:35
 * @Description:
 */
public interface HomeWheelService extends BaseService<HomeWheel, Long> {

    /**
     * 首页轮播分页查询
     *
     * @param pageable
     * @param homeWheel
     * @return
     */
    Page<HomeWheel> getHomeWheelList(Pageable pageable, HomeWheel homeWheel);
}
