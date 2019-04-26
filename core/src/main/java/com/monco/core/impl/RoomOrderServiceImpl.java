package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.RoomOrderDao;
import com.monco.core.entity.RoomInfo;
import com.monco.core.entity.RoomOrder;
import com.monco.core.page.RoomOrderPage;
import com.monco.core.service.BaseService;
import com.monco.core.service.RoomInfoService;
import com.monco.core.service.RoomOrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:21
 * @Description:
 */
@Service
public class RoomOrderServiceImpl extends BaseServiceImpl<RoomOrder, Long> implements RoomOrderService {

    @Autowired
    RoomOrderDao roomOrderDao;

    @Autowired
    RoomInfoService roomInfoService;

    @Override
    public Page<RoomOrder> getRoomOrderList(Pageable pageable, RoomOrderPage roomOrderPage) {
        Page<RoomOrder> result = roomOrderDao.findAll(new Specification<RoomOrder>() {
            @Override
            public Predicate toPredicate(Root<RoomOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                /**
                 * 房屋出租状态
                 */
                if (roomOrderPage.getOrderStatus() != null) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("orderStatus").as(Integer.class), roomOrderPage.getOrderStatus()));
                }
                /**
                 * 用户订单
                 */
                if (roomOrderPage.getUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("user").get("id").as(Long.class), roomOrderPage.getUserId()));
                }
                /**
                 * 房东订单
                 */
                if (roomOrderPage.getRoomUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(
                            root.get("roomInfo").get("user").get("id").as(Long.class), roomOrderPage.getRoomUserId()));
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

    @Override
    public List<RoomOrder> getRoomOrderList(Long roomInfoId) {
        RoomInfo roomInfo = roomInfoService.find(roomInfoId);
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setRoomInfo(roomInfo);
        roomOrder.setDataDelete(ConstantUtils.UN_DELETE);
        Example<RoomOrder> roomOrderExample = Example.of(roomOrder);
        List<RoomOrder> roomOrderList = this.findAll(roomOrderExample, Sort.by("id"));
        return roomOrderList;
    }

    @Override
    @Transactional
    public void setScore(RoomOrder roomOrder) {
        this.save(roomOrder);
        RoomInfo roomInfo = roomOrder.getRoomInfo();
        List<RoomOrder> roomOrderList = this.getRoomOrderList(roomInfo.getId(), ConstantUtils.NUM_3);
        Double total = (roomOrderList.size() - 1) * roomInfo.getScore() + roomOrder.getScore();
        Double score = total / roomOrderList.size();
        roomInfo.setScore(score);
        roomInfoService.save(roomInfo);
    }

    @Override
    public boolean enableOrder(RoomOrderPage roomOrderPage) {
        List<RoomOrder> roomOrderList = roomOrderDao.getEnableOrder(roomOrderPage.getRoomInfoId(), roomOrderPage.getStayDate(), roomOrderPage.getLeaveDate());
        if (CollectionUtils.isEmpty(roomOrderList)) {
            return true;
        }
        return false;
    }

    @Override
    public List<RoomOrder> getRoomOrderList(Long roomInfoId, Integer orderStatus) {
        RoomInfo roomInfo = roomInfoService.find(roomInfoId);
        RoomOrder roomOrder = new RoomOrder();
        roomOrder.setRoomInfo(roomInfo);
        roomOrder.setDataDelete(ConstantUtils.UN_DELETE);
        roomOrder.setOrderStatus(orderStatus);
        Example<RoomOrder> roomOrderExample = Example.of(roomOrder);
        List<RoomOrder> roomOrderList = this.findAll(roomOrderExample, Sort.by("id"));
        return roomOrderList;
    }
}
