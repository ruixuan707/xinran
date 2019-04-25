package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.OrderEvaluateDao;
import com.monco.core.entity.OrderEvaluate;
import com.monco.core.page.OrderEvaluatePage;
import com.monco.core.service.OrderEvaluateService;
import org.apache.commons.lang3.ArrayUtils;
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
 * @Date: 2019/3/24 17:19
 * @Description:
 */
@Service
public class OrderEvaluateServiceImpl extends BaseServiceImpl<OrderEvaluate, Long> implements OrderEvaluateService {

    @Autowired
    OrderEvaluateDao orderEvaluateDao;

    @Override
    public Page<OrderEvaluate> getOrderEvaluateList(Pageable pageable, OrderEvaluatePage orderEvaluatePage) {
        Page<OrderEvaluate> result = orderEvaluateDao.findAll(new Specification<OrderEvaluate>() {
            @Override
            public Predicate toPredicate(Root<OrderEvaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                // 评价
                if (ArrayUtils.isNotEmpty(orderEvaluatePage.getRoomOrderIds())) {
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("roomOrder").get("id"));
                    for (Long id : orderEvaluatePage.getRoomOrderIds()) {
                        in.value(id);
                    }
                    predicateList.add(in);
                }
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
