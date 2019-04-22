package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.RoomInfoDao;
import com.monco.core.entity.RoomInfo;
import com.monco.core.page.RoomInfoPage;
import com.monco.core.service.RoomInfoService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
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
                // 自动加载查询条件
                List<Predicate> predicateList = new ArrayList<>();
                // 价格小于最大值
                if (roomInfoPage.getTopPrice() != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("price").as(BigDecimal.class),
                            roomInfoPage.getTopPrice()));
                }
                // 价格大于最小值
                if (roomInfoPage.getBottomPrice() != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("price").as(BigDecimal.class),
                            roomInfoPage.getBottomPrice()));
                }
                // 出租类型
                if (roomInfoPage.getRentType() != null) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("rentType").as(Integer.class),
                            roomInfoPage.getRentType()));
                }
                // 宜居人数
                if (roomInfoPage.getHoldSize() != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("holdSize").as(Integer.class),
                            roomInfoPage.getHoldSize()));
                }
                // 城市
                if (StringUtils.isNotBlank(roomInfoPage.getCity())) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("city").as(String.class),
                            roomInfoPage.getCity()));
                }
                // 收藏列表
                if (ArrayUtils.isNotEmpty(roomInfoPage.getRoomCollectionIds())) {
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("id"));
                    for (Long id : roomInfoPage.getRoomCollectionIds()){
                        in.value(id);
                    }
                    predicateList.add(in);
                }
                // 房间审核状态
                if (roomInfoPage.getRoomStatus() != null) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("roomStatus").as(Integer.class),
                            roomInfoPage.getRoomStatus()));
                }
                // 户型
                if (StringUtils.isNotBlank(roomInfoPage.getRoomType())) {
                    predicateList.add(criteriaBuilder.like(
                            root.get("roomType").as(String.class),
                            "%" + roomInfoPage.getRoomType() + "%"));
                }
                // 设施
                if (StringUtils.isNotBlank(roomInfoPage.getFacilities())) {
                    predicateList.add(criteriaBuilder.like(
                            root.get("facilities").as(String.class),
                            "%" + roomInfoPage.getFacilities() + "%"));
                }
                // 绑定登录用户
                if (roomInfoPage.getUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("user").get("id").as(Long.class),
                            roomInfoPage.getUserId()));
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
