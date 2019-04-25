package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.HomeWheelDao;
import com.monco.core.entity.HomeWheel;
import com.monco.core.service.HomeInfoService;
import com.monco.core.service.HomeWheelService;
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
 * @Date: 2019/4/25 16:35
 * @Description:
 */
@Service
public class HomeWheelServiceImpl extends BaseServiceImpl<HomeWheel, Long> implements HomeWheelService {

    @Autowired
    HomeWheelDao homeWheelDao;


    @Override
    public Page<HomeWheel> getHomeWheelList(Pageable pageable, HomeWheel homeWheel) {
        Page<HomeWheel> result = homeWheelDao.findAll(new Specification<HomeWheel>() {
            @Override
            public Predicate toPredicate(Root<HomeWheel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(
                        root.get("dataDelete").as(Integer.class), ConstantUtils.UN_DELETE));
                Predicate[] predicates = new Predicate[predicateList.size()];
                criteriaQuery.where(predicateList.toArray(predicates));
                return criteriaQuery.getRestriction();
            }
        },pageable);
        return result;
    }
}
