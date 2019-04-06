package com.monco.core.dao;

import com.monco.core.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * BaseDao - 持久层基类
 *
 * @author Lijin
 * @version 1.0.0
 */
@Repository
public interface BaseDao<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}