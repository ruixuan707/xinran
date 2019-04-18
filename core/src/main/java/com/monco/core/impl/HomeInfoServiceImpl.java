package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.HomeInfoDao;
import com.monco.core.entity.HomeInfo;
import com.monco.core.page.HomeInfoPage;
import com.monco.core.service.HomeInfoService;
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
 * @Date: 2019/3/24 17:18
 * @Description:
 */
@Service
public class HomeInfoServiceImpl extends BaseServiceImpl<HomeInfo, Long> implements HomeInfoService {

    @Autowired
    HomeInfoDao homeInfoDao;

    @Override
    public Page<HomeInfo> getHomeInfoList(Pageable pageable, HomeInfoPage homeInfoPage) {
        Page<HomeInfo> result = homeInfoDao.findAll(new Specification<HomeInfo>() {
            @Override
            public Predicate toPredicate(Root<HomeInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(
                        root.get("dataDelete").as(Integer.class), ConstantUtils.UN_DELETE));
                Predicate[] predicates = new Predicate[predicateList.size()];
                criteriaQuery.where(predicateList.toArray(predicates));
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return result;
    }
}
