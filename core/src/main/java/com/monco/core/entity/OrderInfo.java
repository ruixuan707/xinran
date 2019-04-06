package com.monco.core.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Auther: monco
 * @Date: 2019/3/24 17:01
 * @Description:
 */
@Data
@Entity
@Table(name = "sys_order_info")
public class OrderInfo extends BaseEntity<Long> {

    private static final long serialVersionUID = -7178597042799489587L;


}
