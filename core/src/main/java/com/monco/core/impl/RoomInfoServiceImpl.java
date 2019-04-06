package com.monco.core.impl;

import com.monco.core.dao.RoomInfoDao;
import com.monco.core.entity.RoomInfo;
import com.monco.core.page.RoomInfoPage;
import com.monco.core.service.RoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:20
 * @Description:
 */
@Service
public class RoomInfoServiceImpl extends BaseServiceImpl<RoomInfo, Long> implements RoomInfoService {

    @Autowired
    RoomInfoDao roomInfoDao;

    @Override
    public Page<RoomInfo> getRoomInfoList(Pageable pageable, RoomInfoPage roomInfoPage) {
        Page<RoomInfo> result = roomInfoDao.findAll(new Specification<RoomInfo>() {
            @Override
            public Predicate toPredicate(Root<RoomInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                // 自动加载查询条件
                if (roomInfoPage.getHoldSize() != null) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("holdSize").as(Integer.class),
                            roomInfoPage.getHoldSize()));
                }
                predicateList.add(criteriaBuilder.equal(
                        root.get("dataDelete").as(Integer.class), 0));
                Predicate[] predicates = new Predicate[predicateList.size()];
                criteriaQuery.where(predicateList.toArray(predicates));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return result;
    }
}
