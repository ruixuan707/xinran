package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.UserDiaryDao;
import com.monco.core.entity.UserDiary;
import com.monco.core.service.UserDiaryService;
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
 * @Date: 2019/4/11 22:09
 * @Description:
 */
@Service
public class UserDiaryServiceImpl extends BaseServiceImpl<UserDiary, Long> implements UserDiaryService {

    @Autowired
    UserDiaryDao userDiaryDao;

    @Override
    public Page<UserDiary> getUserDiaryList(Pageable pageable, UserDiary userDiary) {
        Page<UserDiary> result = userDiaryDao.findAll(new Specification<UserDiary>() {
            @Override
            public Predicate toPredicate(Root<UserDiary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
