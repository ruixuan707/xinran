package com.monco.core.impl;

import com.monco.core.dao.BaseDao;
import com.monco.core.entity.BaseEntity;
import com.monco.core.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.*;

/**
 * BaseServiceImpl - 业务层基类实现
 *
 * @author monco
 * @version 1.0.0
 */
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

    /**
     * 注入 EntityManager
     */
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected BaseDao<T, ID> baseDao;

    /**
     * 通用保存方法
     * JPA采用的是 实体绑定存储原则  我们只要向EntityManager 容器中加入响应的实体  通过entityManager可以对这个实体进行增删改查
     * EntityManager 默认提供好多种方法
     * persist 保存
     * merge 修改
     * find 查询
     * remove 删除
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public T save(T entity) {
        Assert.notNull(entity, "Entity is not empty");
        // 声明泛型
        Optional<T> optional;
        // 如果获得entity实体的id 为空 则为添加
        if (Objects.isNull(entity.getId())) {
            optional = Optional.empty();
        }
        // 如果获得entity实体的id 不为空 则将此实体从数据库中查出来
        else {
            optional = baseDao.findById(entity.getId());
        }
        /**
         * Optional 类是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
         * Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样我们就不用显式进行空值检测。
         * Optional 类的引入很好的解决空指针异常。
         */
        if (!optional.isPresent()) {
            entityManager.persist(entity);
            return entity;
        } else {
            T target = optional.get();
            // 获取entity中不为空的字段
            String[] nullProperties = getNullProperties(entity);
            // 将target对象 覆盖到 entity 对象中  entity中不为空的字段不覆盖
            BeanUtils.copyProperties(target, entity, nullProperties);
            // 将覆盖之后的entity 进行保存
            entityManager.merge(entity);
            return entity;
        }
    }

    @Override
    @Transactional
    public void saveCollection(List<T> list) {
        Assert.notEmpty(list, "Collection is not empty");
        for (int i = 0; i < list.size(); i++) {
            save(list.get(i));
            if (i % 30 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T find(ID id) {
        Optional<T> optional = baseDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public T get(ID id) {
        return baseDao.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findByIds(ID... ids) {
        List<T> result = new ArrayList<T>();
        if (ids != null) {
            for (ID id : ids) {
                T entity = find(id);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        Assert.notNull(pageable, "pageable is not empty");
        return spec == null ? baseDao.findAll(pageable) : baseDao.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(Specification<T> spec, Sort sort) {
        if (spec != null && sort != null) {
            return baseDao.findAll(spec, sort);
        } else if (spec != null) {
            return baseDao.findAll(spec);
        } else if (sort != null) {
            return baseDao.findAll(sort);
        } else {
            return baseDao.findAll();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(Example<T> example, Sort sort) {
        if (Objects.nonNull(example) && Objects.nonNull(sort)) {
            return baseDao.findAll(example, sort);
        } else if (Objects.nonNull(example)) {
            return baseDao.findAll(example);
        } else if (Objects.nonNull(sort)) {
            return baseDao.findAll(sort);
        } else {
            return baseDao.findAll();
        }
    }

    @Override
    @Transactional
    public T update(T entity) {
        Assert.isTrue(!entity.isNew(), "Entity is new");
        Optional<T> optional = baseDao.findById(entity.getId());
        T target = optional.get();
        String[] nullProperties = getNullProperties(entity);
        BeanUtils.copyProperties(entity, target, nullProperties);
        entityManager.merge(target);
        return entity;
    }

    @Override
    @Transactional
    public void updateList(List<T> list) {
        Assert.notEmpty(list, "Collection is not empty");
        for (T t : list) {
            update(t);
        }
    }

    @Override
    @Transactional
    public void delete(ID id) {
        baseDao.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(ID... ids) {
        if (ids != null) {
            for (ID id : ids) {
                delete(id);
            }
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    /**
     * 获取对象空属性
     *
     * @param src 源对象
     * @return 空属性
     */
    private String[] getNullProperties(Object src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            if ("class".equals(propertyName) || "new".equals(propertyName)) {
                continue;
            }
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (propertyValue != null) {
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[0]);
    }

    @Override
    @Transactional
    public void deleteAll(Collection<T> collections) {
        baseDao.deleteAll(collections);
    }

}