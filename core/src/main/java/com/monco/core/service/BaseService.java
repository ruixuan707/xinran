package com.monco.core.service;

import com.monco.core.entity.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * BaseService - 业务层基类
 *
 * @author Lijin
 * @version 1.0.0
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    T save(T entity);

    void saveCollection(List<T> list);

    T find(ID id);

    T get(ID id);

    List<T> findByIds(ID... ids);

    List<T> findAll();

    List<T> findAll(Specification<T> spec, Sort sort);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> findAll(Example<T> example, Sort sort);

    T update(T entity);

    void updateList(List<T> list);

    void delete(ID id);

    void delete(ID... ids);

    void delete(T entity);

    void deleteAll(Collection<T> collections);

}
