package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.UserDao;
import com.monco.core.entity.User;
import com.monco.core.service.UserService;
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
 * @Date: 2019/3/24 17:22
 * @Description:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Page<User> getUserList(Pageable pageable, User user) {
        Page<User> result = userDao.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

    @Override
    public User login(String username, String password) {
        return userDao.findUserByUsernameAndPasswordAndDataDelete(username, password, ConstantUtils.UN_DELETE);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findUserByUsernameAndDataDelete(username, ConstantUtils.UN_DELETE);
    }
}
