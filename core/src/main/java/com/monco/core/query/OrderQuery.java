package com.monco.core.query;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * BaseQuery
 *
 * @author Mengke
 * @version 1.0.0
 */
@Data
public class OrderQuery implements Serializable {
    private static final long serialVersionUID = 2492447214174358772L;
    private String orderField = "id";
    private String orderType;

    /**
     * 返回排序对象
     *
     * @param orderQuery
     * @param page
     * @param size
     * @return pageable
     */
    public static Pageable getQuery(OrderQuery orderQuery, Integer page, Integer size) {
        if (StringUtils.isBlank(orderQuery.getOrderField())) {
            orderQuery.setOrderField("id");
        }
        //排序
        Sort.Direction direction = Sort.Direction.DESC;
        if (StringUtils.isNotBlank(orderQuery.getOrderType())) {
            if ("ASC".equals(orderQuery.getOrderType())) {
                direction = Sort.Direction.ASC;
            }
        }
        PageRequest pageable = PageRequest.of(page, size, direction, orderQuery.getOrderField());
        return pageable;
    }
}