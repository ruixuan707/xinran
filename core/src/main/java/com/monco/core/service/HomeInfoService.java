package com.monco.core.service;

import com.monco.core.entity.HomeInfo;
import com.monco.core.page.HomeInfoPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:15
 * @Description:
 */
public interface HomeInfoService extends BaseService<HomeInfo, Long> {

    /**
     * 分页查找房屋信息
     *
     * @param pageable
     * @param homeInfoPage
     * @return
     */
    Page<HomeInfo> getHomeInfoList(Pageable pageable, HomeInfoPage homeInfoPage);
}
